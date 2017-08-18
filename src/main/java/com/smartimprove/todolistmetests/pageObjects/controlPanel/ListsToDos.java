package com.smartimprove.todolistmetests.pageObjects.controlPanel;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.smartimprove.todolistmetests.pageObjects.BasePage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by inspiron on 02.08.2017.
 */
public class ListsToDos extends BasePage {

    @FindBy(id = "addlist")
    private SelenideElement newList;

    @FindBy(id = "updatebox")
    private SelenideElement nameList;

    @FindBy(id = "mycategory_0")
    private SelenideElement defaultCategoryUl;

    @FindBy(id = "mycategories")
    private SelenideElement categoriesUl;

    @FindBy(xpath = "//h3/*[@id=\"adddivider\"]")
    private SelenideElement category;

    public void closeCategoryDropdown(String categoryName) {
        ElementsCollection selectedListInCategory = listsInCategory(categoryName);
        selectedListInCategory.first().hover().$(By.xpath("./../img")).click();
    }

    public void openCategoryDropdown(String categoryName) {
        ElementsCollection selectedListInCategory = listsInCategory(categoryName);
        selectedListInCategory.first().hover().$(By.xpath(".//../../../li[@class='hidecategory']/h5/img[1]")).click();
    }

    public SelenideElement createNewListInDefaultCategory(String listName) {
        newList.click();
        nameList.val(listName).pressEnter();
        ElementsCollection addedList = listInDefaultCategory(listName);
        return addedList.first();
    }

    public void createNewCategory(String nameCategory) {
        category.click();
        $(By.xpath(".//form/input")).val(nameCategory).pressEnter();
    }

    public boolean removeListFromCategory(String categoryName, String listName) {
        ElementsCollection elems = getListsFromCategoryByName(categoryName).filter(text(listName));
        Assert.assertTrue(elems.size() == 1);
        SelenideElement selectedList = elems.first();
        selectedList.hover().$(By.className("delete")).click();
        return selectedList.is(not(visible));
    }

    public boolean removeListFromDefaultCategory(String listName) {
        ElementsCollection listsOfDefaultCategory = getListsFromDefaultCategory().filter(text(listName));
        Assert.assertTrue(listsOfDefaultCategory.size() == 1);
        SelenideElement selectedList = listsOfDefaultCategory.first();
        selectedList.hover().$(By.className("delete")).click();
        return selectedList.is(not(visible));
    }

    public ElementsCollection getListsFromDefaultCategory() {
        return defaultCategoryUl.$$(By.tagName("li"));
    }

    public void switchToListInDefaultCategory(String listName) {
        ElementsCollection selectedListInDefaultCategory = listInDefaultCategory(listName);
        selectedListInDefaultCategory.first().$(By.xpath("./span")).click();
    }

    public void switchToListInCategory(String categoryName, String listName) {
        ElementsCollection selectedListInCategory = listsInCategory(categoryName);
        ElementsCollection switchedList = selectedListInCategory.first().$$(By.xpath("./../../ul/li")).filter(text(listName));
        Assert.assertTrue(switchedList.size() == 1);
        switchedList.first().$(By.xpath("./span")).click();
    }

    public void addListToCategory(String listName, String categoryName) {
        ElementsCollection selectedListInDefaultCategory = listInDefaultCategory(listName);
        ElementsCollection selectedListInCategory = listsInCategory(categoryName);
        SelenideElement container = selectedListInCategory.first().$(By.xpath("./../../ul"));
        selectedListInDefaultCategory.first().dragAndDropTo(container);
    }

    public ElementsCollection getListsFromCategoryByName(String categoryName) {
        ElementsCollection listsOfCategory = $$(By.xpath("//h5/span")).filter(text(categoryName));
        Assert.assertTrue(listsOfCategory.size() == 1);
        return listsOfCategory.first().$$(By.xpath("./../../ul/li"));
    }

    private ElementsCollection listInDefaultCategory (String listName){
        ElementsCollection selectedListInDefaultCategory = defaultCategoryUl.$$(By.xpath("./ul/li")).filter(text(listName));
        Assert.assertTrue(selectedListInDefaultCategory.size() == 1);
        return selectedListInDefaultCategory;
    }
    private ElementsCollection listsInCategory(String categoryName){
        ElementsCollection selectedListInCategory = categoriesUl.$$(By.xpath(".//h5/span")).filter(text(categoryName));
        Assert.assertTrue(selectedListInCategory.size() == 1);
        return selectedListInCategory;
    }
}
