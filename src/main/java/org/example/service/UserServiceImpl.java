package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;
    final private Mapper mapper;

    @Override
    public UserDto crateUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .fullName(userDto.getFullName())
                .role(userDto.getRole())
                .build();
        userRepository.save(user);
        return null;
    }

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
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return mapper.userFromEntityToDto(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User does not exists!!"));
    }
}
