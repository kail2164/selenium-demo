package org.example.skibentaautomationtest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// page_url = https://www.jetbrains.com/
public class SkribentaPage {

  //This class is for declaring all the elements on the page that we want to interact with

  //Here are the elements that are used for login
  public SelenideElement inputUsername = $("#username");
  public SelenideElement inputPassword = $("#password");
  public SelenideElement inputSubmit = $("input[name='btn-submit']");


  //These are the basic buttons of Skribenta
  public SelenideElement overviewAspect = $(".AspectNavigate");

  public SelenideElement workspaceAspect = $("div[class*='AspectExplore']");
  public SelenideElement treeFrame = $("#TreeFrame");
  public SelenideElement contentFrameButton = $("div[class*='FrameButtonContent']");

  //Here are my 2 resources that I will interact with
  public SelenideElement folder001 = $x("//span[text()='001']");
  public SelenideElement fileSelenium = $x("//span[text()='1a.xml']");

  public SelenideElement fileSeleniumPenIcon = $x("/html/body/div[5]/div[5]/div[2]/div/div/div[2]/div[9]/div[2]/div[3]/div/div[2]");

  //Here are the buttons in the Floating Toolbar
  public SelenideElement propsButton = $x("//div[@class='Action FileProps Expandable']");

  public SelenideElement generalButton = $x("//div[contains(@title, 'General')]");














}
