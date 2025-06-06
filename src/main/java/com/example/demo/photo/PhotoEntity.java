package com.example.demo.photo;


import com.example.demo.component.BaseMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "photo")
public class PhotoEntity extends BaseMapper {

    private byte[] data;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            insertable = false,
            updatable = false)
    private PhotoEntity photoEntity;

    @Column(name = "product_id")
    private UUID productId;
}