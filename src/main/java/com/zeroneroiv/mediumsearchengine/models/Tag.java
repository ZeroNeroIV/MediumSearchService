package com.zeroneroiv.mediumsearchengine.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Todo
}
