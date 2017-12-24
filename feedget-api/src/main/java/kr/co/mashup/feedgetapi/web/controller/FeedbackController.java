package kr.co.mashup.feedgetapi.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.mashup.feedgetapi.web.dto.DataListResponse;
import kr.co.mashup.feedgetapi.web.dto.FeedbackDto;
import kr.co.mashup.feedgetapi.web.dto.Response;
import kr.co.mashup.feedgetcommon.domain.Feedback;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 피드백 관련 request에 대한 처리
 * <p>
 * Created by ethan.kim on 2017. 12. 21..
 */
@RestController
@RequestMapping(value = "/creations/{creationId}/feedbacks")
@Api(description = "피드백", tags = {"feedbacks"})
public class FeedbackController {

//    @Autowired
//    private FeedbackService feedbackService;

    @ApiOperation(value = "창작물의 피드백 리스트 조회", notes = "창작물의 피드백 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponse<Feedback> getFeedbacks(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                                   @PathVariable(value = "creationId") long creationId) {

        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback());

        return new DataListResponse<>(feedbacks);

//        return new DataListResponse<>(feedbackService.readFeedbacks(creationId, page, size));
    }

    @ApiOperation(value = "창작물의 피드백 추가", notes = "창작물에 피드백을 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public Response createFeedback(@PathVariable(value = "creationId") long creationId,
                                   @RequestHeader long userId,  // Todo: 유저 ID 셋팅
                                   @Valid @RequestBody FeedbackDto feedbackDto) {
//        feedbackService.addFeedback(userId, creationId, feedbackDto);

        return Response.created();
    }

    @ApiOperation(value = "창작물의 피드백 삭제", notes = "창작물에 피드백을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "삭제 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/{feedbackId}")
    public void deleteFeedback(@PathVariable long feedbackId) {

    }


    // Todo: 피드백 채택하기

}