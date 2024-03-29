package life.majiang.community.service;/*
 *
 */

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;


    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());

        List<User> users = userMapper.selectByExample(userExample);


        if (users.size() == 0) {
//            插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
//            更新

            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setToken(user.getToken());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());

            User dbUser = users.get(0);
            UserExample example = new UserExample();
                    example.createCriteria().andIdEqualTo(dbUser.getId());


            userMapper.updateByExampleSelective(updateUser, example);
        }

    }
}
