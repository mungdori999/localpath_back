package com.mungdori.localpath.application.passes.provided;

import com.mungdori.localpath.domain.passes.Pass;

import java.util.List;
import java.util.Optional;

public interface PassService {
    List<Pass> getAllPasses();

    Optional<Pass> getPassById(String passId);
}

