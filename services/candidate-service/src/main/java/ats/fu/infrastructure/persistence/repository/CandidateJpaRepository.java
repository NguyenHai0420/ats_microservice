package ats.fu.infrastructure.persistence.repository;

import ats.fu.domain.aggregate.CandidateAggregate;
import ats.fu.infrastructure.persistence.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateJpaRepository extends JpaRepository<CandidateEntity, UUID> {
}
