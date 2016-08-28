import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class CalculatorsPaycheckcity {

    private String baseUrl = "http://www.paycheckcity.com/";
    private WebDriver driver;
    private String expectedResultSalary = "$8,387.79";
    private String expectedResultHourly = "8640";

    @BeforeSuite(groups = "p1",alwaysRun = true)
    public void BeforeSuite() throws Exception {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        System.out.println(baseUrl);
        driver.manage().timeouts().implicitlyWait(101,TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test (groups = {"regression"})
    public void fillParametersAndCalculateSalary() throws Exception {
        driver.get(baseUrl+"calculator/salary/");
        System.out.println(baseUrl+"calculator/salary/");
        driver.manage().timeouts().implicitlyWait(101,TimeUnit.SECONDS);

        driver.findElement(By.id("calcDate")).clear();
        driver.findElement(By.id("calcDate")).sendKeys("08/31/2016");
        driver.findElement(By.id("state")).clear();
        driver.findElement(By.id("state")).sendKeys("California");
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("generalInformation.grossPayAmount")).clear();
        driver.findElement(By.id("generalInformation.grossPayAmount")).sendKeys("10000");
        driver.findElement(By.id("generalInformation.grossPayMethodType")).clear();
        driver.findElement(By.id("generalInformation.grossPayMethodType")).sendKeys("Pay Per Period");
        driver.findElement(By.id("generalInformation.payFrequencyType")).clear();
        driver.findElement(By.id("generalInformation.payFrequencyType")).sendKeys("Monthly");
        if ( !driver.findElement(By.id("generalInformation.exemptFederal1")).isSelected() )
        {
            driver.findElement(By.id("generalInformation.exemptFederal1")).click();
        }
        driver.findElement(By.id("calculate")).click();
        System.out.println("All data prepared");

        WebElement dynamicElementSalary = (new WebDriverWait(driver, 101))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("resultData")));
        System.out.println("Net Pay is presented");

        String actualResultSalary
                = driver.findElement(By.xpath("//span[@class='resultDef' and text()='Net Pay']/following-sibling::span[1]")).getText();
        System.out.println("expectedResultSalary = "+expectedResultSalary);
        System.out.println("actualResultSalary = "+actualResultSalary);
        assertEquals(actualResultSalary, expectedResultSalary);
    }

    @Test (groups = {"regression"})
    public void fillParametersAndCalculateHourly() throws Exception {

        driver.get(baseUrl+"calculator/hourly/");
        System.out.println(baseUrl+"calculator/hourly/");
        driver.manage().timeouts().implicitlyWait(101, TimeUnit.SECONDS);

        driver.findElement(By.id("calcType.rates0.payRate")).clear();
        driver.findElement(By.id("calcType.rates0.payRate")).sendKeys("50");
        driver.findElement(By.id("calcType.rates0.hours")).clear();
        driver.findElement(By.id("calcType.rates0.hours")).sendKeys("160");
        driver.findElement(By.id("addRate_label")).click();
        WebElement dynamicElementHourly = (new WebDriverWait(driver, 101))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("calcType.rates1.payRate")));
        driver.findElement(By.id("calcType.rates1.payRate")).clear();
        driver.findElement(By.id("calcType.rates1.payRate")).sendKeys("80");
        driver.findElement(By.id("calcType.rates1.hours")).clear();
        //    driver.findElement(By.id("generalInformation.grossPayAmount")).clear();
        driver.findElement(By.id("calcType.rates1.hours")).sendKeys("8");
        driver.findElement(By.id("generalInformation.grossPayAmount")).click();

        String actualResultHourly
                = driver.findElement(By.id("generalInformation.grossPayAmount")).getAttribute("value");
        System.out.println("expectedResultHourly = "+expectedResultHourly);
        System.out.println("actualResultHourly = "+actualResultHourly);
        assertEquals(actualResultHourly, expectedResultHourly);

    }
    @AfterSuite(groups = "p1",alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}