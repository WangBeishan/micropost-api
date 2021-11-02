package cf.beishan.micropostapi.dao;

import cf.beishan.micropostapi.bean.Content;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentDao {

    public int addContent(Content content);

    public List<Content> getAllContent();

    public List<Content> getContentByUserId(int id);
}
