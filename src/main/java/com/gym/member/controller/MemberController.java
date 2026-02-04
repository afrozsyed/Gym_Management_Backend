package com.gym.member.controller;

import com.gym.common.model.MemberStatus;
import com.gym.common.response.ApiResponse;
import com.gym.member.dto.MemberProfileResponse;
import com.gym.member.dto.MemberRequest;
import com.gym.member.dto.MemberResponse;
import com.gym.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> create(@Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Member created successfully", memberService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MemberResponse>>> list(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(required = false) String search,
                                                                  @RequestParam(required = false) MemberStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("joinedDate").descending());
        return ResponseEntity.ok(ApiResponse.success("Members fetched", memberService.list(search, status, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberProfileResponse>> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Member profile fetched", memberService.getProfile(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponse>> update(@PathVariable Long id,
                                                              @Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Member updated", memberService.update(id, request)));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable Long id) {
        memberService.deactivate(id);
        return ResponseEntity.ok(ApiResponse.success("Member deactivated", null));
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activate(@PathVariable Long id) {
        memberService.activate(id);
        return ResponseEntity.ok(ApiResponse.success("Member activated", null));
    }
}
