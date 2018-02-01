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

    /**
     * 查询作者名下文章
     *
     * @param id
     * @return
     */
    public List<Article> getUserArticles(int id) {
        //每个人的数据量比较小不考虑分页查询
        return articleDao.selectByAuthor(id);
    }

    /**
     * 查询作者名下的文章数量
     *
     * @param id
     * @return
     */
    public int getUserArticleCount(int id) {
        return articleDao.authorArticleCounts(id);
    }

    /**
     * 首页推荐的文章列表
     *
     * @return
     */
    public List<Article> getIndexArticle() {

        return articleDao.selectByLimit(0, 100);
    }

    /**
     * 根据文章id查找文章
     *
     * @param id
     * @return
     */
    public Article getArticleById(int id) {
        return articleDao.selectById(id);
    }

    /**
     * 添加新文章
     *
     * @param article
     */
    public Article addNewArticleOrUpdate(Article article) {
        try {
            if (article.getId() > 0) {
                articleDao.update(article);
                return article;
            }
            articleDao.insert(article);
            return articleDao.getNewAddArtcle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
