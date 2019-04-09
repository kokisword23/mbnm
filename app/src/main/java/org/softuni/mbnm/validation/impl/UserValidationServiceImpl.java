package org.softuni.mbnm.validation.impl;

import org.softuni.mbnm.domain.models.service.UserServiceModel;
import org.softuni.mbnm.validation.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isUserValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }
}
