package ats.fu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "cv_file_url", nullable = false, length = 1000)
    private String cvFileUrl;

    @Column(name = "applied_at")
    private OffsetDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = OffsetDateTime.now();
    }
}
