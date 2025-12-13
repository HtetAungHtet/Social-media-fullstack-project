package com.hah.social.service.impl;

import com.hah.social.error.UserNotFoundException;
import com.hah.social.model.entity.User;
import com.hah.social.repository.UserRepository;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {

        Optional<User> result = this.userRepository.findById(id);

        if (result.isPresent()) {
            User user = result.get();
            return Optional.of(user);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public User registerUser(User user) {

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFollowers(user.getFollowers());
        newUser.setFollowings(user.getFollowings());
        newUser.setGender(user.getGender());
        newUser.setSavedPost(user.getSavedPost());

        return userRepository.save(user);
    }

    @Override
    public User updateUserDetails(User user, Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFollowers(user.getFollowers());
        newUser.setFollowings(user.getFollowings());
        newUser.setGender(user.getGender());
        newUser.setSavedPost(user.getSavedPost());

        return userRepository.save(user);
    }


    @Override
    public User findUserByEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be invalid or blank!");
        }

        return userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException(email));

    }

    @Override
    @Transactional
    public User followUser(Long userId1, Long userId2) {

        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new UserNotFoundException(userId1));

        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));

        user2.getFollowers().add(user1.getId());
        user1.getFollowings().add(user2.getId());

        userRepository.save(user1);
        userRepository.save(user2);

        return user1;
    }

    @Override
    public List<User> searchUser(String query) {
        return  userRepository.searchUser(query);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
