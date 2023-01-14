import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MyParameters {
	WebDriver driver;
	int numbersOfTries ;
	
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		
		driver.get("https://smartbuy-me.com/smartbuystore/en/");

	}

	@Test(invocationCount = 80)
	public void home_Page() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		driver.findElement(By.xpath(
				"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[1]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
				.click();

		driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();

		
		for (int i = 1; i < numbersOfTries; i++) {

			driver.findElement(By.xpath("//*[@id=\"updateCartForm0\"]/span[2]")).click();

			WebElement AlertMsg = driver.findElement(By.xpath("/html/body/main/div[3]/div[1]"));

			String myMsg = AlertMsg.getText();

			if (myMsg.contains("insufficient")) {

				numbersOfTries = i;
			}

		}

		WebElement singleUnitPrice = driver.findElement(
				By.xpath("/html/body/main/div[3]/div[2]/div[2]/div[1]/div/ul/table/tbody/tr/td/li[1]/div[4]"));

		String singleUnitPriceUpdated = singleUnitPrice.getText().replace(" JOD", "");

		double singleUnitPRiceAsdouble = Double.parseDouble(singleUnitPriceUpdated);

		double myTotalPrice = singleUnitPRiceAsdouble * numbersOfTries;

		System.err.println(myTotalPrice);

		WebElement actualPRice = driver.findElement(By.xpath(
				"/html/body/main/div[3]/div[2]/div[2]/div[1]/div/ul/table/tbody/tr/td/li[1]/div[9]/div/div/div"));

		String ActualPRiceinString = actualPRice.getText();
		System.out.println("*********" + ActualPRiceinString + "*********");

	}

	@AfterTest
	public void closing_the_browser() {
		driver.close();
	}
}
