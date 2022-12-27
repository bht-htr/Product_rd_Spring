package com.example.crud_nosql_redis.reponsitory;

import com.example.crud_nosql_redis.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    public static final String HASH_KEY = "Product";

    @Autowired
    @Qualifier
    private RedisTemplate template;

    public Product save(Product product) {
        template.opsForHash().put(HASH_KEY, String.valueOf(product.getId()), product);
        return product;
    }

    public List<Product> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public Product findProductById(int id) {
        System.out.println("called findProductById() from DB");
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProduct(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "Delete Success!!";
    }


}
