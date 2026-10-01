package ats.fu.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {
    private Long id;
    private String fullName;
    private String status;
    private String source;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private boolean duplicate;
    private Long activeCvId;
    private ParsedCvDataResponse parsedCvData;
}
