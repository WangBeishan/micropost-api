package cf.beishan.micropostapi.controller;

import cf.beishan.micropostapi.bean.User;
import cf.beishan.micropostapi.dao.UserDao;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        String flag = "error";
        String passwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        User loginUser = userDao.getUserByMessage(user.getEmail(), passwd);
        if (loginUser != null) {
            flag = "ok";
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("flag", flag);
        res.put("user", loginUser);
        return JSON.toJSONString(res);
    }
}
