package com.tripleassignment.tripledemo.controller;

import com.tripleassignment.tripledemo.domain.ReviewInfoVO;
import com.tripleassignment.tripledemo.domain.ReviewVO;
import com.tripleassignment.tripledemo.domain.UserVO;
import com.tripleassignment.tripledemo.messages.EventsMsg;
import com.tripleassignment.tripledemo.messages.PointListMsg;
import com.tripleassignment.tripledemo.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
@Slf4j

public class MypageController {

    @Autowired
    MessageSource messageSource;
    private final MypageService mypageService;

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> pointUpdate(@RequestBody ReviewInfoVO param) throws Exception{
        EventsMsg resMsg = new EventsMsg();
        try{

            List<String> photoIds = param.getAttachedPhotoIds();
            String action = param.getAction();
            String type = param.getType();

            ReviewVO reviewVO = new ReviewVO();

            reviewVO.setUserId(param.getUserId());
            reviewVO.setPlaceId(param.getPlaceId());
            reviewVO.setContent(param.getContent());
            reviewVO.setAttachedPhotoIds(photoIds);
            reviewVO.setReviewId(param.getReviewId());
            reviewVO.setReviewType("");

            if(type.equals("REVIEW")){
                if(!(action.equals("ADD") || action.equals("MOD") || action.equals("DELETE"))){
                    resMsg.setCode("401");
                    resMsg.setMessage("매개변수 입력이 잘못되었습니다.");
                    return new ResponseEntity<EventsMsg>(resMsg, HttpStatus.OK);
                }

                if(action.equals("ADD")){
                    int excutedRtn = 0;
                    
                    // 리뷰 존재 여부 확인
                    excutedRtn = mypageService.selectUserReview(reviewVO);
                    if(excutedRtn > 0){
                        resMsg.setCode("402");
                        resMsg.setMessage("해당 장소에 이미 작성한 리뷰가 있습니다.");
                        return new ResponseEntity<EventsMsg>(resMsg, HttpStatus.OK);
                    }
                    // 장소 첫 리뷰 작성 시 포인트 지급
                    excutedRtn = mypageService.selectFirstReview(reviewVO);
                    if(excutedRtn < 1){
                        reviewVO.setReviewType("30");
                        excutedRtn = mypageService.insertPoint(reviewVO);
                    }

                    // 글 리뷰 작성 및 글 리뷰 작성 포인트 지급
                    excutedRtn = mypageService.insertReview(reviewVO);
                    reviewVO.setReviewType("10");
                    excutedRtn = mypageService.insertPoint(reviewVO);

                    // 사진 리뷰 작성 시 사진 리뷰 작성 포인트 지급
                    if(photoIds.size() > 0){
                        for(String photoId : photoIds){
                            reviewVO.setPhotoId(photoId);
                            excutedRtn = mypageService.insertReviewPhoto(reviewVO);
                        }
                        reviewVO.setReviewType("20");
                        excutedRtn = mypageService.insertPoint(reviewVO);
                    }
                } else if(action.equals("MOD")){
                    int excutedRtn = 0;
                    
                    // 리뷰 존재 여부 확인
                    excutedRtn = mypageService.selectUserReview(reviewVO);
                    if(excutedRtn < 1){
                        resMsg.setCode("403");
                        resMsg.setMessage("해당 장소의 리뷰가 존재하지 않습니다.");
                        return new ResponseEntity<EventsMsg>(resMsg, HttpStatus.OK);
                    }
                    
                    // 포토리뷰 존재 여부 확인
                    int userPhotoReview = mypageService.selectUserPhotoReview(reviewVO);

                    // 글만 있는 리뷰였을 때, 사진을 추가해준다면 1점 부여
                    if(photoIds.size() > 0 && userPhotoReview < 1){
                        for(String photoId : photoIds){
                            reviewVO.setPhotoId(photoId);
                            excutedRtn = mypageService.insertReviewPhoto(reviewVO);
                        }
                        reviewVO.setReviewType("10");
                        excutedRtn = mypageService.insertPoint(reviewVO);
                    }

                    // 사진을 삭제한다면 1점 부여 취소
                    if(photoIds.size() < 1 && userPhotoReview > 0){
                        reviewVO.setFlag(0);
                        excutedRtn = mypageService.updateUserPhotoReviewNoUse(reviewVO);

                        reviewVO.setReviewType("40");
                        excutedRtn = mypageService.insertPoint(reviewVO);
                    }

                    // 리뷰 및 사진 업데이트
                    excutedRtn = mypageService.updateUserReview(reviewVO);

                    // 특정 사진만 추가/삭제할 경우 해당 작업 처리
                    if(photoIds.size() > 0){
                        for(String photoId : photoIds){
                            reviewVO.setPhotoId(photoId);
                            excutedRtn = mypageService.updateUserPhotoReview(reviewVO);
                        }
                        reviewVO.setFlag(1);
                        excutedRtn = mypageService.updateUserPhotoReviewNoUse(reviewVO);
                    }

                } else if(action.equals("DELETE")){
                    int excutedRtn = 0;

                    // 리뷰 존재 여부 확인
                    excutedRtn = mypageService.selectUserReview(reviewVO);
                    if(excutedRtn < 1){
                        resMsg.setCode("403");
                        resMsg.setMessage("해당 장소의 리뷰가 존재하지 않습니다.");
                        return new ResponseEntity<EventsMsg>(resMsg, HttpStatus.OK);
                    }

                    // 리뷰 삭제 및 포인트 회수
                    excutedRtn = mypageService.deleteUserReview(reviewVO);
                    reviewVO.setReviewType("40");
                    excutedRtn = mypageService.insertPoint(reviewVO);

                    // 포토리뷰 존재 여부 확인
                    int userPhotoReview = mypageService.selectUserPhotoReview(reviewVO);

                    // 포토리뷰 삭제
                    if(userPhotoReview > 0){
                        reviewVO.setFlag(0);
                        excutedRtn = mypageService.updateUserPhotoReviewNoUse(reviewVO);

                        // 포토리뷰 포인트 회수
                        reviewVO.setReviewType("40");
                        excutedRtn = mypageService.insertPoint(reviewVO);
                    }

                    // 장소 첫 리뷰 작성 포인트 존재 시 회수
                    int userFirstReviewPoint = mypageService.selectUserFirstReviewPoint(reviewVO);
                    if(userFirstReviewPoint > 0){
                        // 장소 첫 리뷰 작성 포인트 회수
                        reviewVO.setReviewType("40");
                        excutedRtn = mypageService.insertPoint(reviewVO);
                    }

                }
            } else {
                resMsg.setCode("401");
                resMsg.setMessage("매개변수 입력이 잘못되었습니다.");
            }

        } catch (Exception e){
            resMsg.setCode("500");
            resMsg.setMessage(messageSource.getMessage("errors.api.system", null, null));
            log.error("error : ", e);
            return new ResponseEntity<EventsMsg>(resMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resMsg.setCode("200");
        resMsg.setMessage("OK");

        return new ResponseEntity<EventsMsg>(resMsg, HttpStatus.OK);
    }

    @RequestMapping(value = "/point-list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> pointList(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "userId", required = true) String userId
    ) throws Exception {
        PointListMsg resMsg = new PointListMsg();
        try{
            UserVO userVO = mypageService.selectUserPoint(userId);

            if(userVO == null){
                resMsg.setCode("401");
                resMsg.setMessage("고객이 존재하지 않습니다.");
                return new ResponseEntity<PointListMsg>(resMsg, HttpStatus.OK);
            } else {
                resMsg.setCode("200");
                resMsg.setMessage("OK");
                resMsg.setUserId(userVO.getUserId());
                resMsg.setUserPoint(userVO.getPoint());
            }
        } catch (Exception e){
            resMsg.setCode("500");
            resMsg.setMessage("시스템 오류가 발생했습니다.");
        }
        return new ResponseEntity<PointListMsg>(resMsg, HttpStatus.OK);
    }
}
