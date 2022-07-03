package com.tripleassignment.tripledemo.service;

import com.tripleassignment.tripledemo.domain.ReviewVO;
import com.tripleassignment.tripledemo.domain.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface MypageService {
    public UserVO selectUserPoint(String userId);

    int insertReview(ReviewVO param);

    int selectUserReview(ReviewVO reviewVO);

    int insertReviewPhoto(ReviewVO reviewVO);

    int insertPoint(ReviewVO reviewVO);

    int selectFirstReview(ReviewVO reviewVO);

    int updateUserReview(ReviewVO reviewVO);

    int selectUserPhotoReview(ReviewVO reviewVO);

    int updateUserPhotoReview(ReviewVO reviewVO);

    int updateUserPhotoReviewNoUse(ReviewVO reviewVO);

    int deleteUserReview(ReviewVO reviewVO);

    int selectUserFirstReviewPoint(ReviewVO reviewVO);
}
