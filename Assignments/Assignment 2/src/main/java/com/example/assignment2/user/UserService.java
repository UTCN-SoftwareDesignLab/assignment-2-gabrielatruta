package com.example.assignment2.user;

import com.example.assignment2.user.dto.UserDTO;
import com.example.assignment2.user.dto.UserListDTO;
import com.example.assignment2.user.dto.UserMinimalDTO;
import com.example.assignment2.user.mapper.UserMapper;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import com.example.assignment2.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(Collectors.toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public UserDTO create(UserDTO user) {
        Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
        User user1 = userMapper.fromDTO(user);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user1.setRoles(roles);
        
        return userMapper.toDTO(userRepository.save(user1));
    }

    public UserDTO edit(Long id, UserDTO user) {
        User actUser = findById(id);
        actUser.setEmail(user.getEmail());
        actUser.setUsername(user.getUsername());
        actUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toDTO(
                userRepository.save(actUser)
        );

    }

    public UserDTO changePassword(Long id, String newPassword) {
        User actUser = findById(id);
        actUser.setPassword(newPassword);
        return userMapper.toDTO(
                userRepository.save(actUser)
        );
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() { userRepository.deleteAll(); }

    public UserDTO get(Long id) {
        return userMapper.toDTO(findById(id));
    }
}
