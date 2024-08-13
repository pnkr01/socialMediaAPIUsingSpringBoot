package com.pawan.rest.services.restful_web_services.services;

import com.pawan.rest.services.restful_web_services.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<User>();

    static {
        users.add(new User(1, "Pawan", LocalDate.now().plusDays(1)));
        users.add(new User(2, "Rahul", LocalDate.now().plusDays(2)));
        users.add(new User(3, "Pinki", LocalDate.now().plusDays(3)));
        users.add(new User(4, "Kajal", LocalDate.now().plusDays(4)));
        users.add(new User(5, "Soni", LocalDate.now().plusDays(5)));
        users.add(new User(6, "Darsh", LocalDate.now().plusDays(6)));
        users.add(new User(7, "Arohi", LocalDate.now().plusDays(7)));
        users.add(new User(8, "Anita", LocalDate.now().plusDays(8)));
        users.add(new User(9, "Raju", LocalDate.now().plusDays(9)));
    }
    private static int userCount =9;

    public List<User> findAllUsers() {
        return users;
    }
    public User findById(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public void deleteUserById(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;
        users.removeIf(predicate);
    }

    public User save(User user) {
        user.setId(++userCount);
        users.add(user);
        return user;
    }

}
