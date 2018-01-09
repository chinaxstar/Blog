package cn.xstar.samplespringboot.dao;

import cn.xstar.samplespringboot.pojo.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
@Mapper
public interface ArticleDao {
    String TABLE_NAME = "article";
    String INSERT_FILEDS = "authorId,title,keyWords,content,createDate,lastModifyDate";
    String SELECT_FIELDS = "id," + INSERT_FILEDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FILEDS, ")", "values(#{authorId},#{title},#{keyWords},#{content},#{createDate},#{lastModifyDate})"})
    void intsert(Article article);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Article selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where authorId=#{authorId}"})
    List<Article> selectByAuthor(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where keyWords like #{keyWords}"})
    List<Article> selectByKeywords(String keywords);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "limit #{start},#{end}"})
    List<Article> selectByLimit(@Param("start") int start, @Param("end") int end);

    @Update({"update", TABLE_NAME, "set title = #{title},keyWords = #{keyWords},content = #{content},lastModifyDate = #{lastModifyDate} where id=#{id}"})
    void update(Article article);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void delateById(int id);
}
