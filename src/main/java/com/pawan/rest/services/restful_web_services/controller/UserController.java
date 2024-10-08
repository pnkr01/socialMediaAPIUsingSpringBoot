package com.pawan.rest.services.restful_web_services.controller;

import com.pawan.rest.services.restful_web_services.exceptions.UserNotFoundException;
import com.pawan.rest.services.restful_web_services.model.User;
import com.pawan.rest.services.restful_web_services.services.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    public EntityModel<User> getUserById(@PathVariable int id){
        User user =  userDaoService.findById(id);
        if(user == null){
            throw new UserNotFoundException("User not found for id : "+id);
        }
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUserById(@PathVariable int id){
        userDaoService.deleteUserById(id);
    }

    //creeate a user with id,name, birthDate
    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(savedUser.getId());
        return ResponseEntity.created(location).build();
    }
}
