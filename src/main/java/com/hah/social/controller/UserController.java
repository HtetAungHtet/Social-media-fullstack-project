package com.hah.social.controller;

import com.hah.social.error.ApiErrorResponse;
import com.hah.social.model.dto.UserDto;
import com.hah.social.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return this.userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId) {
         Optional<UserDto> result = this.userService.findUserById(userId);

         if (result.isPresent())
         {
            return ResponseEntity.ok(result.get());
         }
         else
         {
             ApiErrorResponse response = new ApiErrorResponse("1010", "No such user with id : " + userId);
             return ResponseEntity.badRequest()
                     .body(response);
         }
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.findUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/search")
    public List<UserDto> searchUser(@RequestParam String query) {
        return userService.searchUser(query);
    }


    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserDto userDto,
                                               BindingResult result) {

        if (result.hasErrors())
        {
            return ResponseEntity.badRequest()
                    .body(result.getAllErrors());
        }
        else
        {
            UserDto registerUser = this.userService.registerUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(registerUser);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDto userDto,
                                             @PathVariable Long userId,
                                               BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getAllErrors());
        } else {
            UserDto updatedUser = this.userService.updateUserDetails(userDto, userId);
            return ResponseEntity.ok(updatedUser);
        }
    }

    @PutMapping("/follow/{userId1}/{userId2}")
    public UserDto followUserHandler(@PathVariable Long userId1,
                                     @PathVariable Long userId2) {
        return userService.followUser(userId1, userId2);
    }


    @DeleteMapping("{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        try
        {
            this.userService.deleteUser(userId);
            return ResponseEntity.ok()
                    .body(null);
        } catch (Exception e)
        {
            return ResponseEntity.badRequest()
                    .body(e);
        }
    }
}
