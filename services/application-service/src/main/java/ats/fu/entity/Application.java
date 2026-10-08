package ats.fu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(
        name = "applications",
        indexes = {
                @Index(name = "idx_applications_job_id", columnList = "job_id"),
                @Index(name = "idx_applications_candidate_id", columnList = "candidate_id"),
                @Index(name = "idx_applications_status", columnList = "status")
        }
)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "job_id", nullable = false, columnDefinition = "uuid")
    private UUID jobId;

    @Column(name = "department_id", nullable = false, columnDefinition = "uuid")
    private UUID departmentId;

    @Column(name = "transferred_from")
    private Long transferredFrom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pipeline_stage_id", nullable = false)
    private PipelineStage pipelineStage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ApplicationStatus status;

    @Column(name = "cv_file_url", nullable = false, length = 1000)
    private String cvFileUrl;

    @Column(name = "applied_at", nullable = false)
    private OffsetDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = OffsetDateTime.now();
    }

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StageTransition> stageTransitions = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationNote> evaluationNotes = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActionableEmailToken> actionableEmailTokens = new ArrayList<>();

    public Application() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cvId) {
        this.cvId = cvId;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public Long getTransferredFrom() {
        return transferredFrom;
    }

    public void setTransferredFrom(Long transferredFrom) {
        this.transferredFrom = transferredFrom;
    }

    public PipelineStage getPipelineStage() {
        return pipelineStage;
    }

    public void setPipelineStage(PipelineStage pipelineStage) {
        this.pipelineStage = pipelineStage;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public OffsetDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(OffsetDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
