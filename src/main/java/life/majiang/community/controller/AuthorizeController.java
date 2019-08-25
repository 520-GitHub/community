package life.majiang.community.controller;/*
 *
 */

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHubUser;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import life.majiang.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${gitHub.client.id}")
    private String clientId;
    @Value("${gitHub.client.secret}")
    private String clientSecret;
    @Value("${gitHub.Redirect.uri}")
    private String uri;

    @Autowired
    private UserService userService;

    //     3.GitHub授权登入之获取index页面点击登录时-->回调redirect-uri携带的code
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        //创建AccessTokenDTO类，传入授权信息
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //6.配置文件设置，将信息配入application.properties
        // 以便于在不同环境修改不同配置时候不改变源码
        // 通过value("${}")进行读取
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = githubProvider.getUser(accessToken);
        //通过GitHub登入成功后，将生成和获取到的资料放到user对象中并存入数据库
        // 创建的资料：生成唯一的标识token、创建时间、更改时间、获得资料的name，id
        if (gitHubUser != null && gitHubUser.getId() != 0) {
            //当登入成功后，获取用户GitHub信息并且生成一个token
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            //创建或者更新user
            userService.createOrUpdate(user);
            //  通过response将token写入cookie
            response.addCookie(new Cookie("token", token));
            //登陆成功,写cookie和session
            // 7.1登录态：写入session后通过index页面中去判断有没有拿到session
//            request.getSession().setAttribute("user",gitHubUser);

            return "redirect:/";// 重定向到index
        } else {
            log.error("callback get gitHub error,{}",gitHubUser);
            //登入失败，重新登录
            return "redirect:/";// 重定向到index
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");

        //退出后删除cookie(方案：重新建立同名立即删除类型的Cookie)
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return"redirect:/";
    }

}

