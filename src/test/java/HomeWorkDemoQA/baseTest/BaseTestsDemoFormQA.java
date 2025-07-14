package HomeWorkDemoQA.baseTest;

import HomeWorkDemoQA.helpers.Attach;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static HomeWorkDemoQA.TestData.BASE_URL;
import static HomeWorkDemoQA.TestData.BROWSER;
import static HomeWorkDemoQA.TestData.PAGE_LOAD_TIMEOUT;
import static HomeWorkDemoQA.TestData.TIMEOUT;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

public class BaseTestsDemoFormQA {

    private static final String OPEN_AUTOMATION_PRACTICE_FORM_RELATIVE_URL = "/automation-practice-form";
    private static final SelenideElement PRACTICE_FORM_TEXT_SELECTOR = $(".text-center");
    private static final String PRACTICE_FORM_TEXT = "Practice Form";

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        Configuration.baseUrl = BASE_URL;
        Configuration.browser = BROWSER;
        Configuration.pageLoadTimeout = PAGE_LOAD_TIMEOUT;
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = TIMEOUT;
        Configuration.remote = "https://user1:1234@" +
                System.getProperty("selenoid", "selenoid.autotests.cloud") + "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureListener" , new AllureSelenide());
    }

    @Step("Precondition для тестов на форму DemoQA ")
    @BeforeEach
    public void setUp() throws Exception {
        open(OPEN_AUTOMATION_PRACTICE_FORM_RELATIVE_URL);
        PRACTICE_FORM_TEXT_SELECTOR.shouldHave(text(PRACTICE_FORM_TEXT));
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
    }

    @AfterEach
    public void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.addVideo();
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Selenide.closeWebDriver();
    }
}
