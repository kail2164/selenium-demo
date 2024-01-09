package org.example.skribentaautomationtest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class GeneralFunctions {

    public static void checkFloatingToolbarIsClosed(){
        Selenide.$("#Pinned").shouldBe(Condition.empty, Duration.ofSeconds(2));
    }

    public static boolean checkIfSeleniumTestFolderExists(){
        return checkIfElementExistsByText("#TreeFrame *", "SeleniumTest");
    }

    public static boolean checkIfTestCaseIsExists(String name){
        return checkIfElementExistsByText("#TreeFrame *", name);
    }

    public static boolean checkIfElementExistsByText(String frame, String textValue){
        return checkIfElementExists(findElementByText(frame, textValue));
    }

    public static boolean checkIfElementExists(SelenideElement elem){
        return elem != null && elem.exists();
    }

    public static SelenideElement findElementByText(String frame, String textValue){
        try{
            return Selenide.$$(frame).findBy(Condition.text(textValue));
        } catch (Exception e){
            return null;
        }
    }

    public static void createNewResource(String name, boolean isFile){
        openNewResourcePanel();
        ActionUtils.click("div.Href.Popup.ActionHref > div > div > div > span > div.Action.FolderNew" + (isFile ? "File" : "Folder") +".Expandable");
        ActionUtils.setValue("#EditCursor > span.I.FileName", name, false);
        ActionUtils.click("div.TableControl.PanelTable.table.Expanded > table > tbody > tr.TableRowControl.row1.tr.Expanded > td.col1 > div > button");
        checkFloatingToolbarIsClosed();

    }

    public static void createNewFile(String name){
        createNewResource(name, true);
    }

    public static void createNewFolder(String name){
       createNewResource(name, false);
    }

    public static void createNewFolder(String name, String folderName) {
        if(folderName == null){
            openWorkspaceToolbar();
            createNewFolder(name);
            getWorkspaceInTreeFrame().$(" div.Name").doubleClick();  //refresh
        } else {
            openFolderToolbar(folderName);
            createNewFolder(name);
            findResource(folderName).doubleClick();            //refresh
        }
        checkFloatingToolbarIsClosed();
        Selenide.sleep(200);
    }

    public static void createSeleniumTestFolder() {
        createNewFolder("SeleniumTest", null);
    }

    public static void createTestCase(String name, String folderName){
        openFolderToolbar(folderName);
        createNewFile(name);
        Selenide.sleep(200);
        checkFloatingToolbarIsClosed();
        findResource(folderName).doubleClick();
        Selenide.sleep(200);
    }

    public static SelenideElement getWorkspaceInTreeFrame(){
        return Selenide.$("div.ViewControl > div.CoProps > div.Compression");
    }

    public static void login() {
        ActionUtils.setValue("#username", "hieu.nguyenkhac@orientsoftware.com", true);
        ActionUtils.setValue("#password", "12345678@Ab", true);
        ActionUtils.click("input[name='btn-submit']");
        Selenide.$(".AspectNavigate").shouldBe(visible, Duration.ofSeconds(20));
        Assertions.assertEquals("http://localhost:8080/S5/html/object.html", WebDriverRunner.url());
    }

    public static void openContentFrame(){
        ActionUtils.click("#FrameToolbar > div.Action.FrameButtonContent.FrameButton");
    }

    public static void openFolder(String name){
        SelenideElement folder = findResource(name);
        openFolder(folder);
    }

    public static SelenideElement findResource(String name){
        return findElementByText("div.KeymapCoPropsName", name);
    }

    public static void openFolder(SelenideElement element){
        if(element == null){
            return;
        }
        element.scrollIntoView(true);
        element.click();
        Selenide.sleep(200);
    }

    public static void openFolderToolbar(String name){
        SelenideElement folder = findResource(name);
        if(folder == null || !folder.exists()){
            return;
        }
        folder.scrollIntoView(true);
        openToolbar(folder.parent().parent());
    }

    public static void openNewResourcePanel() {
        ActionUtils.click("#Pinned > div > table > tbody > tr:nth-child(2) > td > div.Action.FolderNew.Expandable.EditMode");
    }

    public static void openOverview() {
        ActionUtils.click("div.Action.AspectNavigate.Navigate");
    }

    public static void openToolbar(SelenideElement compressionElem){
        compressionElem.$(".KeymapIcon").click();
    }

    public static void openWorkspace() {
        ActionUtils.click("div.Action.AspectExplore.Explore");
    }

    public static void openWorkspaceToolbar() {
        openToolbar(getWorkspaceInTreeFrame());
    }

    public static void prepareTestResources(){
        String folderName = "SeleniumTest";
        openWorkspace();
        if(!checkIfSeleniumTestFolderExists()){
            createSeleniumTestFolder();
        }
        SelenideElement folder = findResource(folderName);
        folder = folder.parent().parent(); //change to .Compression element
        //Check if test folder is empty or not by the + icon
        if(!folder.$(".PlusMinus.HasExp").exists()){
            createTestCase("TestChangeCase", folderName);
        }
    }

    public static void showVersions() {
        toggleViewBoxes(2);
    }

    public static void toggleViewBoxes(int index){
        //index starts from 1
        index++;
        clickViewOptions();
        ActionUtils.click("div.DropDownPanel > div:nth-child(" + index + ") > span.Checkbox > input[type=checkbox]");
    }

    public static void unlockFile(){
        ActionUtils.rightClick(".WrapperControl.body.part");
        ActionUtils.click("#body > div.DropDownPanel > div.Option.File.Expandable > span.Image.OptionImage");
        ActionUtils.click("div.Action.FileMore.Expandable > div");
        ActionUtils.click("div.Href.Popup.ActionHref > div > div > div > span > div.Action.FileUnlock");
        ActionUtils.click("div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button.ui-button-ok.ui-button.ui-corner-all.ui-widget.ui-state-active");
    }

    public static void clickViewOptions() {
        ActionUtils.click("div.Action.ViewOptions");
        Selenide.sleep(200);
    }



}
