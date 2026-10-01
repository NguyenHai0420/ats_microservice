package ats.fu.dto;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ApplicationResponse {
    private UUID id;
    private Long candidateId;
    private UUID jobId;
    private String cvFileUrl;
    private OffsetDateTime appliedAt;
}
