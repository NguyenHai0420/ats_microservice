package ats.fu.mapper;

import ats.fu.dto.JobRequest;
import ats.fu.dto.JobResponse;
import ats.fu.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobMapper {
    Job toEntity(JobRequest jobRequest);

    @Mapping(source = "department.id", target = "departmentId")
    JobResponse fromEntity(Job job);
}
