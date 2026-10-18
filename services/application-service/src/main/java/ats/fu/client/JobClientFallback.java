package ats.fu.client;

import ats.fu.dto.ResponseApi;
import ats.fu.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Fallback cho JobClient khi job-service không khả dụng (connection refused, timeout, ...).
 * Chỉ được gọi khi Feign không thể kết nối tới job-service.
 */
@Component
@Slf4j
public class JobClientFallback implements JobClient {

    @Override
    public ResponseApi findById(UUID id) {
        log.error("Job-service is unavailable. Fallback triggered for jobId: {}", id);
        throw new BusinessException(0, 503, "Job service is unavailable. Please try again later.");
    }
}
