package peaksoft.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Lesson {

    @Id
    @SequenceGenerator(name = "lesson_gen", sequenceName = "lesson_seq", allocationSize = 1)
    @GeneratedValue(generator = "lesson_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "lesson_name")
    private String lessonName;

    public Lesson(String lessonName, Course course) {
        this.lessonName = lessonName;
        this.course = course;
    }

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    private Course course;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lesson")
    private List<Task> tasks;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lesson")
    private Video video;


    public Lesson(String lessonName) {
        this.lessonName = lessonName;
    }
    public void addTask(Task task){
        if (tasks==null){
            tasks=new ArrayList<>();
        }
        tasks.add(task);
    }
}
