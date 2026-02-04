package com.gym.membership.controller;

import com.gym.common.response.ApiResponse;
import com.gym.membership.dto.MembershipAssignRequest;
import com.gym.membership.dto.MembershipRenewRequest;
import com.gym.membership.dto.MembershipResponse;
import com.gym.membership.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<MembershipResponse>> assign(@Valid @RequestBody MembershipAssignRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Membership assigned", membershipService.assign(request)));
    }

    @PutMapping("/{id}/renew")
    public ResponseEntity<ApiResponse<MembershipResponse>> renew(@PathVariable Long id,
                                                                 @Valid @RequestBody MembershipRenewRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Membership renewed", membershipService.renew(id, request)));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<List<MembershipResponse>>> history(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.success("Membership history", membershipService.historyByMember(memberId)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<MembershipResponse>>> active() {
        return ResponseEntity.ok(ApiResponse.success("Active memberships", membershipService.listActive()));
    }

    @GetMapping("/expired")
    public ResponseEntity<ApiResponse<List<MembershipResponse>>> expired() {
        return ResponseEntity.ok(ApiResponse.success("Expired memberships", membershipService.listExpired()));
    }
}
