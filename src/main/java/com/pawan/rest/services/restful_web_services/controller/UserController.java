package com.pawan.rest.services.restful_web_services.controller;

import com.pawan.rest.services.restful_web_services.exceptions.UserNotFoundException;
import com.pawan.rest.services.restful_web_services.model.User;
import com.pawan.rest.services.restful_web_services.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    //GET /user => retrieve all users

    @GetMapping(path = "/users")
    public List<User> getAllUser(){
      return  userDaoService.findAllUsers();
    }

    //GET specific user by id
    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable int id){
        User user =  userDaoService.findById(id);
        if(user == null){
            throw new UserNotFoundException("id :"+id);
        }
        return user;
    }

    //creeate a user with id,name, birthDate
    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(savedUser.getId());
        return ResponseEntity.created(location).build();
    }
}
