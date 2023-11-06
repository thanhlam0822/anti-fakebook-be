package com.project.antifakebook.dto.rate;

import java.util.Date;

public interface GetRateResponseDto {
    Long getId();
    String getMarkContent();
    String getTypeOfMark();
    Long getPostId();
    Long getUserId();
    Date getCreatedDate();
}
