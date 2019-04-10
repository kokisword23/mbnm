package org.softuni.mbnm.service;

import org.softuni.mbnm.domain.models.service.VideoServiceModel;

import java.util.List;

public interface VideoService {

    VideoServiceModel createVideo(VideoServiceModel videoServiceModel);

    VideoServiceModel findVideoByTitle(String title);

    List<VideoServiceModel> findAllVideos();

    VideoServiceModel findVideoById(String id);

    void deleteVideo(String id);

    VideoServiceModel editVideo(String id, VideoServiceModel videoServiceModel);

}
