package com.hah.social.controller;

import com.hah.social.error.ApiErrorResponse;
import com.hah.social.model.entity.User;
import com.hah.social.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId) {
         Optional<User> result = this.userService.findUserById(userId);

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
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String query) {
        return userService.searchUser(query);
    }


    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody @Valid User user,
                                               BindingResult result) {

        if (result.hasErrors())
        {
            return ResponseEntity.badRequest()
                    .body(result.getAllErrors());
        }
        else
        {
            User registerUser = this.userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(registerUser);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User user,
                                             @RequestHeader("Authorization") String jwt,
                                               BindingResult result) {

        User reqUser = userService.findUserByToken(jwt);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getAllErrors());
        } else {
            User updatedUser = this.userService.updateUserDetails(user, reqUser.getId());
            return ResponseEntity.ok(updatedUser);
        }
    }

    @PutMapping("/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String jwt,
                                     @PathVariable Long userId2) {
        User reqUser = userService.findUserByToken(jwt);
        return userService.followUser(reqUser.getId(), userId2);
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

    @GetMapping("/profile")
    public User getUserProfileFromToken(@RequestHeader("Authorization") String jwt) {
        System.out.println("JWT token : " + jwt);

        return userService.findUserByToken(jwt);
    }
}
