package com.mungdori.localpath.adapter.health;

import com.mungdori.localpath.common.constants.ApiPaths;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.API)
public class HealthApi {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
