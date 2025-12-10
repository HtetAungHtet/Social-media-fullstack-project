package com.hah.social.service.impl;

import com.hah.social.mapper.UserMapper;
import com.hah.social.error.UserNotFoundException;
import com.hah.social.model.dto.UserDto;
import com.hah.social.model.entity.User;
import com.hah.social.repository.UserRepository;
import com.hah.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAllUsers() {
        List<User> userList = this.userRepository.findAll();
        return userEntityToUserDto(userList);
    }

    /* User Entity To User DTO shortcut */
    private List<UserDto> userEntityToUserDto(List<User> userList) {
        List<UserDto> usersDto = new ArrayList<>();

        for (User user : userList)
        {
            UserDto userDto = userMapper.toDto(user);
            usersDto.add(userDto);
        }

        return usersDto;
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {

        Optional<User> result = this.userRepository.findById(id);

        if (result.isPresent()) {
            User user = result.get();
            UserDto userDto = userMapper.toDto(user);
            return Optional.of(userDto);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = this.userRepository.save(user);

        userDto = userMapper.toDto(savedUser);
        return userDto;
    }

    @Override
    public UserDto updateUserDetails(UserDto userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFollowers(userDto.getFollowers());
        user.setFollowings(userDto.getFollowings());
        user.setGender(userDto.getGender());
//        user.setCreatedAt(userDto.getCreatedAt());
//        user.setUpdatedAt(userDto.getUpdatedAt());
        user.setSavedPost(userDto.getPosts());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }


    @Override
    public UserDto findUserByEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be invalid or blank!");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException(email));

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto followUser(Long userId1, Long userId2) {

        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new UserNotFoundException(userId1));

        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));

        user2.getFollowers().add(user1.getId());
        user1.getFollowings().add(user2.getId());

        userRepository.save(user1);
        userRepository.save(user2);

        return userMapper.toDto(user1); // or user2
    }

    @Override
    public List<UserDto> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users.stream()
                .map(userMapper::toDto)
                .toList();

    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
