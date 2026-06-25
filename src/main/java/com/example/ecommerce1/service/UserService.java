package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Address;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.AddressRequest;
import com.example.ecommerce1.dto.CreateUserRequest;
import com.example.ecommerce1.dto.UserRequest;
import com.example.ecommerce1.repository.AddressRepository;
import com.example.ecommerce1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
