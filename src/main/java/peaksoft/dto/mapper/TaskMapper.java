package peaksoft.dto.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.request.TaskRequest;
import peaksoft.dto.response.TaskResponse;
import peaksoft.entity.Task;

@Component
public class TaskMapper {

    public Task mapToEntity(TaskRequest request) {
        return Task.builder()
                .taskName(request.getTaskName())
                .taskText(request.getTaskText())
                .deadLine(request.getDeadLine())
                .build();
    }

    public TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .taskText(task.getTaskText())
                .lessonId(task.getLesson().getId())
                .build();
    }

    public Task updateTask(Task task, TaskRequest request) {
        task.setTaskName(request.getTaskName());
        task.setTaskText(request.getTaskText());
        task.setDeadLine(request.getDeadLine());
        return task;
    }
}
