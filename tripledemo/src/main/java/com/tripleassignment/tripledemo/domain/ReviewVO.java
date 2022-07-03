package com.tripleassignment.tripledemo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReviewVO {

    private String userId;
    private String reviewId;
    private String placeId;
    private String content;
    private List<String> attachedPhotoIds;
    private String photoId;
    private int flag;
    private String reviewType;
    private String displayYn;
    private String insertId;
    private Timestamp insertDate;
    private String modifyId;
    private Timestamp modifyDate;

}
