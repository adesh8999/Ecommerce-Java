package springboot.mysql.SpringBootRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.mysql.SpringBootRestApi.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
