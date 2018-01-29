package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.dao.ArticleDao;
import cn.xstar.samplespringboot.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关于文章的服务
 */
@Service
public class ArticleRestService {

    @Autowired
    ArticleDao articleDao;

    public List<Article> getUserArticles(int id) {
        //每个人的数据量比较小不考虑分页查询
        return articleDao.selectByAuthor(id);
    }

    public int getUserArticleCount(int id) {
        return articleDao.authorArticleCounts(id);
    }
}
