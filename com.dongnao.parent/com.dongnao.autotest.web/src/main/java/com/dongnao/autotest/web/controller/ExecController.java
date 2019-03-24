package com.dongnao.autotest.web.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnao.autotest.common.BooleanUtil;
import com.dongnao.autotest.common.DateTimeUtil;
import com.dongnao.autotest.common.IntegerUtil;
import com.dongnao.autotest.common.JsonUtil;
import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.common.ReflectUtil;
import com.dongnao.autotest.common.StringUtil;
import com.dongnao.autotest.common.TimestampUtil;
import com.dongnao.autotest.common.mail.SmtpHelper;
import com.dongnao.autotest.common.mail.SmtpResult;
import com.dongnao.autotest.service.impl.CaseResultServiceImpl;
import com.dongnao.autotest.service.impl.CaseStepServiceImpl;
import com.dongnao.autotest.service.impl.MailServerServiceImpl;
import com.dongnao.autotest.service.impl.SystemConfigServiceImpl;
import com.dongnao.autotest.web.utils.WebDriverFactory;
import com.dongnao.autotest.web.utils.WebDriverHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;

@RestController
public class ExecController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecController.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String SPLIT = "★";

	@Autowired
	private CaseStepServiceImpl caseStepService;
	@Autowired
	private CaseResultServiceImpl caseResultService;
	@Autowired
	private MailServerServiceImpl mailServerService;
	@Autowired
	private SystemConfigServiceImpl systemConfigService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/exec")
	public Object execCase(HttpServletRequest request, HttpServletResponse response) {
		try {
			int groupId = IntegerUtil.toInteger(request.getParameter("groupId"), 0);
			Map<String, Object> whereMap = new HashMap<>();
			whereMap.put("groupId", groupId);
			List<Map<String, Object>> stepList = caseStepService.findList(whereMap);
			if (null == stepList || stepList.isEmpty())
				return "Failure";

			String userDir = System.getProperty("user.dir", "");
			String path = userDir + "/src/main/resources/chromedriver/2.41/chromedriver.exe";
			// System.setProperty("webdriver.chrome.driver", path);

			ChromeOptions options = new ChromeOptions();
			// options.setBinary("D:/ChromePortable/App/Chrome/chrome.exe");
			// ChromeDriver driver = new ChromeDriver(options);

			List<Integer> caseIdList = stepList.stream().map(m -> MapUtils.getInteger(m, "caseId", 0)).distinct()
					.collect(Collectors.toList());
			String batchNo = UUID.randomUUID().toString();
			int i = 0;
			for (Integer caseId : caseIdList) {
				List<Map<String, Object>> caseStepList = stepList.stream()
						.filter(m -> caseId == MapUtils.getInteger(m, "caseId", 0)).collect(Collectors.toList());

				ChromeDriver driver = (ChromeDriver) WebDriverFactory.getWebDriver(WebDriverFactory.CHROME, path,
						options);
				this.driveCase(driver, caseStepList, batchNo);

				i++;
				if (i < caseIdList.size() - 1) { // 每个用例之间执行间隔固定2秒
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			}
			// 发邮件
			this.sendMail(groupId, batchNo);
			return super.doResult(200, "执行成功");
		} catch (Exception ex) {
			return super.doFailureResult(400, ex.getMessage());
		}
	}

	/**
	 * 驱动用例
	 * 
	 * @param caseStepList
	 * @param batchNo
	 */
	private void driveCase(ChromeDriver driver, List<Map<String, Object>> caseStepList, String batchNo) {
		try {
			long begin = System.currentTimeMillis();
			for (Map<String, Object> itemMap : caseStepList) {
				int groupId = MapUtils.getInteger(itemMap, "groupId", 0);
				int caseId = MapUtils.getInteger(itemMap, "caseId", 0);
				String action = MapUtils.getString(itemMap, "action", "");
				String selector = MapUtils.getString(itemMap, "selector", "");
				String body = MapUtils.getString(itemMap, "body", "");

				WebDriverHelper webDriverHelper = new WebDriverHelper(driver);

				if (!StringUtils.isNullOrEmpty(selector)) { // 说明是有选择器的，第一个参数是给选择器用的
					String[] bodyArray = body.split(SPLIT);

					String selectorMethod = String.format("findBy%s", StringUtil.capitalize(selector));
					Object elementObj = ReflectUtil.invokeMethod(webDriverHelper, selectorMethod, bodyArray[0]);
					Object[] args = new Object[bodyArray.length];
					// Object eleObj = elementObj;
					// if (elementObj instanceof RemoteWebElement) {
					// eleObj = (WebElement) elementObj;
					// } else if (elementObj instanceof List) {
					// eleObj = (List<WebElement>) elementObj;
					// }
					args[0] = elementObj;

					for (int i = 1; i < bodyArray.length; i++) {
						args[i] = bodyArray[i];
					}
					Object returnObj = ReflectUtil.invokeMethod(webDriverHelper, action, args);
					if (returnObj != null && returnObj instanceof Boolean) {
						boolean returnBol = BooleanUtil.toBoolean(returnObj.toString(), false);
						// 插入执行结果
						long end = System.currentTimeMillis();
						saveDb(groupId, caseId, batchNo, returnBol, begin, end);
					}
				} else {
					Object[] args = null;
					if (!StringUtil.isNullOrEmpty(body)) {
						args = body.split(SPLIT);
					}
					Object returnObj = ReflectUtil.invokeMethod(webDriverHelper, action, args);
					if (returnObj != null && returnObj instanceof Boolean) {
						boolean returnBol = BooleanUtil.toBoolean(returnObj.toString(), false);
						// 插入执行结果
						long end = System.currentTimeMillis();
						saveDb(groupId, caseId, batchNo, returnBol, begin, end);
					}
				}
			}
		} catch (Exception ex) {
			LOGGER.error("driveCase error, cause: ", ex);
		}
	}

	/**
	 * 
	 * @param groupId
	 * @param caseId
	 * @param batchNo
	 * @param success
	 */
	private void saveDb(int groupId, int caseId, String batchNo, boolean success, long fromOn, long toOn) {
		Map<String, Object> obj = new HashMap<>();
		obj.put("groupId", groupId);
		obj.put("caseId", caseId);
		obj.put("batchNo", batchNo);
		obj.put("success", success);
		obj.put("fromOn", DateTimeUtil.toString(fromOn, DateTimeUtil.DEFAULT_PATTERN));
		obj.put("toOn", DateTimeUtil.toString(toOn, DateTimeUtil.DEFAULT_PATTERN));
		obj.put("totalMill", (toOn - fromOn));
		obj.put("snapshotUrl", "");
		this.caseResultService.add(obj);
	}

	/**
	 * 发邮件
	 * 
	 * @param groupId
	 * @param batchNo
	 */
	private void sendMail(int groupId, String batchNo) {
		try {
			Map<String, Object> whereMap = new HashMap<>();
			whereMap.put("groupId", groupId);
			whereMap.put("batchNo", batchNo);
			List<Map<String, Object>> list = this.caseResultService.findList(whereMap);
			if (CollectionUtils.isEmpty(list))
				return;

			long sumMill = list.stream().mapToLong(m -> MapUtil.getLong(m, "totalMill", 0L)).sum();
			long succCount = list.stream().filter(v -> MapUtil.getBoolean(v, "success", false)).count();
			long failCount = list.stream().filter(v -> !MapUtil.getBoolean(v, "success", false)).count();
			Timestamp startOn = MapUtil.getDate(list.get(0), "createOn", TimestampUtil.getNow());
			StringBuilder sb = new StringBuilder();
			sb.append("<table style=\"width: 600px;\">");
			sb.append("	<tbody>");
			sb.append("		<tr style=\"background-color: #a3d900\">");
			sb.append("			<td>分组名称</td>");
			sb.append("			<td>执行时长</td>");
			sb.append("			<td>成功</td>");
			sb.append("			<td>失败</td>");
			sb.append("			<td>开始时间</td>");
			sb.append("		</tr>");
			sb.append("		<tr style=\"background-color: #a3d900\">");
			sb.append("			<td>" + MapUtil.getString(list.get(0), "groupName", "") + "</td>");
			sb.append("			<td>" + (sumMill / 1000) + "秒</td>");
			sb.append("			<td>" + succCount + "</td>");
			sb.append("			<td>" + failCount + "</td>");
			sb.append("			<td>" + TimestampUtil.toString(startOn, DateTimeUtil.DEFAULT_PATTERN) + "</td>");
			sb.append("		</tr>");
			sb.append("		<tr style=\"background-color: #c9dd22\">");
			sb.append("			<td>用例Id</td>");
			sb.append("			<td>用例名称</td>");
			sb.append("			<td>执行结果</td>");
			sb.append("			<td>运行时长</td>");
			sb.append("			<td>开始时间</td>");
			sb.append("		</tr>");
			for (Map<String, Object> itemMap : list) {
				long totalMill = MapUtil.getLong(itemMap, "totalMill", 0L);
				Timestamp createOn = MapUtil.getDate(itemMap, "createOn", TimestampUtil.getNow());
				sb.append("	<tr style=\"background-color: #c9dd22\">");
				sb.append("		<td>" + MapUtil.getInteger(itemMap, "caseId", 0) + "</td>");
				sb.append("		<td>" + MapUtil.getString(itemMap, "caseName", "") + "</td>");
				sb.append("		<td>" + (true == MapUtil.getBoolean(itemMap, "success", false) ? "成功" : "失败")
						+ "</td>");
				sb.append("		<td>" + (totalMill / 1000) + "秒</td>");
				sb.append("		<td>" + TimestampUtil.toString(createOn, DateTimeUtil.DEFAULT_PATTERN) + "</td>");
				sb.append("	</tr>");
			}
			sb.append("	</tbody>");
			sb.append("</table>");

			whereMap.clear();
			whereMap.put("default", true);
			List<Map<String, Object>> mailServerList = this.mailServerService.findList(whereMap);
			if (CollectionUtils.isEmpty(mailServerList))
				return;

			String host = MapUtil.getString(mailServerList.get(0), "host", "");
			int port = MapUtil.getInteger(mailServerList.get(0), "port", 25);
			String userName = MapUtil.getString(mailServerList.get(0), "userName", "");
			String password = MapUtil.getString(mailServerList.get(0), "password", "");
			String from = MapUtil.getString(mailServerList.get(0), "from", "");
			String fromName = MapUtil.getString(mailServerList.get(0), "fromName", "");
			boolean ssl = MapUtil.getBoolean(mailServerList.get(0), "ssl", false);

			Map<String, Object> configMap = this.systemConfigService.findById(1);
			String subject = MapUtil.getString(configMap, "mailSubject", "");
			String receiver = MapUtil.getString(configMap, "receiver", "");
			List<Map> revList = JsonUtil.toListUseJackson(receiver, Map.class);

			Map<String, String> toMap = new HashMap<>();
			for (Map<String, Object> itemMap : revList) {
				toMap.put(MapUtil.getString(itemMap, "mail", ""), MapUtil.getString(itemMap, "name", ""));
			}
			// toMap.put("mrluo735@126.com", "mrluo735");
			// toMap.put("1042381863@qq.com", "加肥猫");
			// toMap.put("1020284212@qq.com", "美丽的太阳花");

			SmtpHelper smtpHelper = new SmtpHelper(host, port, userName, password, from, fromName);
			smtpHelper.setEnableSsl(ssl);
			smtpHelper.connect();
			if (smtpHelper.isConnected()) {
				SmtpResult smtpResult = smtpHelper.send(subject, sb.toString(), toMap);
			}
		} catch (Exception ex) {

		}
	}
}
