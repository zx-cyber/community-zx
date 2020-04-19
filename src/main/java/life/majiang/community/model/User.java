package life.majiang.community.model;

import lombok.Data;

/**
 * Created by zx on 2020/3/15 13:26
 */
//映射数据库表（模型），dto在类与类之间传输
@Data
public class User {
    private int id;
    private String accountId;
    private String name;
    //给cookie的认证？
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
