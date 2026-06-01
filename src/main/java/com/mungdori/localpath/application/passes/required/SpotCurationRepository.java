package com.mungdori.localpath.application.passes.required;

import com.mungdori.localpath.domain.passes.SpotCuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpotCurationRepository extends JpaRepository<SpotCuration, Long> {

    Optional<SpotCuration> findBySpotName(String spotName);

    List<SpotCuration> findAllByOrderBySpotNameAsc();
}
