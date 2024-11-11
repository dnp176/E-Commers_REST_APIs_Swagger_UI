package com.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.entity.Cart;
import com.main.entity.Products;
import com.main.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	List<Cart> findByUserId(int userId);
	void deleteByUserAndProducts(User user, Products product);
	@Modifying
	@Query("DELETE FROM Cart c WHERE c.user.id = :userId")
	void deleteByUserId(@Param("userId") int userId);
}
