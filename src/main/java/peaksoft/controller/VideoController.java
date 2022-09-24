package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.VideoRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.VideoResponse;
import peaksoft.dto.search.VideoResponseView;
import peaksoft.service.VideoService;

@RestController
@RequestMapping("api/video")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('INSTRUCTOR')")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public VideoResponse saveVideo(@RequestBody VideoRequest request) {
        return videoService.saveVideo(request);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public VideoResponse getVideoById(@PathVariable Long id) {
        return videoService.getVideoResponseById(id);
    }

    @PutMapping("{id}")
    public VideoResponse updateVideo(@PathVariable Long id,
                                     @RequestBody VideoRequest request) {
        return videoService.updateVideo(id, request);
    }

    @DeleteMapping("{id}")
    public SimpleResponse deleteVideo(@PathVariable Long id) {
        return videoService.deleteVideo(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public VideoResponseView search(@RequestParam(name = "text", required = false) String text,
                                    @RequestParam int page,
                                    @RequestParam int size) {
        return videoService.search(text, page, size);
    }
}
