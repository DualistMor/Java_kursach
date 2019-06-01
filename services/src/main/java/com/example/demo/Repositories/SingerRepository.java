package com.example.demo.Repositories;

import com.example.demo.Models.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepository extends JpaRepository<Singer, Integer> {
}
