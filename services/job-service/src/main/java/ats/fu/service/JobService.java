package ats.fu.service;

import ats.fu.dto.JobRequest;
import ats.fu.dto.JobResponse;

import java.util.UUID;

public interface JobService {
    JobResponse save(JobRequest jobRequest);

    JobResponse findById(UUID uuid);
}
