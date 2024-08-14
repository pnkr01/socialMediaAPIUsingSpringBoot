package com.pawan.rest.services.restful_web_services.repository;

import com.pawan.rest.services.restful_web_services.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
