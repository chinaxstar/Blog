package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.dao.LoginTicketRepository;
import cn.xstar.samplespringboot.dao.UserRepository;
import cn.xstar.samplespringboot.pojo.LoginTicket;
import cn.xstar.samplespringboot.pojo.User;
import cn.xstar.samplespringboot.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import java.util.*;

import static cn.xstar.samplespringboot.util.Const.LOGIN_SERVICE_NAME;

/**
 * 登录相关服务 注册、登录、添加更新登录票据等
 */
@Service(LOGIN_SERVICE_NAME)
public class LoginRestService {

	@Autowired
	private UserRepository userDao;
	@Autowired
	private LoginTicketRepository loginTicketDao;

	/**
	 * 票据保存时间/毫秒 15天
	 */
	private static final int TICKET_SAVED_TIME = 15 * 24 * 3600 * 1000;

	private User findUserByName(String username) {
		User temp = new User();
		temp.setName(username);
		return userDao.findOne(Example.of(temp, ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("id"))).get();
	}

	/**
	 * 注册账户 注册时新增账户，新增登录票据
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

		User u = findUserByName(username);
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
		userDao.save(user);

		String ticket = addLoginTicket(user.getId());
		map.put("ticket", ticket);

		return map;
	}

	public String addLoginTicket(long userId) {
		LoginTicket loginTicket = new LoginTicket();
		loginTicket.setUserId(userId);
		Date date = new Date();
		date.setTime(date.getTime() + TICKET_SAVED_TIME);
		loginTicket.setExpired(date);
		loginTicket.setStatus(0);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));

		loginTicketDao.save(loginTicket);

		return loginTicket.getTicket();
	}

	/**
	 * 更新登录票据状态
	 *
	 * @param userId
	 * @return
	 */
	public String updateLoginTicket(long userId) {
		LoginTicket loginTicket = loginTicketDao.findById(userId).get();
		if (loginTicket == null)
			return addLoginTicket(userId);
		else {
			Date date = new Date();
			date.setTime(date.getTime() + TICKET_SAVED_TIME);
			loginTicket.setExpired(date);
			loginTicketDao.save(loginTicket);
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
		User user = findUserByName(username);
		if (user != null) {
			String s = passwd + user.getSalt();
			String pwd = DigestUtils.md5DigestAsHex(s.getBytes());
			if (user.getPassword().equals(pwd)) {
				user.setPassword(updateLoginTicket(user.getId()));
				return user;
			}
		}
		return null;
	}

	/**
	 * 检测票据 根据浏览器保存的ticket验证是否要重新登录
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
				user = userDao.findById(loginTicket.getUserId()).get();
				user.setSalt(null);
			}
		}
		return user;
	}

}
