package org.softuni.mbnm.service;

import org.softuni.mbnm.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);

    UserServiceModel findUserById(String id);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> findAllUsers();

    void deleteUser(String id);

    void setUserRole(String id, String role);
}
