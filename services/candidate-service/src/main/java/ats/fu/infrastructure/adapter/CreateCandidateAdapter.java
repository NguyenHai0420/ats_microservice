package ats.fu.infrastructure.adapter;

import ats.fu.application.port.out.SaveCandidatePort;
import ats.fu.domain.aggregate.CandidateAggregate;
import ats.fu.infrastructure.persistence.entity.CandidateEntity;
import ats.fu.infrastructure.persistence.entity.CandidateStatus;
import ats.fu.infrastructure.persistence.repository.CandidateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCandidateAdapter implements SaveCandidatePort {
    private final CandidateJpaRepository candidateJpaRepository;

    @Override
    public CandidateAggregate save(CandidateAggregate aggregate) {
        // map aggregate to Entity

        CandidateEntity candidateEntity = CandidateEntity.builder()
                .fullName(aggregate.getFullName())
                .source(aggregate.getSource())
                .utmSource(aggregate.getUtmSource())
                .utmMedium(aggregate.getUtmMedium())
                .utmCampaign(aggregate.getUtmCampaign())
                .status(aggregate.getStatus())
                .userId(aggregate.getUserId())
                .build();

        CandidateEntity savedEntity = candidateJpaRepository.save(candidateEntity);

        return CandidateAggregate.restore(
                savedEntity.getId(),
                savedEntity.getFullName(),
                savedEntity.getSource(),
                savedEntity.getUtmSource(),
                savedEntity.getUtmMedium(),
                savedEntity.getUtmCampaign(),
                savedEntity.getStatus(),
                savedEntity.getCvFileId(),
                savedEntity.getParsedCvData(),
                savedEntity.isDuplicate()
        );
    }
}
