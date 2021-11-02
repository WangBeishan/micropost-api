package cf.beishan.micropostapi.dao;

import cf.beishan.micropostapi.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    public User getUserByMessage(@Param("email") String email,
                                 @Param("password") String password);

    public int addUser(User user);

    public User getUserByEmail(String email);

    public User getUserById(Integer id);

    public List<User> getAllUser();
}
