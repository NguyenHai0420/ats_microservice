package ats.fu.controller;

import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;
import ats.fu.dto.ResponseApi;
import ats.fu.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final ApplicationService applicationService;

//    @PostMapping
//    public ResponseEntity<ResponseApi> create(@Valid @RequestBody ApplicationRequest request) {
//        ApplicationResponse response = applicationService.save(request);
//
//        return ResponseEntity.ok(
//                ResponseApi
//                        .builder()
//                        .code(HttpStatus.OK)
//                        .message("Application created successfully")
//                        .data(response)
//                        .build());
//    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> apply(@Valid @RequestBody ApplicationRequest request,
                                                     @RequestHeader(value = "X-User-ID") String userId,
                                                     @RequestHeader(value = "X-User-Email") String email,
                                                     @RequestHeader(value = "X-User-Name") String userName
    ) {

        log.info("User Id {}, User email {}, name {}", userId, email, userName);

        ApplicationResponse response = applicationService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/applications/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> read(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(applicationService.findById(id));
    }
}
