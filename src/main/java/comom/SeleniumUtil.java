package comom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SeleniumUtil {
    private SeleniumUtil(){}

    private static WebDriver driver = null;
    private static long count = 0;

    private static void init() {
        System.setProperty("webdriver.gecko.driver","C:/Users/Jonathan/Documents/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver","C:/Users/Jonathan/Documents/Selenium/chromedriver.exe");
    }

    public static WebDriver getDriver(){
        count++;
        if(driver == null){
            init();
//            FirefoxOptions firefoxOptions = new FirefoxOptions();
//            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0";
//            firefoxOptions.addPreference("general.useragent.override", userAgent);
//            firefoxOptions.addArguments(String.format("user-agent=%s", userAgent));
//            driver = new FirefoxDriver(firefoxOptions);

            ChromeOptions options = new ChromeOptions();
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36";

            options.addArguments("start-maximized");

            options.addArguments(String.format("user-agent=%s", userAgent));
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-blink-features=AutomationControlled");

            if( isUsingChromeProfile() ) {
                options.addArguments("user-data-dir=C:/Users/Jonathan/Documents/Selenium");
                options.addArguments("profile-directory=Profile 1");
            }

            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        }

        return driver;
    }

    public static void closeDriver(){
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }

    public static boolean isUsingChromeProfile(){
        return true;
    }
}
