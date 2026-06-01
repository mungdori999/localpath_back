package com.mungdori.localpath.adapter.passes;

import com.mungdori.localpath.adapter.passes.dto.PassResponse;
import com.mungdori.localpath.application.passes.PassResponseService;
import com.mungdori.localpath.common.constants.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.API)
public class PassApi {

    private final PassResponseService passResponseService;

    @GetMapping("/passes")
    public List<PassResponse> getPasses() {
        return passResponseService.getAllPasses();
    }

    @GetMapping("/passes/{passId}")
    public ResponseEntity<PassResponse> getPass(@PathVariable String passId) {
        return ResponseEntity.ok(passResponseService.getPassById(passId));
    }
}
