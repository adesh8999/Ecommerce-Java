package springboot.mysql.SpringBootRestApi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get("Files-Upload");

		// Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		LocalDateTime dt = LocalDateTime.now();
		String format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-SS").format(dt);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// String fileCode = RandomStringUtils.randomAlphanumeric(8);

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(format + "-" + fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save file: " + fileName, ioe);
		}

		return format.toString();
	}

	
}
