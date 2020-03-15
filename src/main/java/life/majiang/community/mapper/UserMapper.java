package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zx on 2020/3/15 13:23
 */
//操作user表的mapper
@Mapper
@Repository
public interface UserMapper {

    //自动调用model中的getter方法完成注入，所以可以直接写这个变量的属性，不用写user.gmtCreate
    @Insert("insert into user (account_id,name,gmt_create,gmt_modified," +
            "token,) values (#{accountId},#{name},#{gmtCreate},#{gmtModified}," +
            "#{token})")
    void insert(User user);

    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
    List<User> findByAccountId(String accountId);

    //时间和名字会变
    @Update("update user set gmt_modified=#{gmtModified}," +
            "name=#{name} where account_id=#{accountId}")
    void updateByAccountId(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);
}
