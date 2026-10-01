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
                .utmMedium(aggregate.getUtmMedium())
                .utmCampaign(aggregate.getUtmCampaign())
                .status(aggregate.getStatus())
                .userId(aggregate.getUserId())
                .build();

        candidateJpaRepository.save(candidateEntity);

        return aggregate;
    }
