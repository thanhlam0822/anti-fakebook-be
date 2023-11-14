package com.project.antifakebook.rest;

import com.project.antifakebook.config.CustomUserDetails;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.saved_search.DelSavedSearchRequestDto;
import com.project.antifakebook.dto.saved_search.GetSavedSearchRequestDto;
import com.project.antifakebook.service.SavedSearchService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved-search")
public class SavedSearchController {
    private final SavedSearchService savedSearchService;
    public SavedSearchController(SavedSearchService savedSearchService) {
        this.savedSearchService = savedSearchService;
    }
    @PostMapping("get-saved-search")
    public ServerResponseDto getSavedSearch(@AuthenticationPrincipal CustomUserDetails currentUser,
                                            @RequestBody GetSavedSearchRequestDto requestDto) {
        return savedSearchService.getSavedSearch(requestDto, currentUser.getUserId());
    }
    @DeleteMapping("del-saved-search")
    public ServerResponseDto delSavedSearch(@RequestBody DelSavedSearchRequestDto requestDto) {
        return savedSearchService.delSavedSearch(requestDto);
    }
}
