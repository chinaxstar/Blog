package cn.xstar.samplespringboot.dao;

import cn.xstar.samplespringboot.pojo.Article;
import org.apache.ibatis.jdbc.SQL;

public class ArticleSql {
    String INSERT_FILEDS = "authorId,title,keyWords,content,createDate,lastModifyDate";
    String SELECT_FIELDS = "id," + INSERT_FILEDS;
    public String insertByArticle(final Article article){
        return new SQL(){
            {INSERT_INTO("article");INTO_COLUMNS(INSERT_FILEDS);
            INTO_VALUES(Integer.toString(article.getAuthor().getId()),
                    article.getKeyWords(),
                    article.getContent(),
                    article.getCreateDate().toString(),
                    article.getLastModifyDate().toString());

            }
        }.toString();
    }
}
