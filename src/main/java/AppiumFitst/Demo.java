package AppiumFitst;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyMetastate;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Demo {

    private AppiumDriver driver;
    private boolean isInstall = false;


//    public void swipeToLeft(AndroidDriver driver1, int duringTime) {
//        int width = driver1.manage().window().getSize().width;
//        int height = driver1.manage().window().getSize().height;
//        driver1.swipe();
//        // wait for page loading
//    }


    @BeforeTest
    public void setUp() throws Exception {
//        避免多次运行报错终止
        Runtime rt = Runtime.getRuntime();
        rt.exec("adb uninstall io.appium.android.ime");
        System.out.println("success");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        //配置appnium
        capabilities.setCapability("deviceName", "android Emulator");
        capabilities.setCapability("uuid","5e25d38e");
        capabilities.setCapability("platformVersion", " 8.0.0");
        capabilities.setCapability("platformName", "Android");

        //配置测试apk
        capabilities.setCapability("appActivity", "com.sankuai.pms.WelcomeActivity");
        capabilities.setCapability("appPackage", "com.sankuai.pms");
//        capabilities.setCapability("appActivity", ".Calculator");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true); //支持中文输入，必须两条都配置
        capabilities.setCapability("sessionOverride", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        if(isInstall){
            File classpathRoot = new File(System.getProperty("user.dir"));
            File appDir = new File(classpathRoot,"apps");
            File app = new File(appDir,"pms-release-10570.apk");
            capabilities.setCapability("app",app.getAbsolutePath());
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void Login() throws InterruptedException {
        //进行登录
        driver.findElementById("com.sankuai.pms:id/edit_username").sendKeys("18221293942");
        Thread.sleep(2000);
        driver.findElementById("com.sankuai.pms:id/edit_password").sendKeys("123456");
        Thread.sleep(2000);
        WebElement loginbtn = driver.findElementById("com.sankuai.pms:id/btn_login");
        loginbtn.click();
        Thread.sleep(10000);


    }


    @Test
    public void RunApp() throws InterruptedException {
        //点击首页下的订单
        WebElement webElementOrder = driver.findElementByXPath("//android.widget.RelativeLayout[7]/android.widget.LinearLayout/android.widget.TextView[contains(@index,1)]");
        Assert.assertEquals(webElementOrder.getText(),"订单");

        //点击跳转到我的页
        WebElement webElement1 = driver.findElementById("com.sankuai.pms:id/toolbar_back_image");
        webElement1.click();
        Thread.sleep(2000);

        WebElement logoutBtn = driver.findElementById("com.sankuai.pms:id/btn_exit");
        logoutBtn.click();
        Thread.sleep(3000);
        WebElement logoutSure = driver.findElementById("com.sankuai.pms:id/sure");
        logoutSure.click();
        WebElement loginbtn = driver.findElementById("com.sankuai.pms:id/btn_login");
        Assert.assertEquals(loginbtn.getText(),"登录");


    }


}

