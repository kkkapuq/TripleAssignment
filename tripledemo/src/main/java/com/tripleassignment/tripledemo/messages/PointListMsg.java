package com.tripleassignment.tripledemo.messages;

import com.tripleassignment.tripledemo.domain.UserVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PointListMsg {

    private String code;
    private String message;
    private String userId;
    private int userPoint;
}
