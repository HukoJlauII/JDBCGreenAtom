package com.example.jdbcgreenatom;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private UserDao userDao;

    @GetMapping
    public List<User> findAllUsers(@RequestParam String filter, @RequestParam int limit, @RequestParam int offset) throws SQLException {
        return userDao.getUsersWithFilterAndPagination(filter, limit, offset);
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        return "user " + user.getUsername() + " created!";
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable Long userId) throws SQLException {
        return userDao.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    public String updateUserById(@RequestBody User user, @PathVariable Long userId) throws SQLException {
        userDao.updateUser(user);
        return "user " + user.getUsername() + " updated!";
    }

    @DeleteMapping("/{userId}")
    public String deleteUserById(@PathVariable Long userId) throws SQLException {
        userDao.deleteUser(userId);
        return "user deleted!";
    }

}
