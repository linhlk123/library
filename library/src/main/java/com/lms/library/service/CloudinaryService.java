package com.lms.library.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private static final Set<String> ALLOWED_IMAGE_FORMATS = new HashSet<>(
            Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "bmp", "svg", "ico", "tiff"));

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    private void validateImageFormat(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_IMAGE_FORMATS.contains(extension)) {
            throw new IllegalArgumentException("Unsupported image format. " +
                    "Please use: " + String.join(", ", ALLOWED_IMAGE_FORMATS));
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        validateImageFormat(file);

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        return uploadResult.get("url").toString();
    }
}