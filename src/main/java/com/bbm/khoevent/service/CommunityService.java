package com.bbm.khoevent.service;

import com.bbm.khoevent.dto.request.CommunityRequest;
import com.bbm.khoevent.dto.response.CommunityResponse;
import com.bbm.khoevent.model.Community;

import java.util.List;

public interface CommunityService {

    String createCommunity(CommunityRequest request);
    List<CommunityResponse> findAllCommunities();
    CommunityResponse findById(Long id);
    Community getCommunityById(Long id);
    String update(CommunityRequest communityRequest, Long id);
}
