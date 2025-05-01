package com.example.demo.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    // 1. Photo ro‘yxatini olish (productId bo‘yicha)
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByProductId(@PathVariable String productId) {
        return ResponseEntity.ok(photoService.getPhotosByProductId(productId));
    }

    // 2. Photo'ni o‘chirish
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable UUID id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }

    // 3. Photo'ni nomi bo‘yicha qidirish
    @GetMapping("/search")
    public ResponseEntity<List<PhotoDTO>> searchPhotosByName(@RequestParam String name) {
        return ResponseEntity.ok(photoService.searchPhotosByName(name));
    }

    // 4. Photo metadata'sini olish
    @GetMapping("/metadata/{id}")
    public ResponseEntity<PhotoDTO> getPhotoMetadata(@PathVariable UUID id) {
        return ResponseEntity.ok(photoService.getPhotoMetadata(id));
    }

    // 5. Bir nechta rasm yuklash
    @PostMapping("/multi-upload")
    public ResponseEntity<List<PhotoDTO>> uploadMultiplePhotos(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("productId") String productId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(photoService.uploadMultiplePhotos(files, productId));
    }
}