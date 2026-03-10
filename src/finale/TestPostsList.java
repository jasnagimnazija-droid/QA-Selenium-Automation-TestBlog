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
import finale.webpages.posts.PostsListPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPostsList {

	private static WebDriver driver;
	private static WebDriverWait wait;

	private static LoginPage loginPage;

	private static PostsListPage postsListPage;
	private static PostsAddPage postsAddPage;

	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin";

	private String NEWPARTIALTITLE = "Post";
	private String NEWTITLE = "Post new post new post";
	private String NONEXISTENT_TITLE = "Nonexistent";
	private String NEWAUTHOR = "Polaznik Kursa";
	private String NEWCATEGORY = "Default Post Category NE BRISATI";
	private String NEWIMPORTANT = "Yes";
	private String NEWSTATUS = "enabled";
	private String NEWTAG = "Tag";
	private String NEWSEARCH = "post";

	@BeforeClass
	public static void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofMillis(20000));

		loginPage = new LoginPage(driver, wait);
		postsListPage = new PostsListPage(driver, wait);
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
		postsListPage.open();
		postsListPage.ListShow();
		
		if (!(postsListPage.isPostInTable(NEWTITLE))) 
			{
			postsListPage.clickOnAddPost();
			assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
			postsAddPage.SelectCategory();
			postsAddPage.inputStrigInName(NEWTITLE);
			postsAddPage.inputStrigInDescription("I need to desable this post I need to delete this post");
			postsAddPage.SelectCheckbox();
			postsAddPage.writenContent("I need to desable this post");
			postsAddPage.clickSave();
			}
	}

	@After
	public void tearDown() throws Exception {
		
	}

	

	@Test
	public void tc05_testMenu() {
		assertEquals(PAGE_URL + "/posts", driver.getCurrentUrl());
	}

	@Test
	public void tc06_testLinkPostAddPost() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[.='Posts']")));

		checkMenuLink("Posts list", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
		checkMenuLink("Add Post", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
	}

	@Test
	public void tc07_testSearchPostswithEmptyfield() {
		postsListPage.inputStrigInTitle("");
		assertEquals(postsListPage.isPostInTable(""), true);
	}

	@Test
	public void tc08_testSearchPostswithTitle() {
		postsListPage.inputStrigInTitle(NEWTITLE);
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
	}

	@Test
	public void tc09_testSearchPostswithPartialTitle() {
		postsListPage.inputStrigInTitle(NEWPARTIALTITLE);
		assertEquals(postsListPage.isPostInTable(NEWPARTIALTITLE), true);
	}

	@Test
	public void tc10_testSearchPostswithNonexistentTitle() {

		postsListPage.inputStrigInTitle(NONEXISTENT_TITLE);
		postsListPage.isPostInTable(NONEXISTENT_TITLE);
		assertEquals(postsListPage.getTableEmptyMessage(), "No matching records found");
	}

	@Test
	public void tc11_testSearchPostswithAuthor() {
		postsListPage.SelectPostImportant();
		assertEquals(postsListPage.isPostInTable(NEWAUTHOR), true);
	}

	@Test
	public void tc12_testSearchPostswithCategory() {
		postsListPage.SelectPostCategory();
		assertEquals(postsListPage.isPostInTable(NEWCATEGORY), true);
	}

	@Test
	public void tc13_testSearchPostswithImportant() {
		postsListPage.SelectPostImportant();
		assertEquals(postsListPage.isPostInTable(NEWIMPORTANT), true);
	}

	@Test
	public void tc14_testSearchPostswithStatus() {
		postsListPage.SelectPostStatus();
		assertEquals(postsListPage.isPostInTable(NEWSTATUS), true);
	}

	@Test
	public void tc15_testSearchPostswithTag() {
		postsListPage.SelectPostTag();
		assertEquals(postsListPage.isPostInTable(NEWTAG), true);
	}

	@Test
	public void tc16_testSearchPostswithCombinedfilters() {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("title")));
		
		postsListPage.inputStrigInTitle(NEWTITLE);
		postsListPage.SelectPostImportant();
		postsListPage.SelectPostCategory();
		postsListPage.SelectPostImportant();
		postsListPage.SelectPostStatus();
		postsListPage.SelectPostTag();
		assertEquals(postsListPage.isTableEmpty(), false);
	}

	@Test
	public void tc17_testSearchPostswithEmptySearch() {
		postsListPage.inputStrigInSearch("");
		assertEquals(postsListPage.isTableEmpty(), false);
		
	}

	@Test
	public void tc18_testSearchPostswithSearch() {
		postsListPage.inputStrigInSearch(NEWSEARCH);
		assertEquals(postsListPage.isTableEmpty(), false);
	}

	// *********************View POST************************//

	@Test
	public void tc19_testViewPost() {
		postsListPage.inputStrigInTitle(NEWTITLE);
		postsListPage.waitForTableToLoad();
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
		String postId = postsListPage.ViewPost(NEWTITLE);	
		assertEquals(driver.getCurrentUrl().contains(postId), true);
	}

	// *********************EDIT POST************************//

	@Test
	public void tc20_testEditPost() {
		postsListPage.inputStrigInTitle(NEWTITLE);
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);	
		String postId = postsListPage.editPost(NEWTITLE);
		assertEquals(driver.getCurrentUrl().contains(postId), true);
	}

	// *********************DELETING POST************************//

	@Test
	public void tc21_testDeletePost() {
		
		postsListPage.inputStrigInTitle("I need to delete this post");
		postsListPage.waitForTableToLoad();
		
		if (postsListPage.isPostInTable("I need to delete this post"))
		{
			postsListPage.deletePost("I need to delete this post");
		}
		else {
		postsListPage.clickOnAddPost();
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");
		postsAddPage.SelectCategory();
		postsAddPage.inputStrigInName("I need to delete this post");
		postsAddPage.inputStrigInDescription("I need to desable this post I need to delete this post");
		postsAddPage.SelectCheckbox();
		postsAddPage.writenContent("I need to delete this post");
		postsAddPage.clickSave();
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
		
		postsListPage.inputStrigInTitle("I need to delete this post");
		postsListPage.waitForTableToLoad();
		postsListPage.deletePost("I need to delete this post");
		postsListPage.isPostInTable("I need to delete this post");
			}
		postsListPage.waitForTableToLoad();
		assertEquals(postsListPage.isPostInTable("I need to delete this post"), false);  
	
	}

	@Test
	public void tc22_testDeleteCancelPost() {
		
		postsListPage.inputStrigInTitle(NEWTITLE);
		postsListPage.waitForTableToLoad();
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
		postsListPage.deleteCancelPost(NEWTITLE);
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
	}

	// *********************DisablePOST ************************//
	@Test
	public void tc23_testDisablePost() {
		
		postsListPage.waitForTableToLoad();
		postsListPage.inputStrigInTitle(NEWTITLE);
		
		postsListPage.DisableOREnablePost(NEWTITLE);
		
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
	
	}

	@Test
	public void tc24_testCancelDisablePost() {
		postsListPage.waitForTableToLoad();
		postsListPage.inputStrigInTitle(NEWTITLE);
		postsListPage.CancelDisableOREnablePost(NEWTITLE);
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
	}

	// *********************UNIMPORTENT POST RADI************************//
	@Test
	public void tc25_testUnimportantPost() {
		postsListPage.waitForTableToLoad();
		postsListPage.inputStrigInTitle(NEWTITLE);
		
		
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
		postsListPage.UnimportantORImportantPost(NEWTITLE);

		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);

	}

	@Test
	public void tc26_testCancelUnimportantPost() {
	
		postsListPage.inputStrigInTitle(NEWTITLE);
		postsListPage.waitForTableToLoad();
		assertEquals(postsListPage.isPostInTable(NEWIMPORTANT), true);
		
		postsListPage.CancelUnimportantORImportantPost(NEWTITLE);
		postsListPage.waitForTableToLoad();
		assertEquals(postsListPage.isPostInTable(NEWIMPORTANT), true);

	}

	@Test
	public void tc27_testImportantPost() {
		
		postsListPage.waitForTableToLoad();
		postsListPage.inputStrigInTitle(NEWTITLE);
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
		postsListPage.UnimportantORImportantPost(NEWTITLE);

		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);

	}

	@Test
	public void tc28_testCancelImportantPost() {
		postsListPage.waitForTableToLoad();
		postsListPage.inputStrigInTitle(NEWTITLE);
		
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
		postsListPage.CancelUnimportantORImportantPost(NEWTITLE);
		assertEquals(postsListPage.isPostInTable(NEWTITLE), true);
	}

	

//***********************CheckMenu********************************************************//
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
