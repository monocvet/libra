package ru.maxima.libra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.maxima.libra.models.Book;
import ru.maxima.libra.models.ImageData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {
    List<Book> findAllByRemovedIsFalseOrderById();
    @Query(nativeQuery = true, value = "select imagedata from image join book on image.id = book.image_id where book.id = :bookid")
    Optional<ImageData> findImageByBookId(@Param("bookid")Long id);
}
