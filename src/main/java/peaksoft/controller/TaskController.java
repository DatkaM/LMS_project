package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.TaskRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.TaskResponse;
import peaksoft.dto.search.TaskResponseView;
import peaksoft.service.TaskService;

@RestController
@RequestMapping("api/task")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse saveTask(@RequestBody TaskRequest request) {
        return taskService.saveTask(request);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public TaskResponse getTaskResponseById(@PathVariable Long id) {
        return taskService.getTaskResponseById(id);
    }

    @PutMapping("{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                                   @RequestBody TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("{id}")
    public SimpleResponse deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/getAllTasks")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public TaskResponseView search(@RequestParam(name = "text",required = false) String text,
                                   @RequestParam int page,
                                   @RequestParam int size){
        return taskService.search(text, page, size);
    }

}
