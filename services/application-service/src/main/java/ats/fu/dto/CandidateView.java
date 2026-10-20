package ats.fu.dto;

import lombok.Data;

@Data
public class CandidateView {
    private Long id;
    private String fullName;
    private String status;
    private String source;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private boolean duplicate;
    private Long activeCvId;
}
