package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.dao.LoginTicketDao;
import cn.xstar.samplespringboot.dao.UserDao;
import cn.xstar.samplespringboot.pojo.LoginTicket;
import cn.xstar.samplespringboot.pojo.User;
import cn.xstar.samplespringboot.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class LoginRestService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;

    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isEmpty(username)) {
            map.put(Const.MSG, "用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)) {
            map.put(Const.MSG, "密码不能为空");
            return map;
        }

        User u = userDao.seletByName(username);
        if (u != null) {
            map.put(Const.MSG, "用户名已经被占用");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
//        user.setPassword(JblogUtil.MD5(password+user.getSalt()));
        String s = password + user.getSalt();
        user.setPassword(DigestUtils.md5DigestAsHex(s.getBytes()));
        user.setRole("user");
        userDao.insertUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 30);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));

        loginTicketDao.insertLoginTicket(loginTicket);

        return loginTicket.getTicket();
    }

    public int login(String username, String passwd) {
        User user = userDao.seletByName(username);
        if (StringUtils.isEmpty(username)) {
            return Const.LOGIN_NAME_EMPTY;
        }
        if (StringUtils.isEmpty(username)) {
            return Const.LOGIN_PASSWD_EMPTY;
        }
        if (user == null) {
            return Const.LOGIN_NO_USER;
        }
        String s = passwd + user.getSalt();
        String pwd = DigestUtils.md5DigestAsHex(s.getBytes());
        if (user.getPassword().equals(pwd)) {
            return user.getId();
        } else {
            return Const.LOGIN_WRONG_PASSSWD;
        }
    }

}
