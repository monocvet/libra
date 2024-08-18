package ru.maxima.libra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.libra.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIdAndRemovedIsFalse(Long id);
    List<Book> findAllByRemovedIsFalseOrderById();
    byte[] findBookByImage_Id(Long id);
}
