package ats.fu.mapper;

import ats.fu.dto.ApplicationRequest;
import ats.fu.dto.ApplicationResponse;
import ats.fu.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    Application toEntity(ApplicationRequest request);

    @Mapping(source = "cvFileUrl", target = "cvUrl")
    ApplicationResponse fromEntity(Application entity);
}
