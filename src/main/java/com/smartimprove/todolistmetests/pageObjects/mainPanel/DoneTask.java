package com.smartimprove.todolistmetests.pageObjects.mainPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.smartimprove.todolistmetests.pageObjects.BasePage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by inspiron on 02.08.2017.
 */
public class DoneTask extends BasePage {

    @FindBy(xpath = "//ul[@id='mydonetodos']/li")
    private ElementsCollection listOfDoneTasks;

    @FindBy(tagName = "input")
    private SelenideElement checkBox;

    public ElementsCollection getDoneList() {
        return listOfDoneTasks;
    }

    public SelenideElement getFilterDoneList(String task) {
        $(By.xpath("//ul[@id='mydonetodos']/li")).waitUntil(visible, 3000);
        ElementsCollection listDoneTask = listOfDoneTasks.filter(Condition.text(task));
        Assert.assertEquals(listDoneTask.size(), 1);
        return listDoneTask.first();
    }

    public void executeTask(SelenideElement se) {
        se.$(By.tagName(checkBox.getTagName())).click();

    }

    public boolean removeTask(String task) {
        $(By.xpath("//ul[@id='mydonetodos']/li")).waitUntil(visible, 3000);
        ElementsCollection listDoneTask = listOfDoneTasks.filter(Condition.text(task));
        Assert.assertEquals(listDoneTask.size(), 1);
        listDoneTask.first().$(By.tagName("input")).click();
        ElementsCollection listDeletedTask = listDoneTask.filter(Condition.text(task));
        return listDeletedTask.isEmpty();

    }

}
