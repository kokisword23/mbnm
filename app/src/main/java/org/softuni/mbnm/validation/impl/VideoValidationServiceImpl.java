package org.softuni.mbnm.validation.impl;

import org.softuni.mbnm.domain.models.service.VideoServiceModel;
import org.softuni.mbnm.validation.VideoValidationService;
import org.springframework.stereotype.Component;

@Component
public class VideoValidationServiceImpl implements VideoValidationService {

    @Override
    public boolean isVideoValid(VideoServiceModel videoServiceModel) {
        return videoServiceModel != null;
    }
}
