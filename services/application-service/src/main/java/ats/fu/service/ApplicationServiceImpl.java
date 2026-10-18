package ats.fu.service;

import ats.fu.client.JobClient;
import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;
import ats.fu.entity.Application;
import ats.fu.mapper.ApplicationMapper;
import ats.fu.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final JobClient jobClient;

    @Override
    public ApplicationResponse save(ApplicationRequest request) {
        // Validate job exists by calling job-service
        // If job not found → FeignErrorDecoder throws BusinessException automatically
        // If job-service is down → JobClientFallback throws BusinessException
        jobClient.findById(request.getJobId());

        Application application = applicationMapper.toEntity(request);
        return applicationMapper.fromEntity(applicationRepository.save(application));
    }
}
