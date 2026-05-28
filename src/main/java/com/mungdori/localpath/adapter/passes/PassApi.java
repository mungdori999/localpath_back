package com.mungdori.localpath.adapter.passes;

import com.mungdori.localpath.adapter.passes.dto.PassResponse;
import com.mungdori.localpath.application.passes.provided.PassService;
import com.mungdori.localpath.common.constants.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.API)
public class PassApi {

    private final PassService passService;

    @GetMapping("/passes")
    @Transactional(readOnly = true)
    public List<PassResponse> getPasses() {
        return passService.getAllPasses().stream()
                .map(PassMapper::toResponse)
                .toList();
    }

    @GetMapping("/passes/{passId}")
    @Transactional(readOnly = true)
    public ResponseEntity<PassResponse> getPass(@PathVariable String passId) {
        return passService
                .getPassById(passId)
                .map(PassMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
