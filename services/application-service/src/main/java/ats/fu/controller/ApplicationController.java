package ats.fu.controller;

import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;
import ats.fu.dto.ResponseApi;
import ats.fu.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ResponseApi> create(@Valid @RequestBody ApplicationRequest request) {
        ApplicationResponse response = applicationService.save(request);

        return ResponseEntity.ok(
                ResponseApi
                        .builder()
                        .code(HttpStatus.OK)
                        .message("Application created successfully")
                        .data(response)
                        .build());
    }
}
