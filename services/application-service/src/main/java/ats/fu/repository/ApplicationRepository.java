package ats.fu.repository;

import ats.fu.entity.Application;
import ats.fu.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByJobIdAndCandidateIdAndStatusNot(UUID jobId, Long candidateId, ApplicationStatus status);
}
