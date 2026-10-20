package ats.fu.api.rest;

import ats.fu.api.dto.CandidateRequest;
import ats.fu.api.dto.CandidateResponse;
import ats.fu.application.command.CandidateCommand;
import ats.fu.application.port.in.CreateCandidateUseCase;
import ats.fu.application.port.in.FindCandidateUseCase;
import ats.fu.application.query.CandidateQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CreateCandidateUseCase createCandidatePort;
    private final FindCandidateUseCase findCandidateUseCase;

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(@Valid @RequestBody CandidateRequest request) {

        CandidateResponse response = createCandidatePort.execute(new CandidateCommand(
                request.getFullName(),
                request.getSource(),
                request.getUtmSource(),
                request.getUtmMedium(),
                request.getUtmCampaign(),
                request.getUserId()));

        return ResponseEntity.created(URI.create("/api/v1/candidates/" + response.getId())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable("id") Long id) {
        CandidateResponse response = findCandidateUseCase.execute(new CandidateQuery(id));
        return ResponseEntity.ok(response);
    }
}
