package cn.xstar.samplespringboot.pojo;

/**
 * 赞
 */
public class Vote {
    private int id;
    /**
     * 文章id
     */
    private int acticleId;
    /**
     * 用户id
     */
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
