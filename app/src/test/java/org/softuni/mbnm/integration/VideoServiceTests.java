package org.softuni.mbnm.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.mbnm.domain.entities.Log;
import org.softuni.mbnm.domain.entities.Video;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.domain.models.service.VideoServiceModel;
import org.softuni.mbnm.repository.LogRepository;
import org.softuni.mbnm.repository.VideoRepository;
import org.softuni.mbnm.service.LogService;
import org.softuni.mbnm.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VideoServiceTests {

    @Autowired
    private LogService logService;

    @MockBean
    private LogRepository mockLogRepository;

    @Autowired
    private VideoService service;

    @MockBean
    private VideoRepository mockVideoRepository;


    @Test
    public void createVideo_whenValid_quoteCreated(){
        VideoServiceModel model = new VideoServiceModel();

        LogServiceModel log = new LogServiceModel();
        when(mockVideoRepository.save(any())).thenReturn(new Video());
        when(mockLogRepository.save(any())).thenReturn(new Log());

        service.createVideo(model);
        logService.seedLogInDB(log);
        verify(mockVideoRepository).save(any());
        verify(mockLogRepository).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createVideo_whenNull_throw() {
        service.createVideo(null);
        verify(mockVideoRepository)
                .save(any());
    }
}
