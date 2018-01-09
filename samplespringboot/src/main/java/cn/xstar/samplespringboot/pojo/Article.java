package cn.xstar.samplespringboot.pojo;

import java.util.Date;

/**
 * 文章
 */
public class Article {
    /**
     * 文章id
     */
    private int id;
    /**
     * 作者id
     */
    private int authorId;
    /**
     * 标题
     */
    private String title;
    /**
     * 关键字
     */
    private String keyWords;

    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}
