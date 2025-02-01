package com.zeroneroiv.mediumsearchengine.repositories;

import com.zeroneroiv.mediumsearchengine.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
