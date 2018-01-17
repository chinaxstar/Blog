package cn.xstar.samplespringboot.util;


import cn.xstar.samplespringboot.pojo.Data;
import cn.xstar.samplespringboot.pojo.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class JacksonUtilTests {

    @Test
    public void objectToJsonTest() {
        String json = JacksonUtil.toJson(null);
        Assert.assertEquals(json, null);
        json = JacksonUtil.toJson("");
        Assert.assertEquals(json, "\"\"");
        json = JacksonUtil.toJson("123");
        Assert.assertEquals(json, "\"123\"");
        User user = new User();
        user.setName("123");
        user.setRole("管理员");
        user.setSalt("23423423");
        user.setId(123);
        user.setPassword("jcashduishdqe12");
        json = JacksonUtil.toJson(user);
        Assert.assertEquals("{\"id\":123,\"name\":\"123\",\"password\":\"jcashduishdqe12\",\"salt\":\"23423423\",\"role\":\"管理员\"}", json);
        Data<User> data = new Data<>();
        data.setData(Collections.singletonList(user));
        json = JacksonUtil.toJson(data);
        Assert.assertEquals("{\"code\":0,\"t\":0,\"data\":[{\"id\":123,\"name\":\"123\",\"password\":\"jcashduishdqe12\",\"salt\":\"23423423\",\"role\":\"管理员\"}]}", json);
    }
}
