package org.softuni.mbnm.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.models.binding.VideoCreateBindingModel;
import org.softuni.mbnm.domain.models.service.VideoServiceModel;
import org.softuni.mbnm.domain.models.view.UserProfileViewModel;
import org.softuni.mbnm.domain.models.view.VideoDetailsViewModel;
import org.softuni.mbnm.error.VideoNotFoundException;
import org.softuni.mbnm.service.UserService;
import org.softuni.mbnm.service.VideoService;
import org.softuni.mbnm.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/videos")
public class VideoController extends BaseController {

    private final VideoService videoService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public VideoController(VideoService videoService, UserService userService, ModelMapper modelMapper) {
        this.videoService = videoService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Create Video")
    public ModelAndView create (Principal principal, ModelAndView modelAndView){
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        return super.view("video/create-video");
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createConfirm (@ModelAttribute VideoCreateBindingModel model){

        this.videoService.createVideo(this.modelMapper.map(model, VideoServiceModel.class));

        return super.redirect("/home");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Videos")
    public ModelAndView showAll(ModelAndView modelAndView){
        List<VideoServiceModel> videos = this.videoService.findAllVideos()
                .stream()
                .map(v -> this.modelMapper.map(v,VideoServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("videos", videos);
        return super.view("video/all-videos", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Video Details")
    public ModelAndView detailsVideo(@PathVariable String id, ModelAndView modelAndView) {
        VideoServiceModel video = this.videoService.findVideoById(id);
        modelAndView.addObject("video",video);
        return super.view("video/details-video", modelAndView);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Delete Video")
    public ModelAndView deleteVideo(@PathVariable String id, ModelAndView modelAndView) {
        VideoServiceModel videoServiceModel = this.videoService.findVideoById(id);

        modelAndView.addObject("video", videoServiceModel);
        modelAndView.addObject("videoId", id);

        return super.view("video/delete-video", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteVideoConfirm(@PathVariable String id) {
        this.videoService.deleteVideo(id);

        return super.redirect("/videos/all");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit Video")
    public ModelAndView editVideo(@PathVariable String id, ModelAndView modelAndView){
        VideoServiceModel videoServiceModel = this.videoService.findVideoById(id);

        modelAndView.addObject("video", videoServiceModel);
        modelAndView.addObject("videoId", id);

        return super.view("video/edit-video",modelAndView);

    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editVideoConfirm(@PathVariable String id,@ModelAttribute VideoCreateBindingModel model){
        this.videoService.editVideo(id, this.modelMapper.map(model, VideoServiceModel.class));

        return super.redirect("/videos/details/"+id);
    }

    @ExceptionHandler({VideoNotFoundException.class})
    public ModelAndView handleVideoNotFound(VideoNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatus());

        return modelAndView;
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
