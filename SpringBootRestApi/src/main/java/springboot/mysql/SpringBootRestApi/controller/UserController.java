package springboot.mysql.SpringBootRestApi.controller;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.ResponseHandler;
import springboot.mysql.SpringBootRestApi.dto.EmailDetails;
import springboot.mysql.SpringBootRestApi.entity.User;
import springboot.mysql.SpringBootRestApi.exception.AlreadyExistsException;
import springboot.mysql.SpringBootRestApi.exception.CustomException;
import springboot.mysql.SpringBootRestApi.service.EmailService;
import springboot.mysql.SpringBootRestApi.service.UserService;
import springboot.mysql.SpringBootRestApi.utils.FileDownloadUtil;
//import springboot.mysql.SpringBootRestApi.service.UserServiceImpl;
import springboot.mysql.SpringBootRestApi.utils.FileUploadResponse;
import springboot.mysql.SpringBootRestApi.utils.FileUploadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
//import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	// build create User REST API
	@PostMapping("/adminSignUp")
	public ResponseEntity<Object> createAdmin(@RequestBody User user) throws AlreadyExistsException {

		user.setRollType(501);
		User savedUser = userService.createUser(user);
		
		return ResponseHandler.generateResponse("Admin Account created successfully", HttpStatus.CREATED, savedUser);
	}
	
	@PostMapping("/adminLogin")
	public ResponseEntity<Object> loginAdmin(@RequestBody User log) throws CustomException {
		Optional<User> login = userService.loginUser(log);

		return ResponseHandler.generateResponse("Admin Logged in successfully", HttpStatus.ACCEPTED, login, false);
	}

	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@RequestBody User user) throws AlreadyExistsException {

		user.setRollType(502);
		User savedUser = userService.createUser(user);
		return ResponseHandler.generateResponse("User created successfully", HttpStatus.CREATED, savedUser);
	}

	// build get user by id REST API
	// http://localhost:8080/api/users/1
	@GetMapping("{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") Long userId) {
		Optional<User> user = userService.getUserById(userId);
		return ResponseHandler.generateResponse("User by id fetch successfully", HttpStatus.OK, user);
	}

	// Build Get All Users REST API
	// http://localhost:8080/api/users
	@GetMapping
	public ResponseEntity<Object> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseHandler.generateResponse("All Users fetch successfully", HttpStatus.OK, users);
	}

	// Build Update User REST API
	@PutMapping("{id}")
	// http://localhost:8080/api/users/1
	public ResponseEntity<Object> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
		user.setId(userId);
		User updatedUser = userService.updateUser(user);
		return ResponseHandler.generateResponse("User updated successfully", HttpStatus.OK, updatedUser);
	}

	// Build Delete User REST API
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
		return ResponseHandler.generateResponse("User deleted successfully", HttpStatus.OK, userService);
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile)
			throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();

		String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

		FileUploadResponse response = new FileUploadResponse();
		response.setFileName(fileName);
		response.setSize(size);
		response.setDownloadUri("/downloadFile/" + filecode);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/downloadFile/{fileCode}")
	// (For download image, uncomment the lines 125 and 141)
	public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
		FileDownloadUtil downloadUtil = new FileDownloadUtil();

		Resource resource = null;
		try {
			resource = downloadUtil.getFileAsResource(fileCode);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}

		if (resource == null) {
			return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
		}
//         System.out.println(resource.getFilename());
//        String contentType = "application/octet-stream";
//       String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
//         
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
//                .body(resource);    

		String extension = "jpg";
		String fileName = resource.getFilename();
		int index = fileName.lastIndexOf('.');
		if (index > 0) {
			extension = fileName.substring(index + 1);
		}

		return ResponseEntity.ok().contentType(
				MediaType.parseMediaType(extension == "jpg" ? MediaType.IMAGE_JPEG_VALUE : MediaType.IMAGE_PNG_VALUE))
//				 .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
				.body(resource);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody User log) throws CustomException {
		Optional<User> login = userService.loginUser(log);

		return ResponseHandler.generateResponse("User Logged in successfully", HttpStatus.ACCEPTED, login, false);
	}
	
	
	// email send
	
	
	 
    // Sending a simple Email
    @PostMapping("/sendMail")
    public Object sendMail(@RequestBody EmailDetails details)
    {
        String status
            = emailService.sendSimpleMail(details);
 
        return ResponseHandler.generateResponse("Mail Send Successfully ", HttpStatus.OK, status, false);
    }
 
    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
        @RequestBody EmailDetails details)
    {
        String status
            = emailService.sendMailWithAttachment(details);
 
        return status;
    }

}
