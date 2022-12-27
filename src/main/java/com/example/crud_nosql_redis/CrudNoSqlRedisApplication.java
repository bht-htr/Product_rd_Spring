package com.example.crud_nosql_redis;

import com.example.crud_nosql_redis.entity.Product;
import com.example.crud_nosql_redis.reponsitory.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class CrudNoSqlRedisApplication {

    @Autowired
    private ProductDao dao;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return dao.save(product);
    }

    @GetMapping
    public List<Product> getALlProducts() {
        return dao.findAll();
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.POST,
//            headers = "Accept=application/json")
    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result.price > 1000")
    public Product findProduct(@PathVariable int id) {
        return dao.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "Product")
    public String delete(@PathVariable int id) {
        return dao.deleteProduct(id);
    }
    public static void main(String[] args) {
        SpringApplication.run(CrudNoSqlRedisApplication.class, args);
    }

}
