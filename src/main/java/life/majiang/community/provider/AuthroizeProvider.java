package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.DataTransferObject.AccessTokenDTO;
import life.majiang.community.DataTransferObject.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by zx on 2020/3/12 21:24
 */
@Component
public class AuthroizeProvider {
    //获取access_token  用post
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        try {
            Response execute = client.newCall(request).execute();
            String response = execute.body().string();
            String accessToken = response.split(" ")[0].split("=")[1];
            return  accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //快速用代码实现get post请求
    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request= new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respString = response.body().string();
            GithubUser githubUser = JSON.parseObject(respString,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
