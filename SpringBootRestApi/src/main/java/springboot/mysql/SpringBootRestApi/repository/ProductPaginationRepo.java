package springboot.mysql.SpringBootRestApi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import springboot.mysql.SpringBootRestApi.entity.Product;

public interface ProductPaginationRepo extends PagingAndSortingRepository<Product, Long> {

}
