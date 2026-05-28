package com.mungdori.localpath.application.passes;

import com.mungdori.localpath.application.passes.required.PassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PassDataSeeder implements ApplicationRunner {

    private final PassRepository passRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (passRepository.count() > 0) {
            return;
        }

        passRepository.saveAll(PassSeedData.passes());
    }
}

