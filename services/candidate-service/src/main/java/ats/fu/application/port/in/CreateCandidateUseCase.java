package ats.fu.application.port.in;

import ats.fu.api.dto.CandidateResponse;
import ats.fu.application.command.CandidateCommand;

public interface CreateCandidateUseCase {
    CandidateResponse execute(CandidateCommand command);
}
