package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.dao.LoginTicketDao;
import cn.xstar.samplespringboot.dao.UserDao;
import cn.xstar.samplespringboot.pojo.LoginTicket;
import cn.xstar.samplespringboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 首页服务
 */
@Service
public class IndexService {

    @Autowired
    private LoginTicketDao ticketDao;
    @Autowired
    private UserDao userDao;

    /**
     * 检测票据
     * 根据浏览器保存的ticket验证是否要重新登录
     *
     * @param ticket
     * @return 账户信息
     */
    public User checLoginState(String ticket) {
        User user = null;
        if (StringUtils.isEmpty(ticket)) {
            LoginTicket loginTicket = ticketDao.seletByTicket(ticket);
            /**
             * 当前时间小于过期时间
             */
            if (loginTicket != null && loginTicket.getExpired().before(new Date())) {
                user = userDao.seletById(loginTicket.getUserId());
            }
        }
        return user;
    }
}
