package life.majiang.community.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import life.majiang.community.DataTransferObject.AccessTokenDTO;
import life.majiang.community.DataTransferObject.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.AuthroizeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthroizeController {
    @Autowired
    private AuthroizeProvider authroizeProvider;

    @Autowired
    private UserMapper userMapper;

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
                           @RequestParam("state") String state,
                           HttpSession session){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = authroizeProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = authroizeProvider.getGithubUser(accessToken);
        // 获取到user之后，存入数据库
        if (githubUser!=null){
            //查找数据库有无此account_id的记录，没有则插入
            User dbUser = userMapper.findByAccountId(githubUser.getId());
            if(dbUser==null){
                User user = new User();
                user.setAccountId(githubUser.getId());
                user.setGmtCreate(System.currentTimeMillis());
                user.setToken(UUID.randomUUID().toString());
                user.setGmtModified(user.getGmtCreate());
                user.setName(githubUser.getName());
                userMapper.insert(user);
            }else{
                //有则更新
                dbUser.setGmtModified(System.currentTimeMillis());
                userMapper.updateByAccountId(dbUser);
            }
            session.setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";

        }
    }
}
