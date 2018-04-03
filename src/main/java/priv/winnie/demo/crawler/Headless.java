package priv.winnie.demo.crawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Headless {
	
	public static void main(String[] args) {
        
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
		String path = System.getProperty("user.dir");
        System.setProperty("webdriver.gecko.driver", path + "\\drivers\\geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        WebDriver driver = new FirefoxDriver(firefoxOptions);
        log.info("Firefox Headless Browser Invoked");
        driver.get("http://www.baidu.com");
        log.info("Page Title is : " + driver.getTitle());
        driver.quit();
	}

}
