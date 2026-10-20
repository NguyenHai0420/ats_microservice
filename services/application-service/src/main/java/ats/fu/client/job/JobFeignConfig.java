package ats.fu.client.job;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class JobFeignConfig {

    @Bean
    public ErrorDecoder defaultErrorDecoder() {
        return new JobErrorDecoder();
    }
}
