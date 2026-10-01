package ats.fu.service;

import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;
import ats.fu.entity.Application;
import ats.fu.mapper.ApplicationMapper;
import ats.fu.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    public ApplicationResponse save(ApplicationRequest request) {
        Application application = applicationMapper.toEntity(request);
        return applicationMapper.fromEntity(applicationRepository.save(application));
    }
}
