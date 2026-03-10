package finale.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/login";

	@FindBy(name = "email")
	private WebElement weEmail;

	@FindBy(name = "password")
	private WebElement wePassword;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement weButtonLogin;

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	public void open() {
		driver.get(PAGE_URL);
		wait.until(ExpectedConditions.visibilityOf(weEmail));
	}

	public void loginSuccess() {
		weEmail.sendKeys("kursqa@cubes.edu.rs");
		wePassword.sendKeys("cubesqa");
		weButtonLogin.click();
		wait.until(ExpectedConditions.urlContains("/admin"));
	}

	
	
}

