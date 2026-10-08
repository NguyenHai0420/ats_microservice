package ats.fu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "interview_interviewers",
        uniqueConstraints = @UniqueConstraint(
                name = "ux_interview_interviewer_user",
                columnNames = {"interview_id", "user_id"}
        )
)
public class InterviewInterviewer extends BaseAuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    // Keycloak user identifier.
    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;
}
