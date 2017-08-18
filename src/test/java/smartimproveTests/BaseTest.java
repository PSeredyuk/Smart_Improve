package smartimproveTests;

/**
 * Created by inspiron on 01.08.2017.
 */

import com.codeborne.selenide.WebDriverRunner;
import com.smartimprove.todolistmetests.pageObjects.mainPanel.DoneTask;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.smartimprove.todolistmetests.pageObjects.controlPanel.ListsToDos;
import com.smartimprove.todolistmetests.pageObjects.mainPanel.ToDoTask;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;


public class BaseTest extends SelenideService {

    private final String TEST_TODO = "Test_task";
    private final String URL = "http://todolistme.net/";
    private final String ADDED_LIST = "Test list";

    @BeforeMethod
    public void init() {
        setup();
        open(URL);
    }

    @Test
    public void testAddTaskTodoList() {
        ToDoTask toDoTask = page(ToDoTask.class);
        toDoTask.createNewTodo(TEST_TODO);
        toDoTask.getToDoList();
        Assert.assertTrue(toDoTask.getFilterToDoList(TEST_TODO).exists());
    }

    @Test
    public void testTaskToDoInList() {
        ToDoTask toDoTask = page(ToDoTask.class);
        toDoTask.createNewTodo(TEST_TODO);
        DoneTask doneTask = page(DoneTask.class);
        doneTask.executeTask(toDoTask.getFilterToDoList(TEST_TODO));
        Assert.assertEquals(TEST_TODO, doneTask.getFilterDoneList(TEST_TODO).getText());
    }

    @Test
    public void testRemoveTask() {
        ToDoTask toDoTask = page(ToDoTask.class);
        toDoTask.createNewTodo(TEST_TODO);
        DoneTask doneTask = page(DoneTask.class);
        doneTask.executeTask(toDoTask.getFilterToDoList(TEST_TODO));
        Assert.assertEquals(doneTask.removeTask(TEST_TODO), true);
    }

    @Test
    public void testCreateNewList() {
        ListsToDos newListsToDos = page(ListsToDos.class);
        Assert.assertTrue(newListsToDos.createNewListInDefaultCategory(ADDED_LIST).exists());
    }

    @Test
    public void testRemoveListFromDefaultCategory() {
        ListsToDos newListsToDos = page(ListsToDos.class);
        newListsToDos.createNewListInDefaultCategory(ADDED_LIST);
        Assert.assertTrue(newListsToDos.removeListFromDefaultCategory(ADDED_LIST));
    }

    @Test
    public void testCreateTaskInNewList() {
        ListsToDos newListsToDos = page(ListsToDos.class);
        newListsToDos.createNewListInDefaultCategory(ADDED_LIST);
        ToDoTask toDoTask = page(ToDoTask.class);
        toDoTask.createNewTodo(TEST_TODO);
        Assert.assertTrue(toDoTask.getFilterToDoList(TEST_TODO).exists());
    }

    @Test
    public void testUserWorkFlow() {
        ListsToDos newListsToDos = page(ListsToDos.class);
        ToDoTask newToDoTask = page(ToDoTask.class);
        DoneTask newDoneTask = page(DoneTask.class);
        newListsToDos.createNewListInDefaultCategory(ADDED_LIST);
        newListsToDos.createNewCategory("Task1");
        newListsToDos.addListToCategory(ADDED_LIST, "Task1");
        newListsToDos.switchToListInCategory("Task1", ADDED_LIST);
        newToDoTask.createNewTodo(TEST_TODO);
        newDoneTask.executeTask(newToDoTask.getFilterToDoList(TEST_TODO));
        Assert.assertEquals(TEST_TODO, newDoneTask.getFilterDoneList(TEST_TODO).getText());

    }

    @Test
    public void testCloseOpenCategory() {
        ListsToDos newListsToDos = page(ListsToDos.class);
        newListsToDos.createNewListInDefaultCategory(ADDED_LIST);
        newListsToDos.createNewCategory("Task1");
        newListsToDos.addListToCategory(ADDED_LIST, "Task1");
        newListsToDos.closeCategoryDropdown("Task1");
        newListsToDos.openCategoryDropdown("Task1");
    }


    @AfterMethod
    public void dispose() {
        WebDriverRunner.getWebDriver().quit();
    }
}
