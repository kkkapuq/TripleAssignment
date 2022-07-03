package com.tripleassignment.tripledemo.repository;

import com.tripleassignment.tripledemo.domain.ReviewVO;
import com.tripleassignment.tripledemo.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface MypageDAO {

    UserVO selectUserPoint(String userId);

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