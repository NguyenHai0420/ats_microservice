package ats.fu.application.service;

import ats.fu.application.command.CandidateCommand;
import ats.fu.application.port.in.CreateCandidatePort;
import ats.fu.domain.aggregate.CandidateAggregate;
import ats.fu.domain.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase implements CreateCandidatePort {
    private  final CandidateRepository candidateRepository;
    @Override
    public CandidateAggregate execute(CandidateCommand command) {
        // map cmd -> aggregate root
        CandidateAggregate aggregate = CandidateAggregate.create(command);

        // Business Validate

        // call doamin/repository
       return candidateRepository.save(aggregate);

    }
}
