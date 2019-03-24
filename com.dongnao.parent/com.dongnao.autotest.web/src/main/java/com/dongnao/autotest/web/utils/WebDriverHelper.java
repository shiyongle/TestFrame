package com.dongnao.autotest.web.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author Richered
 *
 */
public class WebDriverHelper {
	private String type = WebDriverFactory.CHROME;
	private String driverPath;
	private Object options;
	private WebDriver webDriver;

	public WebDriverHelper(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public WebDriverHelper(String type, String driverPath) {
		this.type = type;
		this.driverPath = driverPath;
		this.webDriver = WebDriverFactory.getWebDriver(type, driverPath);
	}

	public WebDriverHelper(String type, String driverPath, Object options) {
		this.type = type;
		this.driverPath = driverPath;
		this.options = options;
		this.webDriver = WebDriverFactory.getWebDriver(type, driverPath, options);
	}

	/**
	 * 获取WebDriver
	 * 
	 * @return
	 */
	public <T extends WebDriver> T getWebDriver() {
		return (T) this.webDriver;
	}

	/**
	 * 根据id查找元素
	 * 
	 * @param id
	 * @return
	 */
	public WebElement findById(String id) {
		return this.webDriver.findElement(By.id(id));
	}

	/**
	 * 根据name查找元素
	 * 
	 * @param name
	 * @return
	 */
	public WebElement findByName(String name) {
		return this.webDriver.findElement(By.name(name));
	}

	/**
	 * 根据xpath查找元素
	 * 
	 * @param xpathExpression
	 * @return
	 */
	public WebElement findByXPath(String xpathExpression) {
		return this.webDriver.findElement(By.xpath(xpathExpression));
	}

	/**
	 * 根据className查找元素
	 * 
	 * @param className
	 * @return
	 */
	public WebElement findByClassName(String className) {
		return this.webDriver.findElement(By.className(className));
	}

	/**
	 * 根据css选择器查找元素
	 * 
	 * @param selector
	 * @return
	 */
	public WebElement findByCssSelector(String selector) {
		return this.webDriver.findElement(By.cssSelector(selector));
	}

	/**
	 * 根据Html标签名查找元素
	 * 
	 * @param tagName
	 * @return
	 */
	public WebElement findByTagName(String tagName) {
		return this.webDriver.findElement(By.tagName(tagName));
	}

	/**
	 * 根据链接内容查找元素
	 * 
	 * @param linkText
	 * @return
	 */
	public WebElement findByLinkText(String linkText) {
		return this.webDriver.findElement(By.linkText(linkText));
	}

	/**
	 * 根据部分链接内容查找元素
	 * 
	 * @param linkText
	 * @return
	 */
	public WebElement findByPartialLinkText(String linkText) {
		return this.webDriver.findElement(By.partialLinkText(linkText));
	}

	/**
	 * 根据xpath查找元素
	 * 
	 * @param xpathExpression
	 * @return
	 */
	public List<WebElement> findByXPaths(String xpathExpression) {
		return this.webDriver.findElements(By.xpath(xpathExpression));
	}

	/**
	 * 根据className查找元素
	 * 
	 * @param className
	 * @return
	 */
	public List<WebElement> findByClassNames(String className) {
		return this.webDriver.findElements(By.className(className));
	}

	/**
	 * 根据css选择器查找元素
	 * 
	 * @param selector
	 * @return
	 */
	public List<WebElement> findByCssSelectors(String selector) {
		return this.webDriver.findElements(By.cssSelector(selector));
	}

	/**
	 * 根据Html标签名查找元素
	 * 
	 * @param tagName
	 * @return
	 */
	public List<WebElement> findByTagNames(String tagName) {
		return this.webDriver.findElements(By.tagName(tagName));
	}

	/**
	 * 根据链接内容查找元素
	 * 
	 * @param linkText
	 * @return
	 */
	public List<WebElement> findByLinkTexts(String linkText) {
		return this.webDriver.findElements(By.linkText(linkText));
	}

	/**
	 * 根据部分链接内容查找元素
	 * 
	 * @param linkText
	 * @return
	 */
	public List<WebElement> findByPartialLinkTexts(String linkText) {
		return this.webDriver.findElements(By.partialLinkText(linkText));
	}

	/**
	 * 获取标题
	 */
	public String getTitle() {
		return this.webDriver.getTitle();
	}

	/**
	 * 获取页面源码
	 */
	public String getPageSource() {
		return this.webDriver.getPageSource();
	}

	/**
	 * 获取当前Url
	 */
	public String getCurrentUrl() {
		return this.webDriver.getCurrentUrl();
	}

	/**
	 * 打开网页
	 * 
	 * @param url
	 */
	public void openPage(String url) {
		this.webDriver.get(url);
	}

	/**
	 * 窗口最大化
	 */
	public void maximize() {
		this.webDriver.manage().window().maximize();
	}

	/**
	 * 显示等待/强制等待
	 * 
	 * @param millis
	 */
	public void sleep(String millis) {
		try {
			Thread.sleep(Long.valueOf(millis) * 1000);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 显式等待/条件等待
	 * 
	 * @param by
	 */
	public void wait(By by) {
		WebDriverWait wait = new WebDriverWait(this.webDriver, 10);
		WebElement wl = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(by);
			}
		});
	}

	/**
	 * 隐式等待/全局等待
	 * 
	 * @param time
	 * @param unit
	 */
	public void implicitlyWait(long time, TimeUnit unit) {
		this.webDriver.manage().timeouts().implicitlyWait(time, unit);
	}

	/**
	 * 点击
	 * 
	 * @param webElement
	 */
	public void click(WebElement webElement) {
		webElement.click();
	}

	/**
	 * 点击
	 * 
	 * @param webElement
	 */
	public void click(RemoteWebElement webElement) {
		webElement.click();
	}

	/**
	 * 发送关键字
	 * 
	 * @param webElement
	 * @param keysToSend
	 */
	public void sendKeys(WebElement webElement, String keysToSend) {
		webElement.sendKeys(keysToSend);
	}

	/**
	 * 发送关键字
	 * 
	 * @param webElement
	 * @param keysToSend
	 */
	public void sendKeys(RemoteWebElement webElement, String keysToSend) {
		webElement.sendKeys(keysToSend);
	}

	/**
	 * 滚动
	 */
	public void scrollTo(long x, long y) {
		((JavascriptExecutor) this.webDriver).executeScript(String.format("scrollTo(%s,%s)", x, y));
	}

	/**
	 * 鼠标悬停
	 */
	public void mouseOver(WebElement webElement) {
		Actions action = new Actions(this.webDriver);
		action.moveToElement(webElement).moveByOffset(10, 3).build().perform();
	}

	/**
	 * 包含
	 * 
	 * @param dest
	 * @return
	 */
	public boolean contains(String dest) {
		String source = this.getPageSource();
		return source.contains(dest);
	}

	/**
	 * 不为null
	 * 
	 * @param source
	 * @param dest
	 * @return
	 */
	public boolean notNull(Object obj) {
		return null != obj;
	}

	/**
	 * 不为null
	 * 
	 * @param source
	 * @param dest
	 * @return
	 */
	public boolean notNull(ArrayList<RemoteWebElement> obj) {
		return null != obj;
	}

	/**
	 * 屏幕快照
	 * 
	 * @return
	 */
	public void snapshotScreen(String destPath) {
		WebDriver augumentDriver = new Augmenter().augment(this.webDriver);
		File file = ((TakesScreenshot) augumentDriver).getScreenshotAs(OutputType.FILE);
		try {
			String fileName = destPath + "/" + UUID.randomUUID().toString() + ".png";
			FileUtils.copyFile(file, new File(fileName));
		} catch (IOException e) {
		}
	}

	/**
	 * 关闭浏览器
	 */
	public void close() {
		this.webDriver.close();
	}
}
