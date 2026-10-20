package ats.fu.client.job;

import ats.fu.exception.BusinessException;
import ats.fu.exception.DownStreamUnavailableException;
import ats.fu.exception.JobNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class JobErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = "";
        if (response.body() != null) {
            try {
                body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException ignored) {
            }
        }

        return switch (response.status()) {
            case 404 -> new JobNotFoundException(
                    "JOB_NOT_FOUND",
                    !body.isBlank() ? body : "Job not found",
                    response.reason(),
                    Instant.now()
            );
            case 400 -> new BusinessException(
                    1,
                    "Bad request to job-service: " + (!body.isBlank() ? body : response.reason())
            );
            case 401, 403 -> new BusinessException(
                    1,
                    "Job service unauthorized or forbidden: " + response.reason()
            );
            case 409 -> new BusinessException(
                    1,
                    "Job service conflict: " + response.reason()
            );
            case 500, 502, 503, 504 -> new DownStreamUnavailableException(
                    "job-service",
                    response.reason() != null ? response.reason() : "Service error"
            );
            default -> new BusinessException(
                    1,
                    "Job service returned unexpected status " + response.status() + ": " + response.reason()
            );
        };
    }
}
