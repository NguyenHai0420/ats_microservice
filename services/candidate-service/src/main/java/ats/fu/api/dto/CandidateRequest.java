package ats.fu.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRequest {
    @NotBlank(message = "Full name must not be blank")
    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    @Size(max = 150, message = "Source must not exceed 150 characters")
    private String source;

    @Size(max = 150, message = "UTM source must not exceed 150 characters")
    private String utmSource;

    @Size(max = 150, message = "UTM medium must not exceed 150 characters")
    private String utmMedium;

    @Size(max = 255, message = "UTM campaign must not exceed 255 characters")
    private String utmCampaign;

    private UUID userId;
}
