package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.LoginUserDto;
import org.example.model.dto.RegisterUserDto;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;
    final private Mapper mapper;
    final private JwtService jwtService;


    @Override
    public UserDto getUserDtoById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exists!!!"));

        return mapper.userFromEntityToDto(user);
    }

    @Override
    public boolean deleteUserById(Integer id) {

        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User does not exists!!");
        }

        if(id == null){
            throw new RuntimeException("Id must not be null!!!");
        }

        userRepository.deleteById(id);

        return true;
    }

    @Override
    public UserDto updateUserById(Integer id, UserDto userDto) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User does not exists"));
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        //user.setRole(userDto.getRole());
        userRepository.save(user);

        return mapper.userFromEntityToDto(user);
    }

    @Override
    public User getUserById(Integer id) {

        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User does not exists!!"));
    }

    @Override
    public List<UserDto> getAllUser() {

        return userRepository.findAll()
                .stream()
                .map(user -> mapper.userResponceFromEntityToDto(user))
                .toList();
    }


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("Такого пользователя нет"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }






    @Override
    public RegisterUserDto getCurrentUser(UserDetails userDetails) {
        User user = getUserByUsername(userDetails.getUsername());
        RegisterUserDto result = RegisterUserDto.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        return result;
    }

    @Override
    public RegisterUserDto updateCurrentUser(UserDetails userDetails, RegisterUserDto registerUserDto) {
        User user = getUserByUsername(userDetails.getUsername());
        System.out.println("1; " + registerUserDto);

        user.setUsername(registerUserDto.getUsername());
        user.setPassword(registerUserDto.getPassword());
        user.setFullName(registerUserDto.getFullName());
        userRepository.save(user);
        System.out.println(user);

        RegisterUserDto result = RegisterUserDto.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        return result;
    }

    @Override
    public RegisterUserDto deleteCurrentUser(UserDetails userDetails) {
        User user = getUserByUsername(userDetails.getUsername());
        RegisterUserDto result = RegisterUserDto.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        userRepository.delete(user);

        return result;
    }
}
