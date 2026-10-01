package ats.fu.domain.repository;

import ats.fu.domain.aggregate.CandidateAggregate;

public interface CandidateRepository {
    CandidateAggregate save(CandidateAggregate aggregate);
}
