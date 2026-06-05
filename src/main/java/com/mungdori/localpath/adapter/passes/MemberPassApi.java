package com.mungdori.localpath.adapter.passes;

import com.mungdori.localpath.adapter.AuthorizationUtil;
import com.mungdori.localpath.adapter.passes.dto.MemberPassTicketResponse;
import com.mungdori.localpath.adapter.passes.dto.PurchasePassRequest;
import com.mungdori.localpath.adapter.passes.dto.PurchasePassResponse;
import com.mungdori.localpath.application.passes.MemberPassService;
import com.mungdori.localpath.common.constants.ApiPaths;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.MY_PASSES)
public class MemberPassApi {

    private final MemberPassService memberPassService;

    @GetMapping
    public List<MemberPassTicketResponse> getMyPasses() {
        return memberPassService.getMyPasses(AuthorizationUtil.requireEmail());
    }

    @GetMapping("/{ticketId}")
    public MemberPassTicketResponse getMyPassTicket(@PathVariable String ticketId) {
        return memberPassService.getMyPassTicket(AuthorizationUtil.requireEmail(), ticketId);
    }

    @PostMapping
    public PurchasePassResponse purchase(@Valid @RequestBody PurchasePassRequest request) {
        return memberPassService.purchase(
                AuthorizationUtil.requireEmail(),
                request.passId(),
                request.spendingFocus(),
                request.quantity()
        );
    }
}
