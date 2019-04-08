package org.softuni.mbnm.service;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Log;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public LogServiceModel seedLogInDB(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        return this.modelMapper.map(this.logRepository.saveAndFlush(log),LogServiceModel.class);
    }
}
