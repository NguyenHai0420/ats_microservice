package ats.fu.service;

import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;

public interface ApplicationService {
    ApplicationResponse save(ApplicationRequest request);
}
