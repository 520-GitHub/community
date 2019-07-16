package life.majiang.community.controller;/*
 *
 */

import jdk.nashorn.internal.parser.Token;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
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
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${gitHub.client.id}")
    private String clientId;
    @Value("${gitHub.client.secret}")
    private String clientSecret;
    @Value("${gitHub.Redirect.uri}")
    private  String uri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = githubProvider.getUser(accessToken);
        if(gitHubUser!= null){
            //当登入成功后，将资料放入user并存储到数据库中
            User user = new User();
            //  生成一个唯一标识token
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //  将token写入cookie中
            response.addCookie(new Cookie("token", token));
            //登陆成功,写cookie和session

            request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";// 重定向到index


        }else {//登入失败，重新登录
           return "redirect:/";// 重定向到index
        }
    }
}
