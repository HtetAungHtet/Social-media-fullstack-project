package com.hah.social.service;

import com.hah.social.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    User registerUser(User user);
    User updateUserDetails(User user, Long id);
    User findUserByEmail(String email);
    User followUser(Long userId1, Long userId2);

    List<User> searchUser(String query);

    void deleteUser(Long id);

    User findUserByToken(String jwt);

}
