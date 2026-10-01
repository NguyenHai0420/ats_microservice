package ats.fu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class ApplicationRequest {
    @NotNull(message = "Candidate ID is required")
    private Long candidateId;

    @NotNull(message = "Job ID is required")
    private UUID jobId;

    @NotBlank(message = "CV File URL is required")
    private String cvFileUrl;
}
