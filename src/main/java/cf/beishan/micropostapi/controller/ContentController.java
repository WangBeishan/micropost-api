package cf.beishan.micropostapi.controller;

import cf.beishan.micropostapi.bean.Content;
import cf.beishan.micropostapi.bean.UserContent;
import cf.beishan.micropostapi.bean.dto.ContentDto;
import cf.beishan.micropostapi.dao.ContentDao;
import cf.beishan.micropostapi.dao.UserDao;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ContentController {

    @Autowired
    private ContentDao contentDao;
    @Autowired
    private UserDao userDao;

    @PostMapping("/post")
    public String postContent(@RequestBody UserContent userContent) {
        String flag = "success";
        if (userContent == null) {
            flag = "error";
        }
        Content content = new Content();
        content.setUserId(userContent.getUserId());
        content.setContent(userContent.getContent());
        content.setState(true);
        contentDao.addContent(content);

        HashMap<String, Object> data = new HashMap<>();
        data.put("flag", flag);
        data.put("content", content);
        String res = JSON.toJSONString(data);
        return res;
    }

    @GetMapping("/getContent")
    public String getAllContent() {
        List<Content> contents= contentDao.getAllContent();
        List<ContentDto> contentDtos = new ArrayList<>();
        for (Content content : contents) {
            ContentDto contentDto = new ContentDto();
            contentDto.setUserId(content.getUserId());
            String username = (userDao.getUserById(content.getUserId())).getUsername();
            String email = userDao.getUserById(content.getUserId()).getEmail();
            contentDto.setEmail(email);
            contentDto.setUsername(username);
            contentDto.setContent(content.getContent());
            contentDto.setCrtTime(content.getCrtTime());
            contentDtos.add(contentDto);
        }
        String res = JSON.toJSONString(contentDtos);
        return res;
    }

    @GetMapping("/contentByUserId/{userId}")
    public String getContentByUserId(@PathVariable("userId") int userId) {
        List<Content> contents = contentDao.getContentByUserId(userId);
        if (contents == null) {
            return null;
        }
        List<ContentDto> contentDtos = new ArrayList<>();
        for (Content content : contents) {
            ContentDto contentDto = new ContentDto();
            contentDto.setUserId(content.getUserId());
            String username = (userDao.getUserById(content.getUserId())).getUsername();
            String email = userDao.getUserById(content.getUserId()).getEmail();
            contentDto.setEmail(email);
            contentDto.setUsername(username);
            contentDto.setContent(content.getContent());
            contentDto.setCrtTime(content.getCrtTime());
            contentDtos.add(contentDto);
        }
        return JSON.toJSONString(contentDtos);
    }
}
