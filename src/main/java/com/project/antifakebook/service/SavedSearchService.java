package com.project.antifakebook.service;

import com.project.antifakebook.dto.ResponseCase;
import com.project.antifakebook.dto.ServerResponseDto;
import com.project.antifakebook.dto.saved_search.DelSavedSearchRequestDto;
import com.project.antifakebook.dto.saved_search.GetSavedSearchRequestDto;
import com.project.antifakebook.dto.saved_search.GetSavedSearchResponseDto;
import com.project.antifakebook.entity.SavedSearchEntity;
import com.project.antifakebook.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedSearchService {
    private final SavedSearchRepository savedSearchRepository;
    public SavedSearchService(SavedSearchRepository savedSearchRepository) {
        this.savedSearchRepository = savedSearchRepository;
    }
    public ServerResponseDto getSavedSearch(GetSavedSearchRequestDto requestDto, Long userId) {
        List<SavedSearchEntity> searchEntities = savedSearchRepository
                .findByUserId(userId,requestDto.getIndex(),requestDto.getCount());
        List<GetSavedSearchResponseDto> responseDtos = new ArrayList<>();
        for(SavedSearchEntity entity : searchEntities) {
            GetSavedSearchResponseDto responseDto = new GetSavedSearchResponseDto(entity);
            responseDtos.add(responseDto);
        }
        return new ServerResponseDto(ResponseCase.OK,responseDtos);
    }
    public ServerResponseDto delSavedSearch(DelSavedSearchRequestDto requestDto) {
        ServerResponseDto serverResponseDto;
        Optional<SavedSearchEntity> savedSearchEntity = savedSearchRepository.findById(requestDto.getSearchId());
        if(savedSearchEntity.isPresent()) {
            if(requestDto.getAll()) {
                savedSearchRepository.deleteAll();
            } else {
                savedSearchRepository.deleteById(requestDto.getSearchId());
            }
        serverResponseDto = new ServerResponseDto(ResponseCase.OK);
        } else {
            serverResponseDto = new ServerResponseDto(ResponseCase.INVALID_PARAMETER);
        }
        return serverResponseDto;
    }
}
