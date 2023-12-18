package com.example.projectappchat.repository;

import com.example.projectappchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u where u.userAccount = :userAccount")
    User findUserAccount(@Param("userAccount") String userAccount);

    @Query("SELECT u FROM User u WHERE u.userName LIKE %:userName%")
    List<User> findUserByUserName(@Param("userName") String userName);


    @Query(value = "SELECT next_val FROM hibernate_sequence", nativeQuery =
            true)
    Long getNextSeriesId();
}
