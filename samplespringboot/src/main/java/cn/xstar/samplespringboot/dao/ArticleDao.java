package cn.xstar.samplespringboot.dao;

import cn.xstar.samplespringboot.pojo.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
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

    @InsertProvider(type = ArticleSql.class, method = "insertByArticle")
    void insert(Article article);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    @Results({
            @Result(column = "authorId", property = "author", one = @One(
                    select = "cn.xstar.samplespringboot.dao.UserDao.seletById", fetchType = FetchType.EAGER))
    })
    Article selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where authorId=#{authorId}"})
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "keyWords", property = "keyWords"),
            @Result(column = "content", property = "content"),
            @Result(column = "createDate", property = "createDate"),
            @Result(column = "lastModifyDate", property = "lastModifyDate"),
            @Result(column = "authorId", property = "author", one = @One(
                    select = "cn.xstar.samplespringboot.dao.UserDao.seletOpenUserById", fetchType = FetchType.EAGER))
    })
    List<Article> selectByAuthor(int id);


    @Select({"select", "count(id)", "from", TABLE_NAME, "where authorId=#{authorId}"})
    int authorArticleCounts(int id);

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "keyWords", property = "keyWords"),
            @Result(column = "content", property = "content"),
            @Result(column = "createDate", property = "createDate"),
            @Result(column = "lastModifyDate", property = "lastModifyDate"),
            @Result(column = "authorId", property = "author", one = @One(
                    select = "cn.xstar.samplespringboot.dao.UserDao.seletOpenUserById", fetchType = FetchType.EAGER))
    })
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where keyWords like #{keyWords}"})
    List<Article> selectByKeywords(String keywords);

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "keyWords", property = "keyWords"),
            @Result(column = "content", property = "content"),
            @Result(column = "createDate", property = "createDate"),
            @Result(column = "lastModifyDate", property = "lastModifyDate"),
            @Result(column = "authorId", property = "author", one = @One(
                    select = "cn.xstar.samplespringboot.dao.UserDao.seletOpenUserById", fetchType = FetchType.EAGER))
    })
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "limit #{start},#{end}"})
    List<Article> selectByLimit(@Param("start") int start, @Param("end") int end);

    @Update({"update", TABLE_NAME, "set title = #{title},keyWords = #{keyWords},content = #{content},lastModifyDate = #{lastModifyDate} where id=#{id}"})
    void update(Article article);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void delateById(int id);

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "keyWords", property = "keyWords"),
            @Result(column = "content", property = "content"),
            @Result(column = "createDate", property = "createDate"),
            @Result(column = "lastModifyDate", property = "lastModifyDate"),
            @Result(column = "authorId", property = "author", one = @One(
                    select = "cn.xstar.samplespringboot.dao.UserDao.seletOpenUserById", fetchType = FetchType.EAGER))
    })
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "ORDER BY id DESC LIMIT 0,1"})
    Article getNewAddArtcle();
}
