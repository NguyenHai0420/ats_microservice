package ats.fu.client.job;

import ats.fu.dto.JobView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "job-service",
        url = "${job-service.url:http://localhost:8081}",
        configuration = JobFeignConfig.class,
        fallback = JobClientFallback.class
)
public interface JobClient {

    @GetMapping("/api/v1/jobs/{id}")
    JobView findById(@PathVariable("id") UUID id);
}
