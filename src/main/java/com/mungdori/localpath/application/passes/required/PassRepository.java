package com.mungdori.localpath.application.passes.required;

import com.mungdori.localpath.domain.passes.PassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassRepository extends JpaRepository<PassEntity, String> {}

