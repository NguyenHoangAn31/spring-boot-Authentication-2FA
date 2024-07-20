package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;


import com.demo.entities.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.id FROM User u WHERE u.email = :email AND u.provider = 'email'")
    Optional<Integer> findIdByEmailRegister(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email AND u.otp = :otp AND provider = 'email'")
    boolean validateOtpEmai(@Param("email") String email , @Param("otp") String otp);



    
    @Query("SELECT u.id FROM User u WHERE u.phone = :phone AND u.provider = 'phone'")
    Optional<Integer> findIdByPhoneRegister(@Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.phone = :phone AND u.otp = :otp AND provider = 'phone'")
    boolean validateOtpPhone(@Param("phone") String phone , @Param("otp") String otp);



    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.otp = :otp WHERE u.id = :id")
    int refreshOtp(@Param("id") int id , @Param("otp") String otp);
}
