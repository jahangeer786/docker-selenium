package com.learning_docker.tests;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;


public abstract class AbstractTest {

    protected WebDriver driver;

    @BeforeTest
    public void setDriver()throws Exception{
        if(Boolean.getBoolean("selenium.grid.enabled"))
            this.driver = getRemotWebDriver();
        else 
            this.driver = getLocalDriver();
    }

    public WebDriver getRemotWebDriver() throws Exception{
        Capabilities capabilities;

        if(System.getProperty("browser").equals("chrome"))
            capabilities = new ChromeOptions();
        else
            capabilities = new FirefoxOptions();
        
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    } 

    public WebDriver getLocalDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }

}