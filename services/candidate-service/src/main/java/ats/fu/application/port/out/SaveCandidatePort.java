package ats.fu.application.port.out;

import ats.fu.domain.aggregate.CandidateAggregate;
import ats.fu.domain.repository.CandidateRepository;

public interface SaveCandidatePort extends CandidateRepository {
    CandidateAggregate save(CandidateAggregate aggregate);
}
