package peaksoft.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Instructor;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {

    @Query("select i " +
            "from Instructor i" +
            " where " +
            "upper(i.firstName) like concat('%',:text,'%')" +
            "or upper(i.lastName) like concat('%',:text,'%')")
    List<Instructor> getAll(@Param("text")String text, Pageable pageable);

}
