package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.dao.ArticleRepository;
import cn.xstar.samplespringboot.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关于文章的服务
 */
@Service
public class ArticleRestService {

	@Autowired
	ArticleRepository articleDao;

	/**
	 * 查询作者名下文章
	 *
	 * @param id
	 * @return
	 */
	@Transactional
	public List<Article> getUserArticles(int id) {
		PageRequest pageRequest = new PageRequest(1, 100);
		// 每个人的数据量比较小不考虑分页查询
		return articleDao.selectByAuthor(id, pageRequest);
	}

	/**
	 * 查询作者名下的文章数量
	 *
	 * @param id
	 * @return
	 */
	@Transactional
	public int getUserArticleCount(long id) {

		return articleDao.authorArticleCounts(id);
	}

	/**
	 * 首页推荐的文章列表
	 *
	 * @return
	 */
	@Transactional
	public List<Article> getIndexArticle() {
		PageRequest pageRequest = new PageRequest(1, 100);
		return articleDao.selectByAuthor(0, pageRequest);
	}

	/**
	 * 根据文章id查找文章
	 *
	 * @param id
	 * @return
	 */
	@Transactional
	public Article getArticleById(long id) {
		//
		return articleDao.findOne(id);
	}

	/**
	 * 添加新文章
	 *
	 * @param article
	 */
	@Transactional
	public Article addNewArticleOrUpdate(Article article) {
		try {
			if (article.getId() > 0) {
				articleDao.save(article);
				return article;
			}
			return articleDao.saveAndFlush(article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
