package ru.maxima.libra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxima.libra.models.ImageData;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData,Long> {

    Optional<ImageData> findByName(String fileName);
}