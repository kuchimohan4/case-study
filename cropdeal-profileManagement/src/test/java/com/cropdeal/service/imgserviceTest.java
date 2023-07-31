package com.cropdeal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cropdeal.entites.ImageModel;
import com.cropdeal.repositry.imagerepostyry;
import com.cropdeal.util.imageutil;

public class imgserviceTest {
    private imgservice imgService;
    private imagerepostyry imageRepository;

    @BeforeEach
    public void setUp() {
        imageRepository = mock(imagerepostyry.class);
        imgService = new imgservice();
        imgService.setImageRepository(imageRepository);
    }

    @Test
    public void testUploadimg() throws IOException {
        // Arrange
        MultipartFile mockFile = new MockMultipartFile("test.png", new byte[] { 1, 2, 3 });
        ImageModel savedImageModel = ImageModel.builder()
                .name(mockFile.getOriginalFilename())
                .type(mockFile.getContentType())
                .picByte(imageutil.compressImage(mockFile.getBytes()))
                .build();
        when(imageRepository.save(any(ImageModel.class))).thenReturn(savedImageModel);

        // Act
        String result = imgService.uploadimg(mockFile);

        // Assert
        assertEquals("file uploaded", result);
    }

    @Test
    public void testDownloadimg() throws IOException {
        // Arrange
        String imageName = "test.png";
         byte[]  imgBite=new byte[] { 1, 2, 3 };
        byte[] compressedImageData = imageutil.compressImage(imgBite);
        ImageModel imageModel = ImageModel.builder()
                .name(imageName)
                .type("image/png")
                .picByte(compressedImageData)
                .build();
        when(imageRepository.findByName(imageName)).thenReturn(Optional.of(imageModel));

        // Act
        byte[] result = imgService.downloadimg(imageName);

        // Assert
        assertEquals(compressedImageData[0], imageutil.compressImage(result)[0]);
    }

}
