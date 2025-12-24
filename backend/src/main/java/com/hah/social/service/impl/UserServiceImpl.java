package com.hah.social.service.impl;

import com.hah.social.config.JwtProvider;
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
        newUser.setGender(user.getGender());

        return userRepository.save(user);
    }

    @Override
    public User updateUserDetails(User user, Long id) {
        Optional<User> user1 = userRepository.findById(id);

        if (user1.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User oldUser = user1.get();

        if (user.getFirstName()!=null)
            oldUser.setFirstName(user.getFirstName());

        if (user.getLastName()!=null)
            oldUser.setLastName(user.getLastName());

        if (user.getEmail()!=null)
            oldUser.setEmail(user.getEmail());

        if (user.getPassword()!=null)
            oldUser.setPassword(user.getPassword());

        if (user.getGender()!=null)
            oldUser.setGender(user.getGender());

        return userRepository.save(oldUser);
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
    public User followUser(Long reqUserId, Long userId2) {

        User reqUser = userRepository.findById(reqUserId)
                .orElseThrow(() -> new UserNotFoundException(reqUserId));

        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));

        user2.getFollowers().add(reqUser.getId());
        reqUser.getFollowings().add(user2.getId());

        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;
    }

    @Override
    public List<User> searchUser(String query) {
        return  userRepository.searchUser(query);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User findUserByToken(String jwt) {
        String mail = JwtProvider.getEmailFromJwtToken(jwt);

        return userRepository.findByEmail(mail)
                .orElseThrow(()->new UserNotFoundException(mail));
    }
}
