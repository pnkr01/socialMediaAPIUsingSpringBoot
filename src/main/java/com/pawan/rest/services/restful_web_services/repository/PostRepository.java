package com.pawan.rest.services.restful_web_services.repository;

import com.pawan.rest.services.restful_web_services.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
