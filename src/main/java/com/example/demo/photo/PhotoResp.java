package com.example.demo.photo;

import java.util.UUID;

public record PhotoResp(
        UUID id,
        String name,
        UUID productId
) {
}
