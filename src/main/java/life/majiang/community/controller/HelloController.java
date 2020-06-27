package life.majiang.community.controller;

import life.majiang.community.DataTransferObject.PaginationDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    //访问这个根目录的时候就会访问主页，每次访问主页的时候去校验登录状态，就不用点登录了，这样的
    @GetMapping("/")
    public String hello(HttpServletRequest servletRequest,
                        Model model, @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "2")Integer size){
        //response返回浏览器去写cookie的值，request是拿到携带的cookie，有一个内建的对象在框架里了
        //就是HttpServletRequest
        Cookie[] cookies = servletRequest.getCookies();
        //cookies.for tab快速对一个集合写for
        if(cookies!=null&&cookies.length!=0){
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
        }
        PaginationDTO paginationDTO = questionService.queryQuestions(page,size);
        model.addAttribute("pagination",paginationDTO);
        return "hello";
    }
}
