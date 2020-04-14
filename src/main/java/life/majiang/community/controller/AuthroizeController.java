package life.majiang.community.controller;

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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    //这个路径是点登录的时候才会访问GitHub的一些路径，如果访问首页已经获取到用户，那么用户不会想去去点登录
    //这个map不会执行，也就不会老是connect reset
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest servletRequest,
                           HttpServletResponse servletResponse){
        //返回给浏览器的cookie在response中
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
            User user = new User();
            user.setAccountId(githubUser.getId());
            user.setGmtCreate(System.currentTimeMillis());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtModified(user.getGmtCreate());
            user.setName(githubUser.getName());
            userMapper.insert(user);
            //加入cookie
            servletResponse.addCookie(new Cookie("token",user.getToken()));
            //更新同一用户所有记录
            userMapper.updateByAccountId(user);
            servletRequest.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";

        }
    }
}
