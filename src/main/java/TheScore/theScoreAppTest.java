package TheScore;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class theScoreAppTest {
    public AndroidDriver driver;

    @BeforeTest
    public void setup() throws MalformedURLException {
        //setup desired capabilities
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel2");

        //set up the absolute path of the score app
        File f = new File("src/Utils");
        File fs = new File(f, "theScore.apk");
        String appPath = fs.getAbsolutePath();
        options.setApp(appPath);

        //set up appium driver
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver(url, options);
    }

    @AfterTest
    public void tearDown() {
        //quit the driver
        driver.quit();
    }

    @Test
    public void gettingStarted() {
        //wait for application to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //clicking the get started button
        WebElement getStartedButton = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        getStartedButton.click();

        //clicking the continue button
        WebElement continueButton = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        continueButton.click();

        //clicking the location disallow button
        WebElement locationDisallowButton = driver.findElement(By.id("com.fivemobile.thescore:id/btn_disallow"));
        locationDisallowButton.click();

        //choosing your favourite team and selecting it
        WebElement initialSearchBar= driver.findElement(By.id("com.fivemobile.thescore:id/search_bar_placeholder"));
        initialSearchBar.click();

        //using this approach because sendKeys was not working for me
        driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.J)));
        driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.A)));
        driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.Y)));
        driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.S)));

        driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget." +
                "LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android." +
                "view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget." +
                "FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.view.ViewGroup/android.view.ViewGroup/androidx." +
                "recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView")).click();

        //clicking the continue button
        continueButton.click();

        //clicking the done button
        WebElement doneButton = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        doneButton.click();

        //clicking the cancel button on the pop up
        WebElement cancelButton = driver.findElement(By.id("com.fivemobile.thescore:id/dismiss_modal"));
        cancelButton.click();
    }

        @Test
        public void openATeam() {
            //Test 1: open a league team and verify the expected page opens correctly
            WebElement searchBar = driver.findElement(By.id("com.fivemobile.thescore:id/search_bar_text_view"));
            searchBar.click();

            //using this approach because sendKeys was not working for me
            driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.J)));
            driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.A)));
            driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.Y)));
            driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent((AndroidKey.S)));

            driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget" +
                    ".LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget" +
                    ".FrameLayout/android.widget.ScrollView/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.view.ViewGroup/android.view" +
                    ".ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView")).click();

            String actualTeamName = driver.findElement(By.id("com.fivemobile.thescore:id/team_name")).getText();
            String expectedTeamName = "Toronto Blue Jays";

            Assert.assertEquals(actualTeamName, expectedTeamName);
        }

        @Test
        public void openASubTab() {
        //Test 2: open a syb tab, verify you are on the correct tab and verify back navigation returns to the previous screen
        WebElement teamStats = driver.findElement(AppiumBy.accessibilityId("Team Stats"));
        teamStats.click();

        String actualStatsField = driver.findElement(By.id("com.fivemobile.thescore:id/header_text")).getText();
        String expectedStatsField = "STATS";
        Assert.assertEquals(actualStatsField, expectedStatsField);

        String actualTeamNameStats = driver.findElement(By.id("com.fivemobile.thescore:id/team_name")).getText();
        String expectedTeamNameStats = "Toronto Blue Jays";
        Assert.assertEquals(actualTeamNameStats, expectedTeamNameStats);

        WebElement backButton = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
        backButton.click();

        String allField= driver.findElement(By.xpath("//android.widget.LinearLayout[@content-desc=\"All\"]/android.widget.TextView")).getText();
        Assert.assertEquals(allField, "ALL");
    }
}
