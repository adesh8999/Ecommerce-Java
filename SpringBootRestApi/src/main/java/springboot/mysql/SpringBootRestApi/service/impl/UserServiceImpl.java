package springboot.mysql.SpringBootRestApi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.entity.User;
import springboot.mysql.SpringBootRestApi.exception.AlreadyExistsException;
import springboot.mysql.SpringBootRestApi.exception.CustomException;
import springboot.mysql.SpringBootRestApi.exception.RecordNotFoundException;
import springboot.mysql.SpringBootRestApi.repository.UserRepository;
import springboot.mysql.SpringBootRestApi.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public User createUser(User user) throws AlreadyExistsException {
		
		Optional<User> u1 = userRepository.findByEmail(user.getEmail());
		if(u1.isPresent()) {
			throw new AlreadyExistsException("Email already exists, Please use another email.");
		}
		
		return userRepository.save(user);
	}

	@Override
	public Optional<User> getUserById(Long userId) throws RecordNotFoundException {
		
		 Optional<User> optionalUser = userRepository.findById(userId);
		 if(optionalUser.isPresent()) {
			 return optionalUser;
		 } else {
			 throw new RecordNotFoundException("User not found with id " + userId);
		 }
		 
	}

	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public User updateUser(User user) {
		
		User existingUser = userRepository.findById(user.getId()).get();
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
	}

	@Override
	public void deleteUser(Long userId) {
		
		userRepository.deleteById(userId);;

	}

	@Override
	public List<User> searchUsers(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findUserByEmail(String email) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<User> authenticateUser(String email, String password) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<User> loginUser(User login) throws CustomException {
		Optional<User> login2 = userRepository.findByEmailAndPassword(login.getEmail() , login.getPassword());
		if(login2.isPresent()) {
			
			return login2;
		} else {
			throw new CustomException("User not registered...");
		}
		
	}

	

}
