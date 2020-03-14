package life.majiang.community.controller;

import life.majiang.community.DataTransferObject.AccessTokenDTO;
import life.majiang.community.DataTransferObject.GithubUser;
import life.majiang.community.provider.AuthroizeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthroizeController {
    @Autowired
    private AuthroizeProvider authroizeProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("ba24286d5b9eeda4784a");
        accessTokenDTO.setClient_secret("87d760cbeab210f01a86074ceef951f48740696b");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:9007/callback");
        accessTokenDTO.setState(state);
        String accessToken = authroizeProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = authroizeProvider.getGithubUser(accessToken);
        return "hello";
    }
}
