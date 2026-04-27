package com.cybercom.api.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cybercom.api.reports.ExtentReportManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {

    protected ExtentTest extentTest;

    @BeforeMethod
    public void startTest(Method method) {
        extentTest = ExtentReportManager.createTest(method.getName());
    }

    @AfterMethod
    public void endTest(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, "Test Passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else {
            extentTest.log(Status.SKIP, "Test Skipped");
        }
    }

    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flush();
    }
}