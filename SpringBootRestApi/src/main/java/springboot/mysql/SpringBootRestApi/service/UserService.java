package springboot.mysql.SpringBootRestApi.service;

import java.util.List;
import java.util.Optional;

import springboot.mysql.SpringBootRestApi.entity.User;
import springboot.mysql.SpringBootRestApi.exception.AlreadyExistsException;
import springboot.mysql.SpringBootRestApi.exception.CustomException;
import springboot.mysql.SpringBootRestApi.exception.RecordNotFoundException;

public interface UserService {

	User createUser(User user) throws AlreadyExistsException;
	
	Optional<User> getUserById(Long userId) throws RecordNotFoundException;

	//User getUserById(Long userId);

	List<User> getAllUsers();

	User updateUser(User user);

	void deleteUser(Long userId);
	
	List<User> searchUsers(String query);
	
	Optional<User> findUserByEmail(String email) throws RecordNotFoundException;
	
	Optional<User> authenticateUser(String email, String password) throws RecordNotFoundException;
	
	Optional<User> loginUser(User login) throws CustomException;
	
	

}
