package com.artcoversapp.repositories;

import com.artcoversapp.entities.CoverArt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoverArtRepository extends JpaRepository<CoverArt, Integer> {
}
