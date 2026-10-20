package ats.fu.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ApplicationRequest(
        @JsonAlias("jobId")
        @NotNull UUID jobId,
        @JsonAlias("candidateId")
        @NotNull Long candidateId,
        @JsonAlias("cvFileUrl")
        @NotBlank String cvFileUrl) {
}
