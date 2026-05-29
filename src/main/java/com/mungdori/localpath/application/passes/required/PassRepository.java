package com.mungdori.localpath.application.passes.required;

import com.mungdori.localpath.domain.passes.Pass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassRepository extends JpaRepository<Pass, String> {}

