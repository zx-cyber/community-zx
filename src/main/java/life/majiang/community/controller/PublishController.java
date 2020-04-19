package life.majiang.community.controller;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zx on 2020/4/14 22:03
 */
@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish/question")
    public String publishQuestion(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "tag") String tag,
            HttpServletRequest servletRequest,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        Question question = new Question();
        if (StringUtils.isEmpty(title)) {
            model.addAttribute("error", "标题不能为空");
            return "/publish";
        }
        if (StringUtils.isEmpty(description)) {
            model.addAttribute("error", "内容不能为空");
            return "/publish";
        }
        if (StringUtils.isEmpty(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "/publish";
        }
        User user = null;
        Cookie[] cookies = servletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                user = userMapper.findByToken(cookie.getValue());
                if(user!=null){
                    servletRequest.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "/publish";

        }
        question.setCreator(user.getId());
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(System.currentTimeMillis());
        questionMapper.create(question);
        return "redirect:/";
    }
}
