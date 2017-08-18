package com.smartimprove.todolistmetests.pageObjects.mainPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.smartimprove.todolistmetests.pageObjects.BasePage;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;

/**
 * Created by inspiron on 02.08.2017.
 */
public class ToDoTask extends BasePage {

    @FindBy(id = "newtodo")
    private SelenideElement inputNewTodoName;

    @FindBy(xpath = "//ul[@id='mytodos']/li")
    private ElementsCollection  listOfToDoTasks;


    public void createNewTodo(String todoName) {

        inputNewTodoName.val(todoName).pressEnter();
    }

    public  ElementsCollection getToDoList() {
        return listOfToDoTasks;
    }

    public SelenideElement getFilterToDoList(String task ) {
        ElementsCollection listTask = listOfToDoTasks.filter(Condition.text(task));
        Assert.assertEquals(listTask.size(), 1);
        return listTask.first();
    }



}
