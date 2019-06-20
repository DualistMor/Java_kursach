package com.example.services.Repositories;

import com.example.services.Models.CoverArt;
import com.example.services.Models.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverArtRepository extends JpaRepository<CoverArt, Integer> {
}
