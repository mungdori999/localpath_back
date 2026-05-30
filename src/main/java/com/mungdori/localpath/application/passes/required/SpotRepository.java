package com.mungdori.localpath.application.passes.required;

import com.mungdori.localpath.domain.passes.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> findFirstByName(String name);

    List<Spot> findByName(String name);
}
