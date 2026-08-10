package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Address;
import com.example.ecommerce1.domain.Role;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.*;
import com.example.ecommerce1.repository.AddressRepository;
import com.example.ecommerce1.repository.RoleRepository;
import com.example.ecommerce1.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       AddressRepository addressRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public void createUser(CreateUserRequest createUserRequest) {
        String passwordEncoded =
                bCryptPasswordEncoder.encode(createUserRequest.usuario().senha());

        Role role = roleRepository.findById(2L).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = new User(
                createUserRequest.usuario().nome(),
                createUserRequest.usuario().email(),
                passwordEncoded,
                List.of(role));

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

    public void updateEmailAndName(UserUpdate userUpdate, JwtAuthenticationToken token) {
        User user = userRepository.findByEmail(token.getName()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado usuário na base de dados."));

        user.setEmail(userUpdate.email());
        user.setNome(userUpdate.nome());

        userRepository.save(user);
    }
}
