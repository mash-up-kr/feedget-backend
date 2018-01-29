package kr.co.mashup.feedgetcommon.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 유저
 * <p>
 * Created by ethan.kim on 2017. 11. 4..
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"creations", "feedbacks"})
@EqualsAndHashCode(callSuper = false, of = "userId")
public class User extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    // 실명
    @Column(name = "real_name", length = 20, nullable = false)
    private String realName;

    // 닉네임
    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

    // 이메일
    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    // uuid(universally unique identifier) - 외부로 노출되는 ID
    // Todo: 유저 생성시 중복값 체크 로직 추가
    @Column(name = "uuid", length = 32, nullable = false, unique = true)
    private String uuid;

    // FCM 푸시 메시지 전송을 위한 ID
    @Column(name = "cloud_msg_reg_id", length = 256)
    private String cloudMsgRegId;

    // 등급
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_grade_cd", length = 20, nullable = false, columnDefinition = "varchar(20) default 'BRONZE'")
    private UserGrade userGrade;

    // 해당 사용자가 사용하고 있는 앱의 버전코드
    @Column(name = "use_version_code", columnDefinition = "int(11) default '0'")
    private int useVersionCode;

    // 획득한 총 point
    @Column(name = "total_point", nullable = false, columnDefinition = "decimal(12,2) default '0.00'")
    private Double totalPoint;

    // 보유 포인트
    @Column(name = "current_point", nullable = false, columnDefinition = "decimal(12,2) default '0.00'")
    private Double currentPoint;

    // 일정 기간동안 획득 포인트  Todo: 필요 없을시 제거
    @Column(name = "period_point", nullable = false, columnDefinition = "decimal(12,2) default '0.00'")
    private Double periodPoint;

    // 피드백 작성 수
    @Column(name = "feedback_writing_count", nullable = false, columnDefinition = "int(11) default '0'")
    private int feedbackWritingCount;

    // 피드백 채택 수
    @Column(name = "feedback_reward_count", nullable = false, columnDefinition = "int(11) default '0'")
    private int feedbackRewardCount;

    // Todo: 아래 필드 어떻게 할지...? 매번 계산할지, 결과 저장할지
    // 답변 채택률
    // 질문 마감률

    // Todo: 다른 social login 지원시 entity 분리 고려
    // Todo: 필요없을 시 제거
    // OAuth Access Token(카톡, FB)
    @Column(name = "oauth_token", length = 256)
    private String oAuthToken;

    // 가입한 OAuth Type
    @Enumerated(EnumType.STRING)  // enum 이름을 DB에 저장
    @Column(name = "oauth_type", length = 20)
    private OAuthType oAuthType;

    // 유저가 작성한 피드백
    @OneToMany(mappedBy = "writer")
    private List<Feedback> feedbacks;

    // 유저가 작성한 창작물
    @OneToMany(mappedBy = "writer")
    private List<Creation> creations;

    /**
     * oauth type 관리
     */
    public enum OAuthType {
        KAKAO,
        FB;
    }

    /**
     * 유저 등급
     * Todo: 임시 naming이므로 수정 필요
     */
    public enum UserGrade {
        GOLD,
        SILVER,
        BRONZE;
    }

    public boolean isSameUser(User user) {
        if (user == null) {
            return false;
        }
        return this.userId.equals(user.userId);
    }

    public boolean changePoint(double point) {
        this.currentPoint += point;
        this.periodPoint += point;

        if (currentPoint < 0) {
            return false;
        }
        return true;
    }

    public User(String realName, String nickname, String email, String uuid, String oAuthToken, OAuthType oAuthType) {
        this.realName = realName;
        this.nickname = nickname;
        this.email = email;
        this.uuid = uuid;
        this.userGrade = UserGrade.BRONZE;
        this.useVersionCode = 10000;
        this.totalPoint = 0d;
        this.currentPoint = 0d;
        this.periodPoint = 0d;
        this.feedbackWritingCount = 0;
        this.feedbackRewardCount = 0;
        this.oAuthToken = oAuthToken;
        this.oAuthType = oAuthType;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
