package com.example.assignment2.user;

import com.example.assignment2.user.dto.UserDTO;
import com.example.assignment2.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.assignment2.URLMapping.ENTITY;
import static com.example.assignment2.URLMapping.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() { return userService.allUsersForList();}

    @PostMapping
    public UserDTO create(@RequestBody UserDTO user) {
       return userService.create(user);
    }

    @DeleteMapping(ENTITY)
    public void deleteById(@PathVariable Long id){
        userService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() { userService.deleteAll(); }

    @PatchMapping(ENTITY)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.edit(id, user);
    }

    @GetMapping(ENTITY)
    public UserDTO getUser(@PathVariable Long id) {
        return userService.get(id);
    }

}
