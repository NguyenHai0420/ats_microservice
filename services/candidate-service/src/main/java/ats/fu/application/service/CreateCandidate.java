package ats.fu.application.service;

import ats.fu.application.command.CandidateCommand;
import ats.fu.application.port.out.SaveCandidatePort;
import ats.fu.domain.aggregate.CandidateAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase implements ats.fu.application.port.in.CreateCandidateUseCase {
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
