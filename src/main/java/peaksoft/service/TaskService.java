package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.TaskMapper;
import peaksoft.dto.request.TaskRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.TaskResponse;
import peaksoft.dto.search.TaskResponseView;
import peaksoft.entity.Lesson;
import peaksoft.entity.Task;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.LessonRepository;
import peaksoft.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;
    private final TaskMapper mapper;

    public TaskResponse saveTask(TaskRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Lesson with %d id not found", request.getLessonId())
                )
        );

        Task task = mapper.mapToEntity(request);
        task.setLesson(lesson);
        lesson.addTask(task);
        return mapper.mapToResponse(taskRepository.save(task));
    }

    public TaskResponse getTaskResponseById(Long id) {
        return mapper.mapToResponse(taskRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Task with %d id not found", id)))
        );
    }

    public SimpleResponse deleteTask(Long id) {
        boolean exists = taskRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Task with %d id not found", id)
            );
        }
        taskRepository.deleteById(id);
        return new SimpleResponse(
                "DELETE",
                String.format("Task with %d id successfully deleted", id));
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = getTaskById(id);
        Lesson lesson = getLesById(request.getLessonId());
        if (!request.getLessonId().equals(task.getLesson().getId())) {
            throw new BadRequestException(
                    "Task's lesson id and task request's lesson id are different"
            );
        }

        lesson.addTask(task);
        task.setLesson(lesson);
        Task task1 = mapper.updateTask(task,request);
        return mapper.mapToResponse(taskRepository.save(task1));
    }

    public TaskResponseView search(String text, int page, int size) {
        TaskResponseView view = new TaskResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        view.setTaskResponses(getTaskResponses(getTasks(text,pageable)));
        return view;
    }

    private List<TaskResponse> getTaskResponses(List<Task> tasks) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            taskResponses.add(mapper.mapToResponse(task));
        }
        return taskResponses;
    }

    private List<Task> getTasks(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return taskRepository.getAllTasks(name.toUpperCase(),pageable);
    }

    private Lesson getLesById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format(
                        "Lesson with %d id not found", id)
                )
        );
    }

    private Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format(
                        "Task with %d id not found",id))
        );
    }


}
