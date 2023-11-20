package com.project.antifakebook.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class    GetListPostsRequestDto {
    private Long userId;
    private Integer inCampaign;
    private Long campaignId;
    private Double latitude;
    private Double longitude;
    private Long lastId;
    private Integer index;
    private Integer count;
}
