package peaksoft.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select l " +
            "from Lesson l " +
            "where " +
            "upper(l.lessonName) like concat('%',:text,'%') ")
    List<Lesson> getAllLesson(@Param("text") String text, Pageable pageable);

}
