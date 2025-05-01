package com.example.demo.category;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Object> findById(Long id);
}
