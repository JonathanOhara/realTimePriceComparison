package comom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SeleniumUtil {
    private SeleniumUtil(){}

    private static WebDriver driver = null;
    private static long count = 0;
    private static boolean usingChromeProfile = false;

    private static void init() {
        System.setProperty("webdriver.gecko.driver","C:/Users/Jonathan/Documents/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver","C:/Users/Jonathan/Documents/Selenium/chromedriver.exe");
    }

    public static WebDriver getDriver(){
        count++;
        if(driver == null){
            init();

            getFirefoxDriver();
//            getChromeDriver();

            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        }

        return driver;
    }

    private static void getFirefoxDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0";
//        firefoxOptions.addPreference("general.useragent.override", userAgent);
//        firefoxOptions.addArguments(String.format("user-agent=%s", userAgent));

        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");

        FirefoxProfile profile = new FirefoxProfile(new File("C:/Users/Jonathan/AppData/Roaming/Mozilla/Firefox/Profiles/1s7tupo0.default-release"));
        firefoxOptions.setProfile(profile);

        driver = new FirefoxDriver(firefoxOptions);
    }

    private static void getChromeDriver() {
        ChromeOptions options = new ChromeOptions();

//            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36";
//            options.addArguments(String.format("user-agent=%s", userAgent));

        options.addArguments("start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-blink-features=AutomationControlled");

        if( isUsingChromeProfile() ) {
            options.addArguments("user-data-dir=C:/Users/Jonathan/Documents/Selenium");
            options.addArguments("profile-directory=Profile 1");
        }

        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

        driver = new ChromeDriver(options);
    }

    public static void closeDriver(){
        if(driver != null){
            System.out.println("SeleniumUtil.closeDriver");
            try{
                driver.quit();
                driver = null;
            }catch (Exception ex){
                ex.printStackTrace();
                System.exit(0);
            }
        }
    }

    public static boolean isUsingChromeProfile(){
        return usingChromeProfile;
    }
}
