package org.softuni.mbnm.domain.models.view;

public class VideoDetailsViewModel {

    private String title;

    private String uploader;

    private String url;

    private String description;

    public VideoDetailsViewModel() {
    }

    public VideoDetailsViewModel(String title, String uploader, String url, String description) {
        this.title = title;
        this.uploader = uploader;
        this.url = url;
        this.description = description;
    }
}
