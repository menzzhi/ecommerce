package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Address;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.*;
import com.example.ecommerce1.repository.AddressRepository;
import com.example.ecommerce1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository,
                       AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public void createUser(CreateUserRequest createUserRequest) {
        User user = new User(
                createUserRequest.usuario().nome(),
                createUserRequest.usuario().email(),
                createUserRequest.usuario().senha(),
                new ArrayList<>());

        User repositoryUser = userRepository.save(user);

        Address address = new Address(
                repositoryUser,
                createUserRequest.endereco().logradouro(),
                createUserRequest.endereco().cidade(),
                createUserRequest.endereco().cep(),
                createUserRequest.endereco().estado());

        addressRepository.save(address);
    }

    public List<UserResponse> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(u -> new UserResponse(u.getNome(), u.getEmail()))
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userRepository.delete(user);
    }

    public UserUpdate updateEmailAndName(UserUpdate userUpdate, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setEmail(userUpdate.email());
        user.setNome(userUpdate.nome());

        User updatedUser = userRepository.save(user);

        return new UserUpdate(updatedUser.getNome(), updatedUser.getEmail());
    }
}
