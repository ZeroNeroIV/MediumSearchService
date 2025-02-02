package com.zeroneroiv.mediumsearchengine.models;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Todo
}
