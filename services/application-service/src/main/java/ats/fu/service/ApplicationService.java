package ats.fu.service;

import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;

import java.util.UUID;

public interface ApplicationService {
    ApplicationResponse create(ApplicationRequest request);

    ApplicationResponse findById(Long id);
}
