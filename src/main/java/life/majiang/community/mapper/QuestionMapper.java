package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by zx on 2020/4/19 10:57
 */
@Mapper
@Repository
public interface QuestionMapper {
    //指定哪些就插哪些？
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count," +
            "view_count,like_count,tag) values (#{title},#{description},#{gmt_create},#{gmt_modified}," +
            "#{creator},#{comment_count},#{view_count},#{like_count},#{tag})")
    void create(Question question);
}
