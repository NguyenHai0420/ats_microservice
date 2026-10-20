package ats.fu.service;

import ats.fu.client.candidate.CandidateClient;
import ats.fu.client.job.JobClient;
import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;
import ats.fu.dto.CandidateView;
import ats.fu.dto.JobView;
import ats.fu.entity.Application;
import ats.fu.entity.ApplicationStatus;
import ats.fu.entity.PipelineStage;
import ats.fu.exception.BusinessException;
import ats.fu.mapper.ApplicationMapper;
import ats.fu.repository.ApplicationRepository;
import ats.fu.repository.PipelineStageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final JobClient jobClient;
    private final CandidateClient candidateClient;
    private final PipelineStageRepository pipelineStageRepository;

    @Transactional
    public ApplicationResponse create(ApplicationRequest request) {
        // Call job-service

        JobView jobView = jobClient.findById(request.jobId());

        log.info("Creating application with id {}", jobView);

        if (!"PUBLISHED".equals(jobView.getStatus()) || jobView.getDeadline().isBefore(OffsetDateTime.now())) {
            throw new BusinessException(1, "Job is not published or over due!");
        }

        // Call candidate

        CandidateView candidateView = candidateClient.getCandidateById(request.candidateId());

        if ("INACTIVE".equals(candidateView.getStatus())) {
            throw new BusinessException(2, "Candidate is not valid to apply for this job!");
        }

        if (applicationRepository.existsByJobIdAndCandidateIdAndStatusNot(
                request.jobId(), request.candidateId(), ApplicationStatus.WITHDRAWN)) {
            throw new IllegalStateException("Candidate already has an active application for this job");
        }

        Application application = new Application();
        application.setJobId(request.jobId());
        application.setDepartmentId(jobView.getDepartmentId());
        application.setCandidateId(request.candidateId());
        application.setCvId(candidateView.getActiveCvId());
        application.setCvFileUrl(request.cvFileUrl());
        application.setStatus(ApplicationStatus.SUBMITTED);
        application.setAppliedAt(OffsetDateTime.now());
        application.setPipelineStage(getDefaultStage());

        return ApplicationResponse.from(applicationRepository.save(application));
    }

    private PipelineStage getDefaultStage() {
        return pipelineStageRepository.findFirstByDefaultStageTrueOrderByStageOrderAsc()
                .orElseGet(() -> {
                    PipelineStage stage = new PipelineStage();
                    stage.setStageName("Applied");
                    stage.setStageOrder(1);
                    stage.setDefaultStage(true);
                    return pipelineStageRepository.save(stage);
                });
    }

    @Override
    public ApplicationResponse findById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(1, "Application not found with id: " + id));
        return applicationMapper.fromEntity(application);
    }
}
