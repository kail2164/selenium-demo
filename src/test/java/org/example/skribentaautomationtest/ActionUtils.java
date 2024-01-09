package org.example.skribentaautomationtest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class ActionUtils {
    static Actions actions = new Actions(webdriver().object());

    public static void click(String selector) {
        sleep(100);
        $(selector).click();
        sleep(100);
    }

    public static void click(SelenideElement elem) {
        sleep(100);
        elem.click();
        sleep(100);
    }

    public static void deleteLine(SelenideElement elem){
        selectWholeLine(elem);
        sendKey(Keys.DELETE);
    }

    public static void doubleClick(String selector){
        sleep(100);
        $(selector).doubleClick();
        sleep(100);
    }

    public static void makeBold(){
        sendShortCut(Keys.CONTROL, "b");
    }

    public static void rightClick(String selector){
        sleep(100);
        $(selector).contextClick();
        sleep(100);
    }

    public static void rightClick(){
        sleep(100);
        actions.contextClick().build().perform();
        sleep(500);
    }
    public static void selectCharactersInlineByMouseMove(SelenideElement elem, double offsetX, double offsetY, double distance){
        Rectangle rect = elem.getRect();
        double elemHeight = rect.height;

        double yOffset = rect.y + elemHeight/2 + offsetY;
        double xOffset = rect.x + offsetX;

        sleep(200);
        actions.moveToLocation((int)xOffset, (int)yOffset).click().keyDown(Keys.SHIFT).perform();
        sleep(200);
        actions.moveByOffset((int) Math.round(distance), 0).click().keyUp(Keys.SHIFT).build().perform();
        sleep(200);
    }

    public static double getStringWidth(String str, String font, String fontSize, String fontStyle){
        String script = "var element = document.createElement('canvas');"
                + "var context = element.getContext('2d');"
                + "context.font = '" + fontStyle + " " + fontSize + " " + font +"';"
                + "var metrics = context.measureText('"+str+"');"
                + "return metrics.width;";
        return executeJavaScript(script);
    }

    public static void selectText(SelenideElement elem, String textToSelect, Map<String, String> specialFormatTexts){
        String textContent = elem.getText().replaceAll("\n","");
        String fontSize = elem.getCssValue("font-size");
        String font = elem.getCssValue("font-family");
        String fontStyle = "500";
        int startIndex = textContent.indexOf(textToSelect);
        double distance = getStringWidth(textToSelect, font, fontSize, fontStyle);
        String textBefore = textContent.substring(0, startIndex);
        double offsetX = getStringWidth(textBefore, font, fontSize, fontStyle);
        for(String text : specialFormatTexts.keySet()){
            if(textToSelect.contains(text)){
                String format = specialFormatTexts.get(text);
                if(!format.contains("bold")){
                    format += " 500";
                }
                double tempDistance = getStringWidth(text, font, fontSize, format);
                double originDistance = getStringWidth(text, font, fontSize, fontStyle);
                distance = distance - originDistance + tempDistance;
            }
        }
        selectCharactersInlineByMouseMove(elem, offsetX, 0, distance);
    }

    public static void selectText(SelenideElement elem, String textToSelect){
        selectText(elem, textToSelect, new HashMap<>());
    }

    public static void selectWholeLine(SelenideElement elem){
        Rectangle rect = elem.getRect();
        actions.moveToLocation(rect.x , rect.y)
                .click()
                .build()
                .perform();
        sleep(100);
        sendShortCut(Keys.SHIFT,Keys.END);
    }

    public static void sendKey(Keys key){
        actions.sendKeys(key).build().perform();
    }

    public static void sendShortCut(Keys keyOne, Keys keyTwo){
        sleep(200);
        actions.keyDown(keyOne)
                .sendKeys(keyTwo)
                .keyUp(keyOne)
                .build()
                .perform();
        sleep(100);
    }
    public static void sendShortCut(Keys keyOne, CharSequence keyTwo){
        sleep(200);
        actions.keyDown(keyOne)
                .sendKeys(keyTwo)
                .keyUp(keyOne)
                .build()
                .perform();
        sleep(100);
    }
    public static void sendShortCutMultipleTimes(Keys keyOne, Keys keyTwo, int times){
        sleep(100);
        actions.keyDown(keyOne);
        for(int i = 0; i < times; i++){
            actions.sendKeys(keyTwo);
        }
        actions.keyUp(keyOne)
                .build()
                .perform();
        sleep(100);
    }

    public static void setValue(String selector, String value, boolean isNormalInputField) {
        SelenideElement elem = $(selector);
        elem.click();
        if (isNormalInputField) {
            $(selector).setValue(value);
            sleep(100);
        } else {
           setValue(elem, value);
        }
    }

    public static void setValue(SelenideElement elem, String value){
        actions.sendKeys(elem, value).build().perform();
        sleep(100);
    }

    public static void setValue(String value){
        actions.sendKeys(value).build().perform();
    }

}
