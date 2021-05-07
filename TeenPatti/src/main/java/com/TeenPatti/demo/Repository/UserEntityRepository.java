package com.TeenPatti.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.TeenPatti.demo.Entity.UserEntity;


@EnableJpaRepositories
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long>{

}
