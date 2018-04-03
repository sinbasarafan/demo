package priv.winnie.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 30, redisNamespace="priv.winnie.demo") // 将session托管到redis中，session过期时间为30分钟
public class SessionConfig {

}
