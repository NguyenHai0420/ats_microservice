package ats.fu.application.mapper;

import ats.fu.api.dto.CandidateResponse;
import ats.fu.api.dto.ParsedCvDataResponse;
import ats.fu.domain.aggregate.CandidateAggregate;
import ats.fu.infrastructure.persistence.entity.ParsedCvData;

import java.util.List;

public class CandidateResponseMapper {

    public static CandidateResponse toResponse(CandidateAggregate aggregate) {
        if (aggregate == null) {
            return null;
        }

        CandidateResponse response = new CandidateResponse();
        response.setId(aggregate.getId());
        response.setFullName(aggregate.getFullName());
        response.setStatus(aggregate.getStatus() != null ? aggregate.getStatus().toString() : null);
        response.setSource(aggregate.getSource());
        response.setUtmCampaign(aggregate.getUtmCampaign());
        response.setUtmSource(aggregate.getUtmSource());
        response.setUtmMedium(aggregate.getUtmMedium());
        response.setDuplicate(aggregate.isDuplicate());
        response.setActiveCvId(aggregate.getActiveCvId());
        response.setParsedCvData(toParsedCvDataResponse(aggregate.getParsedCvData()));

        return response;
    }

    public static ParsedCvDataResponse toParsedCvDataResponse(ParsedCvData data) {
        if (data == null) {
            return null;
        }

        List<ParsedCvDataResponse.EducationResponse> educationResponses = null;
        if (data.getEducation() != null) {
            educationResponses = data.getEducation().stream()
                    .map(edu -> ParsedCvDataResponse.EducationResponse.builder()
                            .school(edu.getSchool())
                            .degree(edu.getDegree())
                            .fieldOfStudy(edu.getFieldOfStudy())
                            .startDate(edu.getStartDate())
                            .endDate(edu.getEndDate())
                            .build())
                    .toList();
        }

        List<ParsedCvDataResponse.ExperienceResponse> experienceResponses = null;
        if (data.getExperience() != null) {
            experienceResponses = data.getExperience().stream()
                    .map(exp -> ParsedCvDataResponse.ExperienceResponse.builder()
                            .company(exp.getCompany())
                            .title(exp.getTitle())
                            .startDate(exp.getStartDate())
                            .endDate(exp.getEndDate())
                            .description(exp.getDescription())
                            .build())
                    .toList();
        }

        return ParsedCvDataResponse.builder()
                .fullName(data.getFullName())
                .email(data.getEmail())
                .phone(data.getPhone())
                .summary(data.getSummary())
                .skills(data.getSkills())
                .education(educationResponses)
                .experience(experienceResponses)
                .build();
    }
}
