package life.majiang.community.controller;

import life.majiang.community.DataTransferObject.AccessTokenDTO;
import life.majiang.community.DataTransferObject.GithubUser;
import life.majiang.community.provider.AuthroizeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthroizeController {
    @Autowired
    private AuthroizeProvider authroizeProvider;

    //要有个${}取值符啊
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    //value标签的作用，框架启动的时候，去application.properties文件中读取，存到一个map中
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = authroizeProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = authroizeProvider.getGithubUser(accessToken);
        return "hello";
    }
}
