package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zx on 2020/4/19 10:57
 */
@Mapper
@Repository
public interface QuestionMapper {
    //指定哪些就插哪些？
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count," +
            "view_count,like_count,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified}," +
            "#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    //非对象参数时要手动用注解做一个映射，变量名和参数名
    @Select("select * from question limit #{offset},#{size}")
    List<Question> queryQuestions(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

}
