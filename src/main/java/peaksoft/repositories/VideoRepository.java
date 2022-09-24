package peaksoft.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Video;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {

    @Query("select v from Video v where upper(v.videoName) like concat('%',:text,'%') ")
    List<Video> getAllVideo(@Param("text") String text, Pageable pageable);

}
