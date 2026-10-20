package ats.fu.application.service;

import ats.fu.api.dto.CandidateResponse;
import ats.fu.application.command.CandidateCommand;
import ats.fu.application.mapper.CandidateResponseMapper;
import ats.fu.application.port.in.CreateCandidateUseCase;
import ats.fu.application.port.out.SaveCandidatePort;
import ats.fu.domain.aggregate.CandidateAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidate implements CreateCandidateUseCase {
    private final SaveCandidatePort saveCandidatePort;

    @Override
    public CandidateResponse execute(CandidateCommand command) {
        // map cmd -> aggregate root
        CandidateAggregate aggregate = CandidateAggregate.create(command);

        // Business Validate

        // call domain/repository
        CandidateAggregate savedAggregate = saveCandidatePort.save(aggregate);

        return CandidateResponseMapper.toResponse(savedAggregate);
    }
}
