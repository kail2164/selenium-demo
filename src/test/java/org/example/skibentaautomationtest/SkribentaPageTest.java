package org.example.skibentaautomationtest;
import com.codeborne.selenide.*;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.*;

public class SkribentaPageTest {
    SkribentaPage skribentaPage = new SkribentaPage();

    //This will run once for all the test
@BeforeAll    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    //This will run before each test
@BeforeEach    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("http://localhost:8080/S5/do");
    }

    @Test
    public void login() {
    //This is the test for login
        skribentaPage.inputUsername.setValue("hieu.nguyenkhac@orientsoftware.com");
        skribentaPage.inputPassword.setValue("12345678@Ab");
        skribentaPage.inputSubmit.click();

        //Waiting for the page to load
        skribentaPage.overviewAspect.shouldBe(visible, Duration.ofSeconds(20));
        assertEquals("http://localhost:8080/S5/html/object.html", WebDriverRunner.url());

        //Open workspace aspect
        skribentaPage.workspaceAspect.click();
        skribentaPage.treeFrame.shouldBe(visible);
        sleep(1000);

        //looking for the folder: 001
        skribentaPage.folder001.shouldBe(exist);
        skribentaPage.folder001.scrollIntoView(true);
        sleep(1000);
        skribentaPage.folder001.click();
        sleep(1000);

        //looking for the file: 1a.xml
        skribentaPage.fileSelenium.shouldBe(exist, Duration.ofSeconds(5));
        skribentaPage.fileSelenium.scrollIntoView(true);
        //open floating toolbar
        skribentaPage.fileSeleniumPenIcon.click();
        sleep(1000);
        //click on "Props" button
        skribentaPage.propsButton.click();
        sleep(1000);
        //click on "General" button
        skribentaPage.generalButton.click();
        sleep(10000);
    }

}
