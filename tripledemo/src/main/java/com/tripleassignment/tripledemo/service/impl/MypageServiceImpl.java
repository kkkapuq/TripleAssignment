package com.tripleassignment.tripledemo.service.impl;

import com.tripleassignment.tripledemo.domain.ReviewInfoVO;
import com.tripleassignment.tripledemo.domain.ReviewVO;
import com.tripleassignment.tripledemo.domain.UserVO;
import com.tripleassignment.tripledemo.service.MypageService;
import com.tripleassignment.tripledemo.repository.MypageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageServiceImpl implements MypageService {
    private final MypageDAO mypageDAO;

    @Override
    public UserVO selectUserPoint(String userId) {
        return mypageDAO.selectUserPoint(userId);
    }

    @Override
    public int insertReview(ReviewVO param) {
        return mypageDAO.insertReview(param);
    }

    @Override
    public int selectUserReview(ReviewVO reviewVO) {
        return mypageDAO.selectUserReview(reviewVO);
    }

    @Override
    public int insertReviewPhoto(ReviewVO reviewVO) {
        return mypageDAO.insertReviewPhoto(reviewVO);
    }

    @Override
    public int insertPoint(ReviewVO reviewVO) {
        return mypageDAO.insertPoint(reviewVO);
    }

    @Override
    public int selectFirstReview(ReviewVO reviewVO) {
        return mypageDAO.selectFirstReview(reviewVO);
    }

    @Override
    public int updateUserReview(ReviewVO reviewVO) {
        return mypageDAO.updateUserReview(reviewVO);
    }

    @Override
    public int selectUserPhotoReview(ReviewVO reviewVO) {
        return mypageDAO.selectUserPhotoReview(reviewVO);
    }

    @Override
    public int updateUserPhotoReview(ReviewVO reviewVO) {
        return mypageDAO.updateUserPhotoReview(reviewVO);
    }

    @Override
    public int updateUserPhotoReviewNoUse(ReviewVO reviewVO) {
        return mypageDAO.updateUserPhotoReviewNoUse(reviewVO);
    }

    @Override
    public int deleteUserReview(ReviewVO reviewVO) {
        return mypageDAO.deleteUserReview(reviewVO);
    }

    @Override
    public int selectUserFirstReviewPoint(ReviewVO reviewVO) {
        return mypageDAO.selectUserFirstReviewPoint(reviewVO);
    }
}
