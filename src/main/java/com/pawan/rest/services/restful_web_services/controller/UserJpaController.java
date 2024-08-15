package com.pawan.rest.services.restful_web_services.controller;

import com.pawan.rest.services.restful_web_services.exceptions.PostNotFoundException;
import com.pawan.rest.services.restful_web_services.exceptions.UserNotFoundException;
import com.pawan.rest.services.restful_web_services.model.Post;
import com.pawan.rest.services.restful_web_services.model.User;
import com.pawan.rest.services.restful_web_services.repository.PostRepository;
import com.pawan.rest.services.restful_web_services.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    //GET /user => retrieve all users

    @GetMapping(path = "/jpa/users")
    public List<User> getAllUser(){
      return  userRepository.findAll();
    }

    //GET specific user by id
    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<User> getUserById(@PathVariable int id){
        Optional<User> user =  userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found for id : "+id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUserById(@PathVariable int id){
        userRepository.deleteById(id);
    }

    //creeate a user with id,name, birthDate
    @PostMapping(path = "/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(savedUser.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> retrieveAllPostOfAUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("User not found for id : "+id);
        return user.get().getPostList();
    }

    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostOfAUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("User not found for id : "+id);

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(savedPost.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/jpa/users/{id}/posts/{postid}")
    public Optional<Post> getPostOfUserByPostId(@PathVariable int id, @PathVariable int postid) {
        // Find the user by id first
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        // Now find the post by postid and make sure it belongs to the user
        Optional<Post> post = postRepository.findById(postid);
        if(post.isEmpty() || !post.get().getUser().getId().equals(id)) {
            throw new PostNotFoundException("Post not found for user with id: " + id + " and post id: " + postid);
        }

        return post;
    }



}
