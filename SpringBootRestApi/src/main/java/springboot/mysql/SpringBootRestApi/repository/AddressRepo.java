package springboot.mysql.SpringBootRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.mysql.SpringBootRestApi.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

}
