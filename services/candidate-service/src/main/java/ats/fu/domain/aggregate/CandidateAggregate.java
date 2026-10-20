package ats.fu.domain.aggregate;

import ats.fu.application.command.CandidateCommand;

import ats.fu.infrastructure.persistence.entity.CandidateStatus;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CandidateAggregate {
    private Long id;
    private String fullName;
    private String source;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private CandidateStatus status;
    private UUID userId;
    private Long activeCvId;
    private ats.fu.infrastructure.persistence.entity.ParsedCvData parsedCvData;
    private boolean duplicate;

    private CandidateAggregate() {

    }

    public static CandidateAggregate create(CandidateCommand command) {
        List<String> sourceValues = List.of("Facebook", "LinkedIn", "VietnamWorks", "Zalo");
        CandidateAggregate candidateAggregate = new CandidateAggregate();
        candidateAggregate.fullName = command.fullName();
        candidateAggregate.source = command.source();

        if (command.source() == null) {
            throw new IllegalArgumentException("Source is null");
        }
        if (!sourceValues.contains(command.source())) {
            throw new IllegalArgumentException("Source is not valid");
        }

        candidateAggregate.utmSource = command.utmSource();
        candidateAggregate.utmMedium = command.utmMedium();
        candidateAggregate.utmCampaign = command.utmCampaign();
        candidateAggregate.status = CandidateStatus.ACTIVE;
        if(command.userId() == null) {
            throw  new IllegalArgumentException("User id is null");
        }
        candidateAggregate.userId = command.userId();
        return candidateAggregate;
    }

    public static CandidateAggregate restore(Long id,
                                             String fullName,
                                             String source,
                                             String utmSource,
                                             String utmMedium,
                                             String utmCampaign,
                                             CandidateStatus status,
                                             Long activeCvId,
                                             ats.fu.infrastructure.persistence.entity.ParsedCvData parsedCvData,
                                             boolean duplicate) {
        CandidateAggregate candidateAggregate = new CandidateAggregate();
        candidateAggregate.id = id;
        candidateAggregate.fullName = fullName;
        candidateAggregate.source = source;
        candidateAggregate.utmSource = utmSource;
        candidateAggregate.utmMedium = utmMedium;
        candidateAggregate.utmCampaign = utmCampaign;
        candidateAggregate.status = status;
        candidateAggregate.activeCvId = activeCvId;
        candidateAggregate.parsedCvData = parsedCvData;
        candidateAggregate.duplicate = duplicate;

        return candidateAggregate;
    }
}
