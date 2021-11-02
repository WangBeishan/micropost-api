package cf.beishan.micropostapi.controller;

import cf.beishan.micropostapi.bean.User;
import cf.beishan.micropostapi.dao.UserDao;
import cf.beishan.micropostapi.util.GenerateMosaicHeadImg;
import com.alibaba.fastjson.JSON;
import jdk.swing.interop.SwingInterOpUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender mailSender;

    private Integer code;

    @PostMapping("/register")
    public String register(@RequestBody User user) throws IOException {
        String md5Passwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setState(true);
        user.setPassword(md5Passwd);
        String path = GenerateMosaicHeadImg.generator(user.getEmail());
        user.setAvatarPath(path);
        int i = userDao.addUser(user);
        return i > 0 ? "success" : "error";
    }

    @PostMapping("/sendVC")
    public String sendVerifyCode(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
        System.out.println(user.getEmail());
        Random random = new Random(new Date().getTime());
        code = random.nextInt(100000);
        String toAddress = user.getEmail();
        String fromAddress = "305304442@qq.com";
        String senderName = "Sunmonkey";
        String subject = "Please verify your registration";
        String content = "Dear " + user.getUsername() +",\n"
                + "Your verify code:\n"
                + code;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content);
        mailSender.send(message);
        return code.toString();
    }



    @PostMapping("/getUserByEmail")
    public String getUserByEmail(@RequestBody User user1) {
        User user = userDao.getUserByEmail(user1.getEmail());
        System.out.println(user);
        if (user != null) {
            return JSON.toJSONString(user.getEmail());
        }
        return "null";
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") Integer id) throws IOException {
        User user = userDao.getUserById(id);
        System.out.println(id);
        if (user != null) {
            return JSON.toJSONString(user);
        }
        return null;
    }

    @GetMapping("/getAllUser")
    public String getAllUser() throws IOException {
        List<User> users = userDao.getAllUser();
        return JSON.toJSONString(users);
    }

    @PostMapping("/getHeadImg")
    public String getUserHeadImgPath(@RequestBody String email) {
        if (!email.equals(null)) {
            String path = userDao.getUserByEmail(email).getAvatarPath();
            File file = new File(path);
            System.out.println(file);
            return JSON.toJSONString(file);
        }
        return null;
    }
}
