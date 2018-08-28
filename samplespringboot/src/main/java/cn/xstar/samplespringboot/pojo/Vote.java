package cn.xstar.samplespringboot.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 赞
 */
@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
	 * 文章id
	 */
	private int acticleId;
	/**
	 * 用户id
	 */
	private int userId;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getActicleId() {
		return acticleId;
	}

	public void setActicleId(int acticleId) {
		this.acticleId = acticleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
