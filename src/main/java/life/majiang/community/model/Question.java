package life.majiang.community.model;

import lombok.Data;

/**
 * Created by zx on 2020/4/19 11:02
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount = 0;
    private Integer viewCount = 0;
    private Integer likeCount = 0;
    private String tag;
    private Integer creator;
}
