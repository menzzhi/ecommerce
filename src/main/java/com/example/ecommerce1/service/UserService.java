package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Address;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.*;
import com.example.ecommerce1.repository.AddressRepository;
import com.example.ecommerce1.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
                createUserRequest.usuario().senha());

        userRepository.save(user);

        Address address = new Address(
                user,
                createUserRequest.endereco().logradouro(),
                createUserRequest.endereco().cidade(),
                createUserRequest.endereco().cep(),
                createUserRequest.endereco().estado());

        user.setAddress(List.of(address));

        addressRepository.save(address);
    }

    public Page<UserResponse> getAllUsers(int pageNumber, Integer itemsNumber) {

        return userRepository.findAll(PageRequest.of(pageNumber, itemsNumber)).map(
                u -> new UserResponse(
                        u.getNome(),
                        u.getEmail(),
                        u.getAddress().stream().map(
                                address -> new AddressResponses(
                                        address.getLogradouro(),
                                        address.getEstado(),
                                        address.getCidade(),
                                        address.getCep())).toList()));
    }

    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado usuário na base de dados."));

        userRepository.delete(user);
    }

    public void updateEmailAndName(UserUpdate userUpdate, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado usuário na base de dados."));

        user.setEmail(userUpdate.email());
        user.setNome(userUpdate.nome());

        userRepository.save(user);
    }
}
