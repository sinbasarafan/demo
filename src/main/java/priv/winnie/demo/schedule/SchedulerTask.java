package priv.winnie.demo.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SchedulerTask {
	
	private int count = 0;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	// 执行简单的定时任务
	// @Scheduled(cron = "*/6 * * * * ?")
    private void process() {
        log.info("this is scheduler task runing  " + (count++));
    }
	
	// @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
		log.info("现在时间：" + dateFormat.format(new Date()));
    }

}
