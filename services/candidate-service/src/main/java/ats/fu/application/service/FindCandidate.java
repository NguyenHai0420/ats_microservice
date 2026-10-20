package ats.fu.application.service;

import ats.fu.api.dto.CandidateResponse;
import ats.fu.application.mapper.CandidateResponseMapper;
import ats.fu.application.port.in.FindCandidateUseCase;
import ats.fu.application.port.out.FindCandidatePort;
import ats.fu.application.query.CandidateQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCandidate implements FindCandidateUseCase {
    private final FindCandidatePort findCandidatePort;

    @Override
    public CandidateResponse execute(CandidateQuery query) {
        return CandidateResponseMapper.toResponse(findCandidatePort.findById(query.id()).orElseThrow(() -> {
            throw new IllegalArgumentException("Candidate not found with id " + query.id());
        }));
    }
}
