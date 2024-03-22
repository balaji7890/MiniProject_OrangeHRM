package humanResources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;


public class humanresources {
	
	public static WebDriver driver;	
	Properties prop = new Properties();
    List<WebElement> listt;
	
	public void setdata() throws IOException
	{
		FileOutputStream file = new FileOutputStream("C:\\Users\\2308822\\eclipse-workspace\\seleniumproject\\testdata\\output.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		for(int r=0;r<listt.size();r++) {
			XSSFRow currentrow = sheet.createRow(r);
			XSSFCell cell_0 = currentrow.createCell(0);
			String s = listt.get(r).getText();
			cell_0.setCellValue(s);
		}
		workbook.write(file);
		workbook.close();
		file.close();
	}
	
	public void property()
	{
		File file = new File("C:\\Users\\2308822\\eclipse-workspace\\seleniumproject\\src\\test\\java\\humanResources\\testdata.properties");
		  
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		//load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void driversetup() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter 1 for Chrome Driver and 2 for Edge Driver");
		int a = sc.nextInt();
		if(a==1) {
			System.out.println("Chrome Browser Launched...");
			System.out.println("Please wait for few seconds...");
			driver = new ChromeDriver();// Launching chrome driver
		}
		
		else if(a==2){
			System.out.println("Edge Browser Launched...");
			System.out.println("Please wait for few seconds...");
			driver = new EdgeDriver();// Launching Edge driver
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));// Implementing implicit wait
	}
	
	public void login() {
		
		// Setting the path to ChromeDriver executable
        // Open the URL
		
		property();
		driver.get(prop.getProperty("URL"));

        // Log in
        
		driver.manage().window().maximize();// Maximazing Chrome Browser
		
		driver.findElement(By.name("username")).sendKeys(prop.getProperty("username"));// Providing Username to input Text Box
		driver.findElement(By.name("password")).sendKeys(prop.getProperty("password"));//Providing Password to input Text Box
		
	}
	
	public void login_enable() {
		
		//Checking if login button is Enabled or not
		
		WebElement check = driver.findElement(By.cssSelector("button[type='submit']"));
		boolean ch = check.isEnabled();
		
		if(ch == true) 
		{
			System.out.println("Login button is Enabled");
		}
		
		else 
		{
			System.out.println("Login button is not Enabled");
		}
		
		// Clicking Login Button
		
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
	}
	
	public void contains_dashboard() {
		
		/* Verify URL contains "Dashboard" */
		
		// Storing CurrentURL in String Variable
		
        String currentUrl = driver.getCurrentUrl(); 
        
        if (currentUrl.contains("dashboard")) 
        {
            System.out.println("URL verification successful!");
        } 
        
        else 
        {
            System.out.println("URL verification failed.");
        }
        
	}
	
	public void navigate_Admin() {
		
		// Navigate to Admin Tab
		
        driver.findElement(By.xpath("//li[1]/a[1]/span")).click();
        
	}
	
	public void navigate_job() throws InterruptedException {
		
		// Navigate to Job Tab
		
        driver.findElement(By.xpath("//span[contains(text(),'Job')]")).click();
        Thread.sleep(3000);
        
	}
	
	public void Check_job_Titles() {
		
		// Checking if "Job Titles" is present or not
        // If present, Click "Job Titles"
        
		List<WebElement> options = driver.findElements(By.cssSelector("a[class='oxd-topbar-body-nav-tab-link']"));
        //System.out.println("the size is "+options.size());
        
        for(WebElement op:options) {
        	String text = op.getText();
        	
        	if(text.equals("Job Titles")) 
        	{
        		System.out.println("Job Titles is present"); 
        		op.click();
        		break;
        	}
        	
        	else 
        	{
        		System.out.println("Job Titles is not present");
        	}
        }
        
	}
	
	public void Retrieve_job_titles() {
		
		/* Retrieve list of all available jobs */
        
        // Storing jobs in List
		
        listt = driver.findElements(By.cssSelector("div[class='oxd-table-card']")); 
        
        //System.out.println("The size of jobs available is "+listt.size());
        
        System.out.println("The Jobs Available are: ");
        
        for(WebElement li:listt) {
        	System.out.println(li.getText());
        }
        
	}
	
	public void Add_Job() throws InterruptedException {
		
		 /* Add a new job titled "Automation Tester" */
        
        // Clicking on Add Button
		
        driver.findElement(By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--secondary']")).click(); 

        // Adding "Automation Tester" Job in the Job List
        
        property();
        driver.findElement(By.xpath("//div/div[2]/input[@class='oxd-input oxd-input--active']")).sendKeys(prop.getProperty("Add_Job"));

        // Clicking on Saving Button
        
        driver.findElement(By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']")).click();
        Thread.sleep(10000);
	}
	
	public void Logout() throws InterruptedException {
		
		 // Clicking on User Button
		
        driver.findElement(By.cssSelector("p[class='oxd-userdropdown-name']")).click();

        // Clicking on Logout Button
        
        driver.findElement(By.linkText("Logout")).click();
        Thread.sleep(3000);
        
	}
	
	public void Browser_close() {
		
		// Close the browser
        //driver.close();
        driver.quit();
        
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		
		humanresources hs = new humanresources();
		hs.driversetup();
		hs.login();
		hs.login_enable();
		hs.contains_dashboard();
		hs.navigate_Admin();
		hs.navigate_job();
		hs.Check_job_Titles();
		hs.Retrieve_job_titles();
		hs.setdata();
		hs.Add_Job();
		hs.Logout();
		hs.Browser_close();
		
	}

}