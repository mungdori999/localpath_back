package com.mungdori.localpath.application.auth.required;

import com.mungdori.localpath.domain.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

}
