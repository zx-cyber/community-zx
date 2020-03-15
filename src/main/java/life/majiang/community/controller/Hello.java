package life.majiang.community.controller;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class Hello {
    @Autowired
    private UserMapper userMapper;

    //访问这个根目录的时候就会访问主页，每次访问主页的时候去校验登录状态，就不用点登录了，这样的
    @GetMapping("/")
    public String hello(HttpServletRequest servletRequest){
        //response返回浏览器去写cookie的值，request是拿到携带的cookie，有一个内建的对象在框架里了
        //就是HttpServletRequest
        Cookie[] cookies = servletRequest.getCookies();
        //cookies.for tab快速对一个集合写for
        for (Cookie cookie : cookies) {
            //命中token
            if(cookie.getName().equals("token")){
                User user = userMapper.findByToken(cookie.getValue());
                if(user!=null){
                    //肯定把user返回去啊，不然信息怎么展示，下拉框里才有各种信息
                    //不能放到请求的范围中，
                    servletRequest.getSession().setAttribute("user",user);
                    //展示“我”还是登录
                }else{

                }
                break;
            }
        }
        return "hello";
    }
}
