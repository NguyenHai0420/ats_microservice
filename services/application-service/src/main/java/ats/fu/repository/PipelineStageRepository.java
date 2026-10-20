package ats.fu.repository;

import ats.fu.entity.PipelineStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PipelineStageRepository extends JpaRepository<PipelineStage, Long> {

    Optional<PipelineStage> findFirstByDefaultStageTrueOrderByStageOrderAsc();

    PipelineStage findByDefaultStage(boolean defaultStage);
}