package priv.winnie.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

import lombok.extern.slf4j.Slf4j;
import priv.winnie.demo.constant.ResultEnum;
import priv.winnie.demo.entity.Result;
import priv.winnie.demo.entity.SysUser;
import priv.winnie.demo.exception.ResultException;
import priv.winnie.demo.mapper.TestMapper;
import priv.winnie.demo.rabbitmq.HelloSender;
import priv.winnie.demo.receiver.Receiver;
import priv.winnie.demo.service.EmailService;
import priv.winnie.demo.service.QrCodeService;
import priv.winnie.demo.util.ResultUtils;

@EnableTransactionManagement  // 需要事务的时候加上
@RestController
@Slf4j
public class BussinessController {
	
	private static final String LOGIN_KEY = "key.value.login.";
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TestMapper testMapper;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private HelloSender helloSender;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Autowired
    private QrCodeService qrCodeService;
	
	@Autowired
	private Receiver receiver;
	
	@RequestMapping("/mongoTest")
	public List<SysUser> saveUser() {
		Random random = new Random();
		SysUser user = new SysUser();
        user.setId(Math.abs(random.nextLong()));
        user.setUserName("小明");
        user.setPassWord("fffooo123");
        mongoTemplate.save(user);
        List<SysUser> list = mongoTemplate.find(new Query(), SysUser.class);
        return list;
    }
	
	@RequestMapping("/sendMail")
	public String index() {
        emailService.send();
        return "Hello World";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result<Object> ajaxLogin(String username, String password) {
		try {
			AuthenticationToken token = new UsernamePasswordToken(username, password);
			SecurityUtils.getSubject().login(token);
			return ResultUtils.success();
		} catch (Exception e) {
			throw new ResultException(ResultEnum.FAIL);
		}
    }
	
	@RequestMapping("/rabbitmq")
	public String rabbitmqTest() {
		helloSender.send();
        return "Hello World";
    }
	
	@RequestMapping("/resultException")
	public String resultException() throws Exception {
		throw new ResultException(ResultEnum.FAIL);
    }
	
	@RequestMapping("/resultUtil")
	public Result<Object> resultUtil() {
		return ResultUtils.success("成功");
    }
	
	@RequestMapping("/exception")
	public String exception() throws Exception {
		throw new Exception();
    }
	
	@RequestMapping("/selectAll")
	@Transactional  // 需要事务的时候加上
    public List<SysUser> selectAll() {
        return testMapper.selectAll();
    }
	
	@RequestMapping("/selectById")
	@Cacheable(value="user-key", keyGenerator="keyGenerator")
    public SysUser selectById() {
        return testMapper.selectById(16L);
    }
	
	@RequestMapping("/redisCache")
	@Cacheable(value="user-key", key="'role'")
	public String redisCache() {
		String value = redisTemplate.opsForValue().get("11").toString();
		log.info("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
		return value;
	}
	
	@RequestMapping("/lettuce")
	public String lettuce() {
		// RedisClient client = RedisClient.create(RedisURI.create("redis-sentinel://192.168.18.121:7000,192.168.18.121:7001,192.168.18.121:7002/0#mymaster"));
		RedisClient client = RedisClient.create(RedisURI.create("redis://192.168.18.235"));
		StatefulRedisConnection<String, String> connection = client.connect();
		RedisCommands<String, String> commands = connection.sync();
		String value = commands.get("11");
		connection.close();
		client.shutdown();
		return value;
	}
	
	@RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
	
	@RequestMapping(value = "/session", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getSession(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SessionId", request.getSession().getId());
        return map;
    }
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/getQrCode")
	public Object getQrCode(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String loginId = UUID.randomUUID().toString().replaceAll("-", "");
		result.put("loginId", loginId);
		// app端登录地址
		String loginUrl = "http://localhost:8080/demo/setUser/" + loginId;
		log.info("loginUrl: {}", loginUrl);
		result.put("image", qrCodeService.createQrCode(loginUrl));
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(LOGIN_KEY + loginId, loginId, 5, TimeUnit.MINUTES);
        return result;
    }
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/getResponse/{loginId}")
	public Callable<Map<String, Object>> getResponse(@PathVariable String loginId, HttpSession session) {
		Callable<Map<String, Object>> callable = () -> {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("loginId", loginId);
			try {
				ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
				String user = opsForValue.get(LOGIN_KEY + loginId);
				if (user == null) {
					result.put("success", false);
					result.put("status", "refresh");
					return result;
				}
				// 已登录
				if (!user.equals(loginId)) {
					// 登录成,认证信息写入session
					session.setAttribute("user", user);
					result.put("success", true);
					result.put("status", "ok");
					return result;
				}
				try {
					// 线程等待30秒
					receiver.getLoginLatch(loginId).await(30, TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				result.put("success", false);
				result.put("status", "waiting");
				return result;
			} finally {
				// 移除登录请求
				receiver.removeLoginLatch(loginId);
			}
		};
		return callable;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/setUser/{loginId}")
	public Map<String, Object> setUser(@PathVariable String loginId) {
		
		String user = "user";

		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		String value = opsForValue.get(LOGIN_KEY + loginId);

		if (value != null) {
			// 保存认证信息
			opsForValue.set(LOGIN_KEY + loginId, user, 1, TimeUnit.MINUTES);

			// 发布登录广播消息
			receiver.receiveLogin(loginId);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("loginId", loginId);
		result.put("user", user);
		return result;
	}

}
