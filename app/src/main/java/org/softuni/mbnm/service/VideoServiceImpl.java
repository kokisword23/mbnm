package org.softuni.mbnm.service;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Video;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.domain.models.service.VideoServiceModel;
import org.softuni.mbnm.error.VideoNotFoundException;
import org.softuni.mbnm.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private final LogService logService;
    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public VideoServiceImpl(LogService logService, VideoRepository videoRepository, ModelMapper modelMapper) {
        this.logService = logService;
        this.videoRepository = videoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoServiceModel createVideo(VideoServiceModel videoServiceModel) {
        Video video = this.modelMapper.map(videoServiceModel, Video.class);

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(videoServiceModel.getUploader());
        logServiceModel.setDescription(videoServiceModel.getTitle() +" - video created");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);


        return this.modelMapper.map(this.videoRepository.saveAndFlush(video), VideoServiceModel.class);
    }

    @Override
    public VideoServiceModel findVideoByTitle(String title) {
        return this.videoRepository.findByTitle(title)
                .map(v -> this.modelMapper.map(v, VideoServiceModel.class))
                .orElseThrow(() -> new VideoNotFoundException("Nqq video s takuv title"));
    }

    @Override
    public List<VideoServiceModel> findAllVideos() {
        return this.videoRepository.findAll()
                .stream()
                .map(v -> this.modelMapper.map(v, VideoServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public VideoServiceModel findVideoById(String id) {
        return this.videoRepository.findById(id)
                .map(v -> this.modelMapper.map(v, VideoServiceModel.class))
                .orElseThrow(() -> new VideoNotFoundException("Nqq video s takova id"));
    }

    @Override
    public void deleteVideo(String id) {
        Video video = this.videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Nqma takova video"));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(video.getUploader());
        logServiceModel.setDescription(video.getTitle() + " - video is deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.videoRepository.delete(video);
    }

    @Override
    public VideoServiceModel editVideo(String id, VideoServiceModel videoServiceModel) {
        Video video = this.videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Nqq takova video"));

        video.setTitle(videoServiceModel.getTitle());
        video.setUploader(videoServiceModel.getUploader());
        video.setUrl(videoServiceModel.getUrl());
        video.setDescription(videoServiceModel.getDescription());

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(videoServiceModel.getUploader());
        logServiceModel.setDescription("Video edited");
        logServiceModel.setTime(LocalDateTime.now());

        return this.modelMapper.map(this.videoRepository.saveAndFlush(video), VideoServiceModel.class);
    }
}
