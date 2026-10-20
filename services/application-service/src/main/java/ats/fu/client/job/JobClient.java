package ats.fu.client;

import ats.fu.dto.ResponseApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "job-service",
        fallback = JobClientFallback.class
)
public interface JobClient {

    @GetMapping("/api/v1/jobs/{id}")
    ResponseApi findById(@PathVariable("id") UUID id);
}
