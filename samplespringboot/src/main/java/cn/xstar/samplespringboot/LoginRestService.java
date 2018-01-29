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
import java.util.concurrent.TimeUnit;

import static cn.xstar.samplespringboot.util.Const.LOGIN_SERVICE_NAME;

/**
 * 登录相关服务
 * 注册、登录、添加更新登录票据等
 */
@Service(LOGIN_SERVICE_NAME)
public class LoginRestService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;

    /**
     * 票据保存时间/毫秒
     * 15天
     */
    private static final int TICKET_SAVED_TIME = 15 * 24 * 3600 * 1000;

    /**
     * 注册账户
     * 注册时新增账户，新增登录票据
     *
     * @param username
     * @param password
     * @return
     */
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
        date.setTime(date.getTime() + TICKET_SAVED_TIME);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));

        loginTicketDao.insertLoginTicket(loginTicket);

        return loginTicket.getTicket();
    }

    /**
     * 更新登录票据状态
     *
     * @param userId
     * @return
     */
    public String updateLoginTicket(int userId) {
        LoginTicket loginTicket = loginTicketDao.seletByUserId(userId);
        if (loginTicket == null) return addLoginTicket(userId);
        else {
            Date date = new Date();
            date.setTime(date.getTime() + TICKET_SAVED_TIME);
            loginTicket.setExpired(date);
            loginTicketDao.updateStatus(loginTicket.getTicket(), date, 0);
            return loginTicket.getTicket();
        }
    }

    /**
     * 验证登录
     *
     * @param username
     * @param passwd
     * @return 账户, 密码信息替换成ticket
     */
    public User login(String username, String passwd) {
        User user = userDao.seletByName(username);
        String s = passwd + user.getSalt();
        String pwd = DigestUtils.md5DigestAsHex(s.getBytes());
        if (user.getPassword().equals(pwd)) {
            user.setPassword(updateLoginTicket(user.getId()));
            return user;
        }
        return null;
    }

    /**
     * 检测票据
     * 根据浏览器保存的ticket验证是否要重新登录
     *
     * @param ticket
     * @return 账户信息
     */
    public User checLoginState(String ticket) {
        User user = null;
        if (!StringUtils.isEmpty(ticket)) {
            LoginTicket loginTicket = loginTicketDao.seletByTicket(ticket);
            /**
             * 当前时间小于过期时间
             */
            if (loginTicket != null && loginTicket.getExpired().after(new Date())) {
                user = userDao.seletById(loginTicket.getUserId());
                user.setSalt(null);
            }
        }
        return user;
    }

}
