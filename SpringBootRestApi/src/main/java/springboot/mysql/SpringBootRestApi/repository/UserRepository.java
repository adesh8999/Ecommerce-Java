package springboot.mysql.SpringBootRestApi.repository;

import java.util.List;
import java.util.Optional;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import springboot.mysql.SpringBootRestApi.entity.User;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("Select u from User u Where "
			+ "u.email like concat('%', :query, '%')"
			+ "Or u.firstName Like Concat('%', :query, '%')")
	List<User> searchUsers(String query);
	
	Optional<User> findByEmail(String email);
	
	//List<User> findByLastName(String lastName, Sort sort);
	
	//Optional<User> findByEmailOrPassword(String email, String password);
	
	Optional<User> findByPassword(String password);
	
	Optional<User> findByEmailAndPassword(String email, String password);
	
	
	

}
