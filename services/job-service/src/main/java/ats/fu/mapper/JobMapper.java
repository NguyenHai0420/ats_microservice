package ats.fu.mapper;

import ats.fu.dto.JobRequest;
import ats.fu.dto.JobResponse;
import ats.fu.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {
    Job toEntity(JobRequest jobRequest);

    JobResponse fromEntity(Job job);
}
