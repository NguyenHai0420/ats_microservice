package ats.fu.application.port.out;

import ats.fu.domain.aggregate.CandidateAggregate;

public interface SaveCandidatePort {
    CandidateAggregate save(CandidateAggregate aggregate);
}
