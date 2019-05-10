package utils;


import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.MalformedURLException;
import java.net.URL;

//class for producing driver objects based on the OS

public class DriverFactory
{
    public static WebDriver driver;
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows(){
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isUnix(){
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
	
    @Before
    public void setUp() throws MalformedURLException{
		
		if(isWindows()){
			System.setProperty("webdriver.chrome.driver", "web_drivers/chromedriver_win32/chromedriver.exe");
			driver = new ChromeDriver();
		}else
			if(isUnix()){
				System.setProperty("webdriver.chrome.driver", "web_drivers/chromedriver_linux64/chromedriver.exe");
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--no-sandbox");
				driver = new ChromeDriver(chromeOptions);
		}  
    }

    @After
    public void finishUp(){
        driver.close();
    }
}