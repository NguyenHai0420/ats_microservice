package ats.fu.client.job;

import ats.fu.dto.JobView;
import ats.fu.exception.DownStreamUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class JobClientFallback implements JobClient {

    @Override
    public JobView findById(UUID id) {
        log.error("Job-service is unavailable. Fallback triggered for jobId: {}", id);
        throw new DownStreamUnavailableException("job-service", "Job service is currently unavailable. Please try again later.");
    }
}
