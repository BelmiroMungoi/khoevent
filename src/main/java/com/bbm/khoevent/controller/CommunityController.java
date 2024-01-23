package com.bbm.khoevent.controller;

import com.bbm.khoevent.dto.request.CommunityRequest;
import com.bbm.khoevent.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody CommunityRequest request) {
        return  ResponseEntity.ok(communityService.createCommunity(request));

    }
}
