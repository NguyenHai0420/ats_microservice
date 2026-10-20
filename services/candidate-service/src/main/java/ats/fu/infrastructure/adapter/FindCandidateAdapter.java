package ats.fu.infrastructure.adapter;

import ats.fu.application.port.out.FindCandidatePort;
import ats.fu.domain.aggregate.CandidateAggregate;
import ats.fu.infrastructure.persistence.entity.CandidateEntity;
import ats.fu.infrastructure.persistence.repository.CandidateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindCandidateAdapter implements FindCandidatePort {
    private final CandidateJpaRepository candidateJpaRepository;

    @Override
    public Optional<CandidateAggregate> findById(Long id) {
        return Optional.of(toAggregate(candidateJpaRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Candidate not found");
        })));
    }

    private CandidateAggregate toAggregate(CandidateEntity candidate) {
        return CandidateAggregate.restore(
                candidate.getId(),
                candidate.getFullName(),
                candidate.getSource(),
                candidate.getUtmSource(),
                candidate.getUtmMedium(),
                candidate.getUtmCampaign(),
                candidate.getStatus(),
                candidate.getCvFileId(),
                candidate.getParsedCvData(),
                candidate.isDuplicate()
        );
    }
}
