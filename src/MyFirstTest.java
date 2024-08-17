import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyFirstTest {
	WebDriver driver = new ChromeDriver();
	String myWebsite = "https://magento.softwaretestingboard.com/";
	Random rand = new Random();
	String password = "iLoveMyDad24@6@1947";
	String LogoutPage = "https://magento.softwaretestingboard.com/customer/account/logout/";
	String emailAddressToLogin = "";
	String passwordAddressToLogin = "";

	@BeforeTest
	public void mySetUp() {
		driver.manage().window().maximize();
		driver.get(myWebsite);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@Test(priority = 1)
	public void CreateAnAccount() {

		WebElement CreateAccountPage = driver.findElement(By.linkText("Create an Account"));
		CreateAccountPage.click();
//		String[] thearrayNameforExampleFirstNames = { "firstname1", "firstname2", "firstname3" };
		String[] first_Names = { "Alice", "Bob", "Charlie", "David", "Eve" };
		String[] last_Names = { "Smith", "Johnson", "Williams", "Jones", "Brown" };
		String[] domainNames = { "@gmail.com", "@yahoo.com", "@outlook.com" };
		int randomIndexForTheFirstName = rand.nextInt(first_Names.length);
		int randomIndexForTheSecondName = rand.nextInt(last_Names.length);
		int randomIndexForThedomainNames = rand.nextInt(domainNames.length);
		System.out.println(randomIndexForTheFirstName);
		System.out.println(randomIndexForTheSecondName);
		WebElement FirstNameInput = driver.findElement(By.id("firstname"));
		WebElement LastNameInput = driver.findElement(By.id("lastname"));
		WebElement emailAddressInput = driver.findElement(By.id("email_address"));
		WebElement passwordInput = driver.findElement(By.id("password"));
		WebElement passwordconfirmInput = driver.findElement(By.id("password-confirmation"));
		WebElement createAccountButton = driver.findElement(By.xpath("//button[@title='Create an Account']"));

		String firstname = first_Names[randomIndexForTheFirstName];
		String lastname = last_Names[randomIndexForTheSecondName];
		int randomnumber = rand.nextInt(9841);
		String domainname = domainNames[randomIndexForThedomainNames];

		FirstNameInput.sendKeys(firstname);
		LastNameInput.sendKeys(lastname);
		emailAddressInput.sendKeys(firstname + lastname + randomnumber + domainname);
		passwordInput.sendKeys(password);
		passwordconfirmInput.sendKeys((password));
		createAccountButton.click();

		emailAddressToLogin = firstname + lastname + randomnumber + domainname;
		passwordAddressToLogin = password;

	}

	@Test(priority = 2)
	public void Logout() {
		driver.get(LogoutPage);
	}

	@Test(priority = 3)
	public void login() {
		WebElement LoginPage = driver.findElement(By.linkText("Sign In"));
		WebElement emaillogin = driver.findElement(By.id("email"));
		WebElement passwordlogin = driver.findElement(By.id("pass"));
		WebElement buttonLogin = driver.findElement(By.id("send2"));

		LoginPage.click();
		emaillogin.sendKeys(emailAddressToLogin);
		passwordlogin.sendKeys(passwordAddressToLogin);
		buttonLogin.click();
	}

	@Test(priority = 4)
	public void addWomanItem() throws InterruptedException {
		WebElement WomanSection = driver.findElement(By.id("ui-id-4"));
		WomanSection.click();

		WebElement productItemsContainer = driver.findElement(By.className("product-items"));
		List<WebElement> AllItem = productItemsContainer.findElements(By.tagName("li"));

		int numberofitem = AllItem.size();
		int randomItem = rand.nextInt(numberofitem);

		AllItem.get(randomItem).click();

		WebElement theContainerOfSizes = driver.findElement(By.cssSelector("div[class='swatch-attribute size'] div[role='listbox']"));
		List<WebElement> sizeOfItems = theContainerOfSizes.findElements(By.tagName("div"));

		int numberOfSizes = sizeOfItems.size();
		int randomsizes = rand.nextInt(numberOfSizes);

		sizeOfItems.get(randomsizes).click();

		WebElement colorOfItems = driver.findElement(By.cssSelector("div[class='swatch-attribute color'] div[role='listbox']"));
		List<WebElement> colors = colorOfItems.findElements(By.tagName("div"));

		int numberofcolors = colors.size();
		int randomcolors = rand.nextInt(numberofcolors);

		colors.get(randomcolors).click();

		WebElement addtocartbutton = driver.findElement(By.id("product-addtocart-button"));
		addtocartbutton.click();
		
		Thread.sleep(5000);
		
		WebElement MessageAdded = driver.findElement(By.cssSelector(".message-success.success.message"));

		System.out.println(MessageAdded.getText().contains("You added"));

		Assert.assertEquals(MessageAdded.getText().contains("You added"), true);
		
		//review 
		
		driver.navigate().refresh();
		
		WebElement reviewsection = driver.findElement(By.id("tab-label-reviews-title"));
		reviewsection.click();
		
		JavascriptExecutor js =  (JavascriptExecutor) driver; 
		
		js.executeScript("window.scrollTo(0,1200)");
		
		Thread.sleep(2000);
		
		WebElement rating = driver.findElement(By.cssSelector(".control.review-control-vote"));
		
		Thread.sleep(2000);
	
		rating.findElements(By.tagName("label")).size();
		
		String [] ids = {"Rating_1","Rating_2","Rating_3","Rating_4","Rating_5"};
		int randomindex = rand.nextInt(ids.length);
		
		WebElement element = driver.findElement(By.id(ids[randomindex]));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();",element);
		

		WebElement nickname = driver.findElement(By.id("nickname_field"));
		nickname.sendKeys("soso");

		WebElement summary = driver.findElement(By.id("summary_field"));
		summary.sendKeys("na");

		WebElement review = driver.findElement(By.id("review_field"));
		review.sendKeys("hello");

		WebElement submitReviewButton = driver.findElement(By.cssSelector(".action.submit.primary"));
		submitReviewButton.click();
		
		String actualTextforreview = driver.findElement(By.cssSelector(".message-success.success.message")).getText();
		String expectedTextforreview = "You submitted your review for moderation.";
		
		Assert.assertEquals(actualTextforreview,expectedTextforreview);
	}
}
