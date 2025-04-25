package com.example.demo;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Object> findById(Long id);
}
