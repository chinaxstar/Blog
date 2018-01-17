package cn.xstar.samplespringboot.pojo;

import java.util.List;

/**
 * 接口标准返回数据
 *
 * @param <T>
 * @author xstar
 * @date 2018-01-01
 */
public class Data<T> {
    private String msg;
    private int code;
    private long t;
    private List<T> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
