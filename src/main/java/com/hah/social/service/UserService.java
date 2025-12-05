package com.hah.social.service;

import com.hah.social.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAllUsers();

    Optional<UserDto> findUserById(Long id);

    UserDto registerUser(UserDto userDto);
    UserDto updateUserDetails(UserDto userDto, Long id);
    UserDto findUserByEmail(String email);
    UserDto followUser(Long userId1, Long userId2);

    List<UserDto> searchUser(String query);

    void deleteUser(Long id);
}
