package life.majiang.community.DataTransferObject;

import lombok.Data;

/**
 * Created by zx on 2020/3/14 16:45
 */

@Data
public class GithubUser {
    private String id;
    private String name;
    //头像
    private String avatar_url;
    private String bio;


}
