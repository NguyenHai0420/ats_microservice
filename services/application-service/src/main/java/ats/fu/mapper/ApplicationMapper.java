package ats.fu.mapper;

import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;
import ats.fu.entity.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    Application toEntity(ApplicationRequest request);
    ApplicationResponse fromEntity(Application entity);
}
