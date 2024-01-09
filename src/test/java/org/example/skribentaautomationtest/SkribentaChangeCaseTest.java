package org.example.skribentaautomationtest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class SkribentaChangeCaseTest {

    //This will run once for all the test
    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1600x900";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    //This will run before each test
    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("http://localhost:8080/S5/do");
    }

    public void changeCaseByContextMenu(){
        ActionUtils.rightClick(".Selection");
        sleep(200);
        ActionUtils.click("#body > div.FloatPanel.Edit > div > span > div.Action.ChangeCase");
        sleep(200);
    }

    public void changeCaseByShortcut(){
        ActionUtils.sendShortCut(Keys.SHIFT, Keys.F3);
        sleep(200);
    }

    private void firstTest(SelenideElement firstParagraph){
        firstParagraph.click();
        //first test
        ActionUtils.setValue(firstParagraph, "Hello world");
        sleep(200);
        SelenideElement inlineBlock = firstParagraph.$(".IB");
        //Test change case by using context menu
        ActionUtils.selectText(inlineBlock, "H");
        changeCaseByContextMenu();
        Assertions.assertEquals("hello world", inlineBlock.getText());
        //Test change case by using SHIFT + F3
        ActionUtils.selectText(inlineBlock, "h");
        changeCaseByShortcut();
        Assertions.assertEquals("Hello world", inlineBlock.getText());
        ActionUtils.deleteLine(inlineBlock);
    }

    private void secondTest(SelenideElement firstParagraph){
        firstParagraph.click();
        ActionUtils.setValue(firstParagraph, "Hello bold world");
        sleep(200);
        SelenideElement inlineBlock = firstParagraph.find("span.IB");

        ActionUtils.selectText(inlineBlock, "bold");
        ActionUtils.makeBold();
        sleep(500);
        Map<String, String> specialFormatTexts = new HashMap<>();
        specialFormatTexts.put("bold", "bold");
        ActionUtils.selectText(inlineBlock, "lo bold worl", specialFormatTexts);
        changeCaseByContextMenu();

        String textContent = inlineBlock.getText().replaceAll("\n", "");
        Assertions.assertEquals("HelLO BOLD WORLd", textContent);
        specialFormatTexts = new HashMap<>();
        specialFormatTexts.put("BOLD", "bold");
        ActionUtils.selectText(inlineBlock, "LO BOLD WORL", specialFormatTexts);
        changeCaseByShortcut();
        textContent = inlineBlock.getText().replaceAll("\n", "");
        Assertions.assertEquals("Hello bold world", textContent);
        ActionUtils.deleteLine(inlineBlock);
    }

    @Test
    public void testChangeCase() {
        String folderName = "SeleniumTest";
        //This is the test for login
        GeneralFunctions.login();
//        GeneralFunction.showVersions();
        GeneralFunctions.prepareTestResources();
        GeneralFunctions.openFolder(folderName);
        GeneralFunctions.openContentFrame();
        if(!GeneralFunctions.checkIfTestCaseIsExists("TestChangeCase")){
            GeneralFunctions.createTestCase("TestChangeCase", folderName);
        }

        SelenideElement testCase = GeneralFunctions.findResource("TestChangeCase.xml");
        testCase.click();

        SelenideElement firstParagraph = $("div.WrapperControl.body.part > div.Expansion > .paragraph");
        GeneralFunctions.unlockFile();
//        firstTest(firstParagraph);
        secondTest(firstParagraph);
        firstParagraph.click();


        ActionUtils.setValue("Test Success, will erase in 5 seconds");
        sleep(5000);
        ActionUtils.sendShortCutMultipleTimes(Keys.SHIFT, Keys.HOME, 1);
        sleep(1000);
        ActionUtils.sendKey(Keys.BACK_SPACE);
        sleep(1000);
        ActionUtils.sendShortCut(Keys.CONTROL, "s");



        sleep(10000);
    }

}
