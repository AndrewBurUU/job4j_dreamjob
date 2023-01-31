package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.File;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.FileService;

import java.util.*;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class FileControllerTest {

    private FileService fileService;

    private FileController fileController;

    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
    }

    @Disabled
    @Test
    public void whenRequestFileThenGetFile() throws Exception {
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        when(fileService.getFileById(1)).thenReturn(Optional.of(fileDto));

        var model = new ConcurrentModel();
        var view = fileController.getById(1);
        var actualFileDto = view.getBody();

        assertThat(view).isEqualTo("200 OK OK,[B@224b4d61,[]");
/**        assertThat(fileDto).usingRecursiveComparison().isEqualTo(view);*/
    }
}