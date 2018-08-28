package cn.xstar.samplespringboot.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.xstar.samplespringboot.pojo.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Query("select u from Article u where author_id = ?1")
	List<Article> selectByAuthor(long id, Pageable page);

	@Query("select count(id) from Article where author_id = ?1")
	int authorArticleCounts(long id);
	
	@Query("select u from Article u where keyWords like %?1%")
	List<Article> selectByKeywords(String keywords);


}
