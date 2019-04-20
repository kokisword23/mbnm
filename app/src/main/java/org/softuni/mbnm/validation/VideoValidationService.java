package org.softuni.mbnm.validation;

import org.softuni.mbnm.domain.models.service.VideoServiceModel;

public interface VideoValidationService {

    boolean isVideoValid(VideoServiceModel videoServiceModel);
}
