package priv.winnie.demo.receiver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	public static final String TOPIC_NAME = "login";
	private Map<String, CountDownLatch> loginMap = new ConcurrentHashMap<String, CountDownLatch>();
	
	public void receiveLogin(String loginId) {
		
		if (loginMap.containsKey(loginId)) {
			CountDownLatch latch = loginMap.get(loginId);
			if (latch != null) {
				// 唤醒登录等待线程
				latch.countDown();
			}
		}
	}
	
	public CountDownLatch getLoginLatch(String loginId) {
		
		CountDownLatch latch = null;
		if (!loginMap.containsKey(loginId)) {
			latch = new CountDownLatch(1);
			loginMap.put(loginId, latch);
		} else
			latch = loginMap.get(loginId);

		return latch;
	}
	
	public void removeLoginLatch(String loginId) {
		if (loginMap.containsKey(loginId)) {
			loginMap.remove(loginId);
		}
	}

}
