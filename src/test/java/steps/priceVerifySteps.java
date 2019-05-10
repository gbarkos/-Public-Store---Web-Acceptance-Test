package steps;

import java.util.List;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.net.MalformedURLException;
import java.net.URL;
import utils.DriverFactory;
import utils.StringManipulation;
import static org.junit.Assert.*;

public class priceVerifySteps 
{
	WebDriver driver = DriverFactory.driver;;
	Actions action = new Actions(driver);
	
	@Given("^I navigate to \"([^\"]*)\"$")
	public void i_navigate_to(String page) throws Throwable 
	{
		driver.get(page);//load the specified page
	}

	@When("^I search for \"([^\"]*)\"$")
	public void i_search_for(String search) throws Throwable 
	{	
		driver.findElement(By.id("textsearch")).sendKeys(search);//send the desired search query to the search box
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div[2]/div/form/button[1]")).click();//find the search button and click it
	}

	@And("^I add two specific products to my cart$")
	public void i_add_two_secific_products() throws Throwable 
	{	
		//Here I use a WebDriverWait object, later on I use Thread.sleep(). On my laptop it runs fine, although WebDriverWait seeme a bit unreliable
		//on the second machine I run it. What is the norm in QA testing? Thread.sleep seems very unproffesional and a not so good approach.
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Inferno")));//wait until the specified element is visible
		Thread.sleep(3000);
		driver.findElement(By.partialLinkText("Inferno")).click();//find specified product and click on it
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div[2]/div[1]/div[1]/div/a[2]")));
		Thread.sleep(3000);
		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[1]/div[1]/div/a[2]")).click();// find "add to cart" button and click it
		
		Thread.sleep(4000);
		action.sendKeys(Keys.ESCAPE).perform();
		driver.navigate().back();
		
		//Question: Which is more efficient? Having WebDriverWait objects do the waiting or manually setting the Thread to sleep?
		
		Thread.sleep(4000);
		driver.findElement(By.partialLinkText("Da Vinci")).click();//find the product and click on it
		Thread.sleep(4000);
		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[1]/div[1]/div/a[2]")).click();//find the "add to cart" button and click on it
										
	}
	
	@And("^I open my cart$")
	public void i_open_my_cart() throws Throwable 
	{	Thread.sleep(4000);
		
		action.sendKeys(Keys.ESCAPE).perform();
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div[3]/div/div[4]/div[2]/a")).click();//get the cart button and click it
	}
	
	@And("^I increase the quantity of the second product by one$")
	public void i_increase_the_quantity_of_the_second_product_by_one() throws Throwable 
	{	Thread.sleep(3000);
		driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[3]/div/div[2]/div[1]/div/div[2]/div/div[3]/div/a[2]")).click();//get the increase  qty button of the second productand click it
	}
	
	@Then("^I should see that the price has been correctly updated$")
	public void i_should_see_that_the_price_has_been_correctly_updated() throws Throwable 
	{	
		Thread.sleep(3000);
		List<WebElement> prices = driver.findElements(By.className("cartProdTotal"));//get a list of all the products' prices
		
		String rcvdtxt;
		double prc;
		double expectedValue = 0;
		
		for(int i=0; i<prices.size(); i++){ //iterate the list so we get each price
			rcvdtxt = prices.get(i).getText(); //get the price as text
			prc=StringManipulation.preparePrice(rcvdtxt); //method that converts the text (eg 899€ which is infact 8,99€ due to the sites formatting choices) to a double (eg 8.99)
			expectedValue += prc;//add the price to the sum
		}
		
		String total = driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[4]/div[2]/div[1]/div/div[1]/div[2]/div")).getText();//get the total price
		double actualValue = StringManipulation.preparePrice(total);//convert the text to double
		
		assertEquals(String.valueOf(expectedValue),String.valueOf(actualValue)); //final assert
	}
}
