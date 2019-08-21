package life.majiang.community.provider;/*
 *
 */

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class GithubProvider {
    /**
     * 4.access_token携带code传输给接收方
     * 获取返会access_token并对其进行解析获得token
     * @param accessTokenDTO
     * @return
     *
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //返会:access_token=221b1573e69877ebf91ecd8765e1482034d19c3e
            // &scope=user&token_type=bearer
            //  将其进行切割。获得token
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *5.user携带access_token传输给接收方
     *以便获取GitHub授权的资料信息User
     * @param accessToken
     * @return
     */
    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (
                Response response = client.newCall(request).execute()) {
                String string = response.body().string();
//               通过parseObject 将string自动转换为一个java类对象
                GitHubUser gitHubUser =  JSON.parseObject(string,GitHubUser.class);
                return gitHubUser;
        } catch (IOException e) {e.printStackTrace(); }
        return null;
    }

}
