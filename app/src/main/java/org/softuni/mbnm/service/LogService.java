package org.softuni.mbnm.service;

import org.softuni.mbnm.domain.models.service.LogServiceModel;

public interface LogService {

    LogServiceModel seedLogInDB(LogServiceModel logServiceModel);
}
