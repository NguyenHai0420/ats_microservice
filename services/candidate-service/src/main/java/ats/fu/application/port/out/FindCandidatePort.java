package ats.fu.application.port.out;

import ats.fu.domain.aggregate.CandidateAggregate;

import java.util.Optional;

public interface FindCandidatePort {
    Optional<CandidateAggregate> findById(Long id);
}
