package com.tripleassignment.tripledemo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserVO {
    private String userId;
    private int Point;
    private int maxSeq;
}
