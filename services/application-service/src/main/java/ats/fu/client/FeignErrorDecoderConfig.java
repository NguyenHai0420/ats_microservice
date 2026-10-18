package ats.fu.client;

import ats.fu.exception.BusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Centralized Feign error decoder.
 * Tự động convert HTTP error response từ các service khác thành BusinessException
 * để service layer không cần catch FeignException.
 */
@Configuration
@Slf4j
public class FeignErrorDecoderConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    private static class CustomErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            String body = extractBody(response);
            log.error("Feign error - method: {}, status: {}, body: {}", methodKey, response.status(), body);

            return switch (response.status()) {
                case 404 -> new BusinessException(1, 404, "Resource not found: " + body);
                case 400 -> new BusinessException(1, 400, "Bad request: " + body);
                case 503 -> new BusinessException(2, 503, "Service unavailable: " + body);
                default -> new BusinessException(2, response.status(), "Unexpected error from downstream service: " + body);
            };
        }

        private String extractBody(Response response) {
            try {
                if (response.body() != null) {
                    return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                log.warn("Failed to read response body", e);
            }
            return "No response body";
        }
    }
}
