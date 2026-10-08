package ats.fu.application.service;

import ats.fu.application.command.CandidateCommand;
import ats.fu.application.port.in.CreateCandidatePort;
import ats.fu.application.port.out.SaveCandidatePort;
import ats.fu.domain.aggregate.CandidateAggregate;
import ats.fu.domain.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase implements CreateCandidatePort {
    private final SaveCandidatePort saveCandidatePort;

    @Override
    public CandidateAggregate execute(CandidateCommand command) {
        // map cmd -> aggregate root
        CandidateAggregate aggregate = CandidateAggregate.create(command);

        // Business Validate

        // call doamin/repository
       return saveCandidatePort.save(aggregate);
    }
}
