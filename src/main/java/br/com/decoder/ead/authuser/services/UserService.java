package br.com.decoder.ead.authuser.services;

import br.com.decoder.ead.authuser.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAllUsers();


    Optional<UserModel> getById(UUID userId);


    void deleteUser(UserModel userModel);

    void save(UserModel userModel);

    boolean existByUserName(String username);

    boolean existByEmail(String email);

}
