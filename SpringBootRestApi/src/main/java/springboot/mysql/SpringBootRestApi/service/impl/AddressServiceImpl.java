package springboot.mysql.SpringBootRestApi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.entity.Address;
import springboot.mysql.SpringBootRestApi.repository.AddressRepo;
import springboot.mysql.SpringBootRestApi.service.AddressService;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	AddressRepo addressRepo;

	@Override
	public Address save(Address address) {
		
		return addressRepo.save(address);
	}

}
