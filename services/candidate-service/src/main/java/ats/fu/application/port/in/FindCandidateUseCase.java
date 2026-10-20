package ats.fu.application.port.in;

import ats.fu.api.dto.CandidateResponse;
import ats.fu.application.query.CandidateQuery;

public interface FindCandidateUseCase {
    CandidateResponse execute(CandidateQuery query);
}
