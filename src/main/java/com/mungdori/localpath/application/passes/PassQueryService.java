package com.mungdori.localpath.application.passes;

import com.mungdori.localpath.application.passes.provided.PassService;
import com.mungdori.localpath.application.passes.required.PassRepository;
import com.mungdori.localpath.domain.passes.Pass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassQueryService implements PassService {

    private final PassRepository passRepository;

    @Override
    public List<Pass> getAllPasses() {
        return passRepository.findAll();
    }

    @Override
    public Optional<Pass> getPassById(String passId) {
        return passRepository.findById(passId);
    }
}

