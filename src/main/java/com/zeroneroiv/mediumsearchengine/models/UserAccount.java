package com.zeroneroiv.mediumsearchengine.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Todo
}
