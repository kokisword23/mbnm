package org.softuni.mbnm.validation;

import org.softuni.mbnm.domain.models.service.UserServiceModel;

public interface UserValidationService {

    boolean isUserValid(UserServiceModel userServiceModel);
}
