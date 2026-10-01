package ats.fu.api.rest;

import ats.fu.api.application.port.in.CreateCandidatePort;
import ats.fu.api.application.port.in.commands.CandidateCommand;
import ats.fu.api.dto.CandidateRequest;
import ats.fu.api.dto.CandidateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CreateCandidatePort createCandidatePort;

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(@Valid @RequestBody CandidateRequest request) {

        createCandidatePort.execute(new CandidateCommand(request.getFullName(), request.getSource(), request.getUtmSource(), request.getUtmMedium(), request.getUtmCampaign(), request.getUserId()));

        return ResponseEntity.ok(new CandidateResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(new CandidateResponse());
    }
}
