package com.example.demo.photo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    // 1. Photo ro‘yxatini olish (productId bo‘yicha)
    public List<PhotoDTO> getPhotosByProductId(String productId) {
        return photoRepository.findByProductId(productId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 2. Photo'ni o‘chirish
    public void deletePhoto(UUID id) {
        if (!photoRepository.existsById(id)) {
            throw new RuntimeException("Photo not found");
        }
        photoRepository.deleteById(id);
    }

    // 3. Photo'ni nomi bo‘yicha qidirish
    public List<PhotoDTO> searchPhotosByName(String name) {
        return photoRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 4. Photo metadata'sini olish
    public PhotoDTO getPhotoMetadata(UUID id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        return mapToDTO(photo);
    }

    // 5. Bir nechta rasm yuklash
    public List<PhotoDTO> uploadMultiplePhotos(List<MultipartFile> files, String productId) {
        List<Photo> photos = files.stream().map(file -> Photo.builder()
                        .name(file.getOriginalFilename())
                        .productId(productId)
                        .visibility(true)
                        .build())
                .collect(Collectors.toList());

        photoRepository.saveAll(photos);
        return photos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PhotoDTO mapToDTO(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setName(photo.getName());
        dto.setProductId(photo.getProductId());
        dto.setCreatedAt(photo.getCreatedAt() != null ? photo.getCreatedAt().toString() : null);
        dto.setUpdatedAt(photo.getUpdatedAt() != null ? photo.getUpdatedAt().toString() : null);
        dto.setVisibility(photo.getVisibility());
        return dto;
    }
}