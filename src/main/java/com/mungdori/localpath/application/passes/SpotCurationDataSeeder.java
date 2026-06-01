package com.mungdori.localpath.application.passes;

import com.mungdori.localpath.application.passes.required.SpotCurationRepository;
import com.mungdori.localpath.domain.passes.SpotCuration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(3)
@RequiredArgsConstructor
public class SpotCurationDataSeeder implements ApplicationRunner {

    private final SpotCurationRepository spotCurationRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (SpotCuration seed : SpotCurationSeedData.curations()) {
            if (spotCurationRepository.findBySpotName(seed.getSpotName()).isPresent()) {
                continue;
            }
            spotCurationRepository.save(seed);
        }
    }
}
