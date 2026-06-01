package com.mungdori.localpath.adapter.passes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PurchasePassRequest(
        @NotBlank String passId,
        @Min(1) @Max(20) int quantity
) {
}
