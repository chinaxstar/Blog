package cn.xstar.samplespringboot.pojo;

import java.util.Date;

/**
 * 登录票据
 * 注册时添加登录票据
 * 登录时修改票据状态和时间，前端cookie保存ticket
 */
public class LoginTicket {
    private int id;
    private int userId;
    private int status;
    private Date expired;
    private String ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
