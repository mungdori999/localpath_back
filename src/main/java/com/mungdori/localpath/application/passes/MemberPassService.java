package com.mungdori.localpath.application.passes;

import com.mungdori.localpath.adapter.passes.dto.MemberPassTicketResponse;
import com.mungdori.localpath.adapter.passes.dto.PurchasePassResponse;
import com.mungdori.localpath.application.member.required.MemberRepository;
import com.mungdori.localpath.application.passes.required.MemberPassTicketRepository;
import com.mungdori.localpath.application.passes.required.PassRepository;
import com.mungdori.localpath.common.constants.Messages;
import com.mungdori.localpath.common.time.KoreaTime;
import com.mungdori.localpath.domain.member.Member;
import com.mungdori.localpath.domain.passes.MemberPassTicket;
import com.mungdori.localpath.domain.passes.Pass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPassService {

    private final MemberRepository memberRepository;
    private final PassRepository passRepository;
    private final MemberPassTicketRepository memberPassTicketRepository;

    @Transactional(readOnly = true)
    public List<MemberPassTicketResponse> getMyPasses(String memberEmail) {
        Member member = findMember(memberEmail);
        return memberPassTicketRepository.findByMemberWithPass(member).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MemberPassTicketResponse getMyPassTicket(String memberEmail, String ticketId) {
        Member member = findMember(memberEmail);
        MemberPassTicket ticket = memberPassTicketRepository.findByTicketIdAndMember(ticketId, member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.TICKET_NOT_FOUND));
        return toResponse(ticket);
    }

    @Transactional
    public PurchasePassResponse purchase(String memberEmail, String passId, int quantity) {
        Member member = findMember(memberEmail);
        Pass pass = passRepository.findById(passId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.PASS_NOT_FOUND));

        var purchasedAt = KoreaTime.nowLocal();
        List<MemberPassTicket> tickets = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            tickets.add(MemberPassTicket.purchase(member, pass, purchasedAt));
        }
        memberPassTicketRepository.saveAll(tickets);

        List<MemberPassTicketResponse> responses = tickets.stream()
                .map(this::toResponse)
                .toList();
        return new PurchasePassResponse(responses);
    }

    private MemberPassTicketResponse toResponse(MemberPassTicket ticket) {
        Pass pass = ticket.getPass();
        return new MemberPassTicketResponse(
                ticket.getTicketId(),
                pass.getId(),
                pass.getName(),
                pass.getImage(),
                ticket.getUnitPrice(),
                KoreaTime.toOffset(ticket.getPurchasedAt()),
                KoreaTime.toOffset(ticket.getExpiresAt()),
                ticket.isValid()
        );
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MEMBER_NOT_FOUND));
    }
}
