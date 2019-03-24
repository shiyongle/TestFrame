package com.dongnao.autotest.web.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * 
 * @author easy
 *
 */
public class WebDriverFactory {
	public static final String CHROME = "chrome";
	public static final String IE = "ie";
	public static final String EDGE = "edge";
	public static final String FIREFOX = "firefox";
	public static final String OPERA = "opera";
	public static final String SAFARI = "safari";

	/**
	 * 重载+1 获取WebDriver
	 * 
	 * @param type
	 * @param driverPath
	 * @return
	 */
	public static WebDriver getWebDriver(String type, String driverPath) {
		return getWebDriver(type, driverPath, null);
	}

	/**
	 * 重载+2 获取WebDriver
	 * 
	 * @param type
	 * @param driverPath
	 * @param options
	 * @return
	 */
	public static WebDriver getWebDriver(String type, String driverPath, Object options) {
		switch (type) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverPath);
			if (null != options) {
				ChromeOptions co = (ChromeOptions) options;
				return new ChromeDriver(co);
			}
			return new ChromeDriver();
		case "ie":
			System.setProperty("webdriver.ie.driver", driverPath);
			return new InternetExplorerDriver();
		case "edge":
			System.setProperty("webdriver.edge.driver", driverPath);
			if (null != options) {
				EdgeOptions co = (EdgeOptions) options;
				return new EdgeDriver(co);
			}
			return new EdgeDriver();
		case "firefox":
			System.setProperty("webdriver.firefox.driver", driverPath);
			return new FirefoxDriver();
		case "opera":
			System.setProperty("webdriver.opera.driver", driverPath);
			if (null != options) {
				OperaOptions co = (OperaOptions) options;
				return new OperaDriver(co);
			}
			return new OperaDriver();
		case "safari":
			System.setProperty("webdriver.safari.driver", driverPath);
			if (null != options) {
				SafariOptions co = (SafariOptions) options;
				return new SafariDriver(co);
			}
			return new SafariDriver();
		default:
			throw new RuntimeException("Your browser driver not supported!");
		}
	}
}
