package finale.webpages.posts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostsListPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin";

	@FindBy(name = "title")
	private WebElement wePostTitle;
	@FindBy(name = "user_id")
	private WebElement wePostAuthor;
	@FindBy(name = "post_category_id")
	private WebElement wePostCategory;
	@FindBy(name = "important")
	private WebElement wePostImportant;
	@FindBy(name = "status")
	private WebElement wePostStatus;
	@FindBy(name = "tag_ids")
	private WebElement wePostTag;
	@FindBy(name = "entities-list-table_length")
	private WebElement wePostShow;
	@FindBy(xpath = "//input[@class='form-control form-control-sm']")
	private WebElement wePostSearch;

	@FindBy(xpath = "//a[contains(.,'Add new Post')]")
	private WebElement weAddnewPost;
	@FindBy(xpath = "//button[text()='Delete']")
	private WebElement weDialogDelete;

	public PostsListPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	public void open() {
		this.driver.get(PAGE_URL + "/posts");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("title")));

	}

	public void inputStrigInTitle(String name) {
		wePostTitle.clear();
		wePostTitle.sendKeys(name);
	}

	public void clickOnAddPost() {
		weAddnewPost.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("title")));
	}

	// Select
	public void SelectPostAuthor() {
		wait.until(ExpectedConditions.elementToBeClickable(wePostTag));

		Select select = new Select(wePostAuthor);

		for (WebElement option : select.getOptions()) {
			if (!option.getAttribute("value").isEmpty()) {
				select.selectByValue(option.getAttribute("value"));
				break;
			}
		}
	}

	public void SelectPostCategory() {
		wait.until(ExpectedConditions.elementToBeClickable(wePostCategory));

		Select select = new Select(wePostCategory);

		for (WebElement option : select.getOptions()) {
			if (!option.getAttribute("value").isEmpty()) {
				select.selectByValue(option.getAttribute("value"));
				break;
			}
		}
	}

	public void SelectPostImportant() {
		wait.until(ExpectedConditions.elementToBeClickable(wePostImportant));

		Select select = new Select(wePostImportant);

		for (WebElement option : select.getOptions()) {
			if (!option.getAttribute("value").isEmpty()) {
				select.selectByValue(option.getAttribute("value"));
				break;
			}
		}
	}

	public void SelectPostTag() {
		wait.until(ExpectedConditions.elementToBeClickable(wePostTag));
		Select select = new Select(wePostTag);

		for (WebElement option : select.getOptions()) {
			if (!option.getAttribute("value").isEmpty()) {
				select.selectByValue(option.getAttribute("value"));
				break;
			}
		}
	}

	public void SelectPostStatus() {
		wait.until(ExpectedConditions.elementToBeClickable(wePostStatus));

		Select select = new Select(wePostStatus);

		for (WebElement option : select.getOptions()) {
			if (!option.getAttribute("value").isEmpty()) {
				select.selectByValue(option.getAttribute("value"));
				break;
			}
		}
	}

	public void SelectPostShow() {
		wait.until(ExpectedConditions.elementToBeClickable(wePostShow));

		Select select = new Select(wePostShow);

		for (WebElement option : select.getOptions()) {
			if (!option.getAttribute("value").isEmpty()) {
				select.selectByValue(option.getAttribute("value"));
				break;
			}
		}
	}

	public void inputStrigInSearch(String name) {
		wePostSearch.clear();
		wePostSearch.sendKeys(name);
	}


	public void ListShow() {
		WebElement dropdownShow = driver.findElement(By.name("entities-list-table_length"));
		Select selectShow = new Select(dropdownShow);
		selectShow.selectByVisibleText("100");
	}

	
//****************************** TABELE  *************************************//

	public boolean isPostInTable(String name) {

		waitForTableToLoad();
		
		if (isTableEmpty()) {
			return false;
		}
		List<WebElement> titles = driver
				.findElements(By.xpath("//table[@id='entities-list-table']//tbody//td[contains(.,'" + name + "')]"));

		int count = titles.size();
		return count > 0;
	}

	public boolean isTableEmpty() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("entities-list-table")));
		return !driver.findElements(By.xpath("//td[@class='dataTables_empty']")).isEmpty();
	}

	public String getTableEmptyMessage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='dataTables_empty']")));
		WebElement EmptyMessage = driver.findElement(By.xpath("//td[@class='dataTables_empty']"));
		return EmptyMessage.getText();
	}

	// ********************DELETE **********************//


 public void deletePost(String name) {

		WebElement weDeleteButton = driver.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'"
				+ name + "')]]//i[contains(@class,'fa-trash')]"));

		weDeleteButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Delete']")));

		weDialogDelete.click();
		
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));
		
		waitForTableToLoad();
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(
	            By.xpath("//table[@id='entities-list-table']//tr[td[normalize-space()='" + name + "']]") ));

		
	}

	public void deleteCancelPost(String name) {

		WebElement weDeleteButton = driver.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'"
				+ name + "')]]//i[contains(@class,'fa-trash')]"));

		weDeleteButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Cancel']")));

		driver.findElement(By.xpath("//button[text()='Cancel']")).click();
	}

	// ********************EDIT**********************//

	public String editPost(String name) {

		waitForTableToLoad();
		
		WebElement row = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]"));

		String postId = row.findElement(By.xpath("td[1]")).getText();
		
		// String postTitle = row.findElement(By.xpath("td[5]")).getText();
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//title[.='"+postTitle+"')]")));

		row.findElement(By.xpath(".//i[contains(@class,'fa-edit')]")).click();

		return postId;

	}

	// ********************View**********************//

	public String ViewPost(String name) {

		waitForTableToLoad();

		WebElement row = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]"));

		String postId = row.findElement(By.xpath("td[1]")).getText();
		
		if ("disabled".equalsIgnoreCase(postId)) {
			enabledPost(name);
		} 
		// String postTitle = row.findElement(By.xpath("td[5]")).getText();

		row.findElement(By.xpath(".//i[contains(@class,'fa-eye')]")).click();
		
		return postId;

	}

// ********************Disable***********************************************//

	public void disablePost(String name) {

		waitForTableToLoad();

		WebElement weDisableButton = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name
						+ "')]]//i[contains(@class,'fa-minus-circle')]"));

		weDisableButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Disable')]")));
		driver.findElement(By.xpath("//button[contains(.,'Disable')]")).click();

	}

	public void DisableOREnablePost(String name) {

		waitForTableToLoad();
		WebElement row = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]"));

		String postD = row.findElement(By.xpath("td[4]")).getText();

		if ("disabled".equalsIgnoreCase(postD)) {
			enabledPost(name);
		} else {
			disablePost(name);
		}
	}

	public void enabledPost(String name) {
		waitForTableToLoad();

		WebElement weEnabledButton = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name
						+ "')]]//i[contains(@class,'fa-check')]"));

		weEnabledButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Enable')]")));
		driver.findElement(By.xpath("//button[contains(.,'Enable')]")).click();

	}

	public void CancelDisableOREnablePost(String name) {

		waitForTableToLoad();
		WebElement row = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]"));

		String postD = row.findElement(By.xpath("td[4]")).getText();

		if ("disabled".equalsIgnoreCase(postD)) {
			CancelEnabledPost(name);
		} else {
			CancelDisablePost(name);
		}
	}
	
	public void CancelDisablePost(String name) {

		waitForTableToLoad();

		WebElement weDisableButton = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name
						+ "')]]//i[contains(@class,'fa-minus-circle')]"));

		weDisableButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//form[@id='disable-modal']//button[@class='btn btn-default']")));
		driver.findElement(By.xpath("//form[@id='disable-modal']//button[@class='btn btn-default']")).click();		
	}
	
	public void CancelEnabledPost(String name) {

		waitForTableToLoad();

		WebElement weDisableButton = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name
						+ "')]]//i[contains(@class,'fa-minus-circle')]"));

		weDisableButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//form[@id='disable-modal']//button[@class='btn btn-default']")));
		driver.findElement(By.xpath("//form[@id='disable-modal']//button[@class='btn btn-default']")).click();
		
		
	}
	
// *********************Unimportant*******************//

	public void UnimportantPost(String name) {

		waitForTableToLoad();
		WebElement weUnimportantButton = driver.findElement(By.xpath(
				"//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]//i[@class='fas fa-times']"));

		weUnimportantButton.click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Set as Unimportant')]")));

		driver.findElement(By.xpath("//button[contains(.,'Set as Unimportant')]")).click();
	}

	public void UnimportantORImportantPost(String name) {

		waitForTableToLoad();

		WebElement row = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]"));

		String postUI = row.findElement(By.xpath("td[3]")).getText();

		//System.out.println(postUI);

		if ("Yes".equalsIgnoreCase(postUI)) {
			UnimportantPost(name);
		} else {
			ImportantPost(name);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("entities-list-table")));
	}

	public void ImportantPost(String name) {

		waitForTableToLoad();
		WebElement weImportantButton = driver.findElement(By.xpath(
				"//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]//i[@class='fas fa-bookmark']"));

		weImportantButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Set as Important')]")));
		driver.findElement(By.xpath("//button[contains(.,'Set as Important')]")).click();

	}

	public void CancelUnimportantORImportantPost(String name) {

		waitForTableToLoad();
		WebElement row = driver
				.findElement(By.xpath("//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]"));

		String postUI = row.findElement(By.xpath("td[3]")).getText();

		//System.out.println(postUI);

		if ("Yes".equalsIgnoreCase(postUI)) {
			CancelUnimportantPost(name);
		} else {
			CancelImportantPost(name);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("entities-list-table")));
	}

	public void CancelUnimportantPost(String name) {

		WebElement weUnimportantButton = driver.findElement(By.xpath(
				"//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]//i[@class='fas fa-times']"));

		weUnimportantButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//form[@id='unimportant-modal']//button[@class='btn btn-default']")));
		driver.findElement(By.xpath("//form[@id='unimportant-modal']//button[@class='btn btn-default']")).click();

	}

	public void CancelImportantPost(String name) {

		WebElement weImportantButton = driver.findElement(By.xpath(
				"//table[@id='entities-list-table']//tr[td[contains(.,'" + name + "')]]//i[@class='fas fa-bookmark']"));

		weImportantButton.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//form[@id='important-modal']//button[@class='btn btn-default']")));
		driver.findElement(By.xpath("//form[@id='important-modal']//button[@class='btn btn-default']")).click();
										
	}

//***************************Wait 	********************//
	public void waitForTableToLoad() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("entities-list-table")));
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='entities-list-table']//tbody/tr")));
		 

	}


	
}
