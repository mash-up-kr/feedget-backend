package kr.co.mashup.feedgetcommon.domain;

import kr.co.mashup.feedgetcommon.domain.code.ContentType;
import lombok.*;

import javax.persistence.*;

/**
 * 피드백에 첨부된 컨텐츠
 * <p>
 * Created by ethan.kim on 2017. 12. 19..
 */
@Entity
@Table(name = "feedback_attached_content")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "feedbackAttachedContentId")
public class FeedbackAttachedContent extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_attached_content_id")
    private Long feedbackAttachedContentId;

    // 파일 이름(중복되지 않게 생성)
    // uuid - 36, timestamp - 10, 확장자(.jpeg) - 5 -> 51
    @Column(name = "file_name", length = 52, nullable = false, unique = true)
    private String fileName;

    // 파일 원본 이름(linux limit 255byte)
    @Column(name = "original_file_name", length = 255, nullable = false)
    private String originalFileName;

    // 파일 사이즈
    @Column(name = "size", columnDefinition = "INT(11) default 0")
    private Long size;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private ContentType type;

    // 경로 + filename
    @Column(length = 255, nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id", foreignKey = @ForeignKey(name = "fk_content_to_feedback_id"))
    private Feedback feedback;
}
