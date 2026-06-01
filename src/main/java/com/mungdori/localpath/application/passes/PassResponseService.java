package com.mungdori.localpath.application.passes;

import com.mungdori.localpath.adapter.passes.PassMapper;
import com.mungdori.localpath.adapter.passes.dto.PassResponse;
import com.mungdori.localpath.adapter.passes.dto.SpotCurationResponse;
import com.mungdori.localpath.application.passes.provided.PassService;
import com.mungdori.localpath.application.passes.required.SpotCurationRepository;
import com.mungdori.localpath.domain.passes.Pass;
import com.mungdori.localpath.domain.passes.SpotCuration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassResponseService {

    private final PassService passService;
    private final SpotCurationRepository spotCurationRepository;

    public List<PassResponse> getAllPasses() {
        Map<String, SpotCurationResponse> curationBySpotName = loadCurationMap();
        return passService.getAllPasses().stream()
                .map(pass -> PassMapper.toResponse(pass, curationBySpotName))
                .toList();
    }

    public PassResponse getPassById(String passId) {
        Pass pass = passService.getPassById(passId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return PassMapper.toResponse(pass, loadCurationMap());
    }

    private Map<String, SpotCurationResponse> loadCurationMap() {
        return spotCurationRepository.findAllByOrderBySpotNameAsc().stream()
                .collect(Collectors.toMap(
                        SpotCuration::getSpotName,
                        this::toCurationResponse,
                        (a, b) -> a
                ));
    }

    private SpotCurationResponse toCurationResponse(SpotCuration curation) {
        return new SpotCurationResponse(
                curation.getOwnerTitle(),
                curation.getSignatureMenu(),
                curation.getRecommendedVisitTime(),
                curation.getHiddenTip()
        );
    }
}
