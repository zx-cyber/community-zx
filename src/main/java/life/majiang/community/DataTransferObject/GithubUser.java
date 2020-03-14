package life.majiang.community.DataTransferObject;

/**
 * Created by zx on 2020/3/14 16:45
 */
public class GithubUser {
    private String id;
    private String name;
    //头像
    private String avatar_url;
    private String bio;

    @Override
    public String toString() {
        return "GithubUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
