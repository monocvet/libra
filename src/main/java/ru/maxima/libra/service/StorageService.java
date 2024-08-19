package ru.maxima.libra.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.maxima.libra.dto.BookDTO;
import ru.maxima.libra.dto.ImageDTO;
import ru.maxima.libra.models.Book;
import ru.maxima.libra.models.ImageData;
import ru.maxima.libra.repositories.StorageRepository;
import ru.maxima.libra.util.ImageUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepository repository;
    private ModelMapper modelMapper;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
