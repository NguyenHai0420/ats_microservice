package ats.fu.application.port.in;

import ats.fu.application.command.CandidateCommand;
import ats.fu.domain.aggregate.CandidateAggregate;

public interface CreateCandidatePort {
    CandidateAggregate execute(CandidateCommand command);
}
