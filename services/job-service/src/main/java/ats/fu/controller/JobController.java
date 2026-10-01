package ats.fu.controller;

import ats.fu.dto.JobRequest;
import ats.fu.dto.JobResponse;
import ats.fu.dto.ResponseApi;
import ats.fu.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<ResponseApi> create(@Valid @RequestBody JobRequest jobRequest) {
        JobResponse jobResponse = jobService.save(jobRequest);

        return ResponseEntity.ok(
                ResponseApi
                        .builder()
                        .code(HttpStatus.OK)
                        .message("Job created successfully")
                        .data(jobResponse)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ResponseApi> read(@Valid @PathVariable(name = "id") UUID uuid) {
        return ResponseEntity.ok(
                ResponseApi
                        .builder()
                        .code(HttpStatus.ACCEPTED)
                        .message("")
                        .data(jobService.findById(uuid))
                        .build());
    }
}
