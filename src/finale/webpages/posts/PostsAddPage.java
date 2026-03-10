package finale.webpages.posts;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class PostsAddPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin";

	@FindBy(name = "post_category_id")
	private WebElement wePostCategory;

	@FindBy(name = "title")
	private WebElement wePostName;

	@FindBy(name = "description")
	private WebElement wePostDescription;

	@FindBy(id = "set-as-important")
	private WebElement wePostImportant;

	@FindBy(name = "tag_id[]")
	private WebElement wePostTag;

	@FindBy(name = "photo")
	private WebElement wePostPhoto;

	@FindBy(css = ".cke_wysiwyg_frame")
	private WebElement wePostContent;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement weButtonSave;

	@FindBy(xpath = "//a[contains(@class,'btn btn-outline-secondary')]")
	private WebElement weButtonCancel;

	@FindBy(xpath = "//i[@class='far fa-user']")
	private WebElement weButtonProfile;

	public PostsAddPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	public void open() {
		driver.get(PAGE_URL + "/posts/add");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("title")));
		PageFactory.initElements(driver, this);
	}

	public void inputStrigInName(String name) {
		wePostName.clear();
		wePostName.sendKeys(name);
	}
	
	public void inputStrigInDescription(String name) {
		wePostDescription.clear();
		wePostDescription.sendKeys(name);
	}

	public void clickSave() {
		Actions actions = new Actions(driver);
		actions.moveToElement(weButtonSave);
		actions.perform();
		wait.until(ExpectedConditions.elementToBeClickable(weButtonSave)).click();
	}

	public void clickCancel() {
		Actions actions = new Actions(driver);
		actions.moveToElement(weButtonCancel);
		actions.perform();
		wait.until(ExpectedConditions.elementToBeClickable(weButtonCancel)).click();
	}

	public void SelectCategory() {
		wait.until(ExpectedConditions.elementToBeClickable(wePostCategory));

		Select select = new Select(wePostCategory);

		for (WebElement option : select.getOptions()) {
			if (!option.getAttribute("value").isEmpty()) {
				select.selectByValue(option.getAttribute("value"));
				break;  }
		}
	}

	public void SelectCheckbox() {
		driver.findElement(By.name("tag_id[]"));
		if (!wePostTag.isSelected()) {
			wePostTag.click();   }

		List<WebElement> checkboxes = driver.findElements(By.name("tag_id[]"));

		for (WebElement chkbox : checkboxes) {
			if (chkbox.isDisplayed() && chkbox.isEnabled() && !chkbox.isSelected()) {
				chkbox.click();  
				 break; 
				 }
		}
	}
	

	public void writenContent(String text) {

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("title")));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".cke_wysiwyg_frame")));

		WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));
		body.clear();
		body.sendKeys(text);
		driver.switchTo().defaultContent();
	}

	

	//Check Table
	public boolean isTitleInList(String name) {
		ArrayList<WebElement> wePostName = (ArrayList<WebElement>) driver.findElements(By.xpath("//strong[text()='"+name+"']"));
			return !wePostName.isEmpty();
	}
	
	//Error
	public String getTitleErrorMessage() {
		WebElement weTitleError = driver.findElement(By.id("title-error"));
		return weTitleError.getText();
	}

	public String getDescriptionErrorMessage() {
		WebElement weDesError = driver.findElement(By.id("description-error"));
		return weDesError.getText();
	}

	public String getTagErrorMessage() {
		WebElement weTagError = driver.findElement(By.id("tag_id[]-error"));
		return weTagError.getText();
	}

	public String getConErrorMessage() {
		WebElement weConError = driver.findElement(By.className("invalid-feedback"));
		return weConError.getText();
	}

}
