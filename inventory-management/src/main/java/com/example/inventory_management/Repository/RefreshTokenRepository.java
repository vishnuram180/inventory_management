package com.example.inventory_management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.inventory_management.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{

	
	@Query(value="DELETE FROM refresh_token WHERE user_id=:userID" ,nativeQuery=true)
	void deletebyUser(@Param("userID")Integer user_id);

	Optional<RefreshToken> findByToken(String refresh_Token);
	
	@Query(value="SELECT*FROM refresh_token WHERE user_id=:userID" ,nativeQuery=true)
	RefreshToken findbyUser(@Param("userID")Integer user_id);


	

}
