package com.mungdori.localpath.application.passes.provided;

import com.mungdori.localpath.domain.passes.PassEntity;

import java.util.List;
import java.util.Optional;

public interface PassService {
    List<PassEntity> getAllPasses();

    Optional<PassEntity> getPassById(String passId);
}

