package ats.fu.domain.repository;

import ats.fu.domain.aggregate.CandidateAggregate;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository {
    Optional<CandidateAggregate> findByUserId(UUID userId);
}
