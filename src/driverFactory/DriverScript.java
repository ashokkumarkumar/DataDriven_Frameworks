package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class DriverScript extends AppUtil{
String inputpath ="D:\\11oclockSelenium\\DataDriven_Framework\\TestInput\\TestData.xlsx";
String outputpath ="D:\\11oclockSelenium\\DataDriven_Framework\\TestOutPut\\DataDrivenResults.xlsx";

@Test

public void startTest()throws Throwable
{
	
//create object for excelfile util class
	
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	
//count no rows in Login sheet
	
	int rc =xl.rowCount("Login");
	Reporter.log("No of rows in a sheet::"+rc,true);
	
	for(int i=1;i<=rc;i++)
	{
		String user = xl.getCellData("Login", i, 0);
		String pass =  xl.getCellData("Login", i, 1);
		
		//call login methods
		boolean res =FunctionLibrary.verifyLogin(user, pass);
		if(res)
		{
			
			//if res true   write as login success into results cell
			xl.setCellData("Login", i, 2, "Login Success", outputpath);
			xl.setCellData("Login", i, 3, "Pass", outputpath);
		}
		else
		{
			
			//take screen shot for fail test
			File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./ScreenShot/Iterations/"+i+"_"+"Loginpage.png"));
			xl.setCellData("Login", i, 2, "Login Fail", outputpath);
			xl.setCellData("Login", i, 3, "Fail", outputpath);
		}
	}
}
}