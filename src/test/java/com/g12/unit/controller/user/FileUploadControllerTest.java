package com.g12.unit.controller.user;

import com.g12.controller.user.FileUploadController;
import com.g12.result.Result;
import com.g12.util.AliOssUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FileUploadControllerTest {

    @Mock
    private AliOssUtil aliOssUtil;

    @InjectMocks
    private FileUploadController fileUploadController;

    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        mockFile = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );
    }

    @Test
    void uploadAvatar_ShouldUploadSuccessfully() throws IOException {
        // 准备测试数据
        String fileUrl = "https://example.com/uploads/test.jpg";
        when(aliOssUtil.upload(any(byte[].class), anyString())).thenReturn(fileUrl);

        // 执行测试
        Result result = fileUploadController.uploadAvatar(mockFile);

        // 验证结果
        verify(aliOssUtil).upload(any(byte[].class), anyString());
        assertNotNull(result);
        assertEquals(1, result.getCode());
        assertEquals(fileUrl, result.getData());
    }
}