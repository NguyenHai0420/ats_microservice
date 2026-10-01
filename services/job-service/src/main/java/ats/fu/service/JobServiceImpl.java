package ats.fu.service;

import ats.fu.dto.JobRequest;
import ats.fu.dto.JobResponse;
import ats.fu.entity.Department;
import ats.fu.entity.Job;
import ats.fu.entity.JobStatus;
import ats.fu.exception.BusinessException;
import ats.fu.mapper.JobMapper;
import ats.fu.repository.DepartmentRepository;
import ats.fu.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final DepartmentRepository departmentRepository;
    private final JobMapper jobMapper;

    @Override
    public JobResponse save(JobRequest jobRequest) {
        Job job = jobMapper.toEntity(jobRequest);

        // Validate
        Department department = departmentRepository.findById(jobRequest.getDepartmentId()).orElse(null);
        if (department != null) {
            job.setDepartment(department);
        }

        if (job.getSalaryMax().compareTo(job.getSalaryMin()) < 0) {
            throw new BusinessException(1, "Salary max must be greater than or equal to salary min");
        }
        if (jobRepository.existsByTitle(job.getTitle())) {
            throw new BusinessException(2, "Title already exists");
        }

        job.setStatus(JobStatus.DRAFT);

        return jobMapper.fromEntity(jobRepository.save(job));
    }

    @Override
    public JobResponse findById(UUID uuid) {
        Job job = jobRepository.findById(uuid).orElseThrow(() -> new BusinessException(3, "Job not found"));
        return jobMapper.fromEntity(job);
    }
}
