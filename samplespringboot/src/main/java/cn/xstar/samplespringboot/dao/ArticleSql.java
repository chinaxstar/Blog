package cn.xstar.samplespringboot.dao;

import cn.xstar.samplespringboot.pojo.Article;
import org.apache.ibatis.jdbc.SQL;
import org.thymeleaf.util.DateUtils;

import java.sql.Date;
import java.util.Locale;

public class ArticleSql {
    String INSERT_FILEDS = "authorId,title,keyWords,content,createDate,lastModifyDate";
    String SELECT_FIELDS = "id," + INSERT_FILEDS;

    public String insertByArticle(Article article) {
        String pattern = "yyyy-MM-dd";

        SQL sql = new SQL();
        sql.INSERT_INTO("article");
        sql.INTO_COLUMNS(INSERT_FILEDS);
        sql.INTO_VALUES(Integer.toString(article.getAuthor().getId()),
                "'" + article.getTitle() + "'",
                "'" + article.getKeyWords() + "'",
                "'" + article.getContent() + "'",
                "'" + DateUtils.format(article.getCreateDate(), pattern, Locale.getDefault()) + "'",
                "'" + DateUtils.format(article.getLastModifyDate(), pattern, Locale.getDefault()) + "'");

        return sql.toString();
    }
}
