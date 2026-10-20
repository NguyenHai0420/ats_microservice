package ats.fu.client.candidate;

import ats.fu.dto.CandidateView;
import ats.fu.exception.DownStreamUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CandidateClientFallback implements CandidateClient {

    @Override
    public CandidateView getCandidateById(Long id) {
        log.error("Candidate-service is unavailable. Fallback triggered for candidateId: {}", id);
        throw new DownStreamUnavailableException("candidate-service", "Candidate service is currently unavailable. Please try again later.");
    }
}
