package finale;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import finale.webpages.LoginPage;
import finale.webpages.posts.PostsAddPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAddPosts {

	private static WebDriver driver;
	private static WebDriverWait wait;

	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin";

	private static LoginPage loginPage;
	private static PostsAddPage postsAddPage;

	private String NEWTITLE_1 = "Post 1";
	private String NEWTITLE_2 = "123456789101112123562";
	private String NEWTITLE_3 = "Post new post new post";
	private String NEWTITLE_4 ="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.";
	private String NEWDES_1 = "Description";
	private String NEWDES_3 = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.";
	private String NEWDES_4 = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.";
	private String NEWCONTENT = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.";

	@BeforeClass
	public static void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofMillis(2000));

		loginPage = new LoginPage(driver, wait);
		postsAddPage = new PostsAddPage(driver, wait);

		loginPage.open();
		loginPage.loginSuccess();

		// System.out.println("ULOGOVANA SAM");

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void SetUp() throws Exception {
		postsAddPage.open();
	}

	@After
	public void tearDown() throws Exception {
	}



	@Test
	public void tc05_testLinkPostAddPost() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[.='Posts']")));
		checkMenuLink("Posts list", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
		checkMenuLink("Add Post", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
		postsAddPage.open();
	}

	@Test
	public void tc07_testAddPostEmptyfield() {

		postsAddPage.inputStrigInName("");
		postsAddPage.inputStrigInDescription("");

		postsAddPage.clickSave();

		assertEquals(postsAddPage.getTitleErrorMessage(), "This field is required.", "Bad error string on Title input");
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "This field is required.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getTagErrorMessage(), "This field is required.", "Bad error string on Tag input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc08_testAddPostInvalidTitle() {
		
		postsAddPage.inputStrigInName(NEWTITLE_1);
		postsAddPage.inputStrigInDescription("");
		postsAddPage.clickSave();
		
		assertEquals(postsAddPage.getTitleErrorMessage(), "Please enter at least 20 characters.", "Bad error string on Title input");
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "This field is required.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getTagErrorMessage(), "This field is required.", "Bad error string on Tag input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc09_testAddPostwithNumberTitle() {

		postsAddPage.inputStrigInName(NEWTITLE_2);
		postsAddPage.inputStrigInDescription("");
		postsAddPage.clickSave();

		assertEquals(postsAddPage.getDescriptionErrorMessage(), "This field is required.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getTagErrorMessage(), "This field is required.", "Bad error string on Tag input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc10_testAddPostTitleDescription() {
		postsAddPage.inputStrigInName(NEWTITLE_1);
		postsAddPage.inputStrigInDescription(NEWDES_1);
	
		postsAddPage.clickSave();

		assertEquals(postsAddPage.getTitleErrorMessage(), "Please enter at least 20 characters.", "Bad error string on Title input");
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "Please enter at least 50 characters.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getTagErrorMessage(), "This field is required.", "Bad error string on Tag input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc11_testAddPostTitleDescriptionTag() {
		
		postsAddPage.inputStrigInName(NEWTITLE_1);
		postsAddPage.inputStrigInDescription(NEWDES_1);
		postsAddPage.SelectCheckbox();
		
		postsAddPage.clickSave();
		
		assertEquals(postsAddPage.getTitleErrorMessage(), "Please enter at least 20 characters.", "Bad error string on Title input");
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "Please enter at least 50 characters.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc12_testAddPostTitleDescriptionTag() {

		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName(NEWTITLE_3);
		postsAddPage.inputStrigInDescription(NEWDES_1);
		postsAddPage.SelectCheckbox();
		postsAddPage.writenContent("");
		
		postsAddPage.clickSave();
	
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "Please enter at least 50 characters.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc13_testValidTitleTagEmptyDescription() {

		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName(NEWTITLE_3);
		postsAddPage.inputStrigInDescription("");
		postsAddPage.SelectCheckbox();
		postsAddPage.writenContent("");
		
		postsAddPage.clickSave();
		
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "This field is required.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc14_testInvalidTitleDescriptionEmptyTag() {
		
		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName(NEWTITLE_4);
		postsAddPage.inputStrigInDescription(NEWDES_4);
		postsAddPage.writenContent("");
		
		postsAddPage.clickSave();
		
		assertEquals(postsAddPage.getTitleErrorMessage(), "Please enter no more than 255 characters.", "Bad error string on Title input");
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "Please enter no more than 500 characters.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getTagErrorMessage(), "This field is required.", "Bad error string on Tag input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc15_testInvalidTitleDescriptionValidTag() {
		
		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName(NEWTITLE_4);
		postsAddPage.inputStrigInDescription(NEWDES_4);
		postsAddPage.writenContent("");
		
		postsAddPage.clickSave();
		
		assertEquals(postsAddPage.getTitleErrorMessage(), "Please enter no more than 255 characters.", "Bad error string on Title input");
		assertEquals(postsAddPage.getDescriptionErrorMessage(), "Please enter no more than 500 characters.",
				"Bad error string on Description input");
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc16_testValidTitleDescriptionTagEmptyContent() {

		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName(NEWTITLE_3);
		postsAddPage.inputStrigInDescription(NEWDES_3);
		postsAddPage.SelectCheckbox();
		postsAddPage.writenContent("");
		
		postsAddPage.clickSave();
		
		assertEquals(postsAddPage.getConErrorMessage(), "The content field is required.",
				"Bad error string on Content input");
	}

	@Test
	public void tc17_testValidTitleDescriptionTag() {
		
		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName(NEWTITLE_3);
		postsAddPage.inputStrigInDescription(NEWDES_3);
		postsAddPage.SelectCheckbox();
		postsAddPage.writenContent(NEWCONTENT);

		postsAddPage.clickCancel();
		
		assertEquals( PAGE_URL + "/posts", driver.getCurrentUrl());	
		assertEquals(postsAddPage.isTitleInList(NEWTITLE_3), false);
	}
		
	@Test
	public void tc18_testValidTitleDescriptionTag() {

		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName(NEWTITLE_3);
		postsAddPage.inputStrigInDescription(NEWDES_3);
		postsAddPage.SelectCheckbox();
		postsAddPage.writenContent(NEWCONTENT);
		
		postsAddPage.clickSave();

		assertEquals(PAGE_URL + "/posts", driver.getCurrentUrl());
		
	}
	

	// CheckMenu
	public void checkMenuLink(String title, String url) {
		WebElement weMenu = driver.findElement(By.xpath("//p[text()='" + title + "']//ancestor::li[2]"));

		if (!weMenu.getAttribute("class").toString().equalsIgnoreCase("nav-item has-treeview menu-open")) {
			weMenu.click();
		}
		WebElement weLink = driver.findElement(By.xpath("//p[text()='" + title + "']"));
		wait.until(ExpectedConditions.visibilityOf(weLink));
		weLink.click();
	}

}
