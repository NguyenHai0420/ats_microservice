package ats.fu.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class JobView {
    private UUID id;
    private UUID departmentId;
    private String title;

    private String description;

    private String location;
    private String status;

    private OffsetDateTime deadline;
}
