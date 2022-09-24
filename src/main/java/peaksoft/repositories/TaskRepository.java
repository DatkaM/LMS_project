package peaksoft.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select t " +
            "from Task t" +
            " where " +
            "upper(t.taskName) like concat('%',:text,'%') ")
    List<Task> getAllTasks(@Param("text") String text, Pageable pageable);

}
