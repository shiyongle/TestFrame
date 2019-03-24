package com.dongnao.autotest.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.dongnao.autotest.common.ByteUtil;
import com.dongnao.autotest.common.IntegerUtil;
import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.common.Pager;
import com.dongnao.autotest.common.StringUtil;
import com.dongnao.autotest.service.impl.CaseCaseServiceImpl;
import com.dongnao.autotest.service.impl.CaseGroupServiceImpl;
import com.dongnao.autotest.service.impl.CaseResultServiceImpl;
import com.dongnao.autotest.service.impl.CaseStepServiceImpl;

/**
 * 用例控制器
 * 
 * @author easy
 *
 */
@RestController
@RequestMapping("/case")
public class CaseController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CaseController.class);

	@Autowired
	private CaseGroupServiceImpl caseGroupService;
	@Autowired
	private CaseCaseServiceImpl caseCaseService;
	@Autowired
	private CaseStepServiceImpl caseStepService;
	@Autowired
	private CaseResultServiceImpl caseResultService;

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 */
	@PostMapping("/upload")
	@Transactional(rollbackFor = Exception.class)
	public Object upload(HttpServletRequest request, HttpServletResponse response) {
		// TODO 1.上传文件; 2.解析文件; 3.入库
		try {
			String root = System.getProperty("user.dir", "");
			// System.out.println(CaseController.class.getClassLoader().getResource("文件名/文件夹"));
			String filePath = root + "/src/main/resources/static/upload/" + UUID.randomUUID().toString() + ".xlsx";

			// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if (!multipartResolver.isMultipart(request)) {
				// jsonResult.put("success", false);
				// jsonResult.put("code",
				// -HttpStatus.FAILED_DEPENDENCY.value());
				// jsonResult.put("message", "上传请求中不包含multipart/form-data");
				// return jsonResult;
			}
			// 将request变成MultipartHttpServletRequest
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// List<MultipartFile> files = multiRequest.getFiles("file");
			// 获取multiRequest 中所有的文件名
			Iterator<String> iterator = multiRequest.getFileNames();
			while (iterator.hasNext()) {
				String fileName = iterator.next();
				MultipartFile file = multiRequest.getFile(fileName);
				if (file.isEmpty())
					continue;
				// bytes = file.getBytes();
				// originalName = file.getOriginalFilename();
				file.transferTo(new File(filePath));
				break;
			}

			byte type = ByteUtil.toByte(request.getParameter("type"), (byte) 1);
			// 导入
			List<Map<String, Object>> caseList = parseTemplate(filePath);
			if (CollectionUtils.isEmpty(caseList))
				return doFailureResult(400, "解析上传的模板出错");

			// 插入数据库
			boolean retVal = this.saveDb(type, caseList);
			if (!retVal)
				return doFailureResult(400, "上传失败");
			return doResult(200, "上传成功");
		} catch (Exception ex) {
			LOGGER.error("上传失败！失败原因：" + ex);
			return doFailureResult(400, "上传失败");
		}
	}

	/**
	 * 解析模板
	 * 
	 * @param filePath
	 */
	private List<Map<String, Object>> parseTemplate(String filePath) {
		List<Map<String, Object>> caseList = new ArrayList<>();
		try {
			// 同时支持Excel 2003、2007
			File excelFile = new File(filePath); // 创建文件对象
			if (!excelFile.exists())
				return caseList;

			FileInputStream in = new FileInputStream(excelFile); // 文件流
			Workbook workbook = null;
			if (excelFile.getName().endsWith(".xls")) { // Excel 2003
				workbook = new HSSFWorkbook(in);
			} else if (excelFile.getName().endsWith(".xlsx")) {
				// Excel 2007/2010
				workbook = new XSSFWorkbook(in);
			}
			// Workbook workbook = WorkbookFactory.create(is); // 这种方式
			// Excel2003/2007/2010都是可以处理的

			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			/**
			 * 设置当前excel中sheet的下标：0开始
			 */
			Sheet sheet = workbook.getSheetAt(0); // 遍历第一个Sheet
			// Sheet sheet = workbook.getSheetAt(2); // 遍历第三个Sheet

			// 获取总行数
			// System.out.println(sheet.getLastRowNum());

			String groupName = sheet.getRow(1).getCell(0).getStringCellValue();
			if (StringUtils.isEmpty(groupName)) {
				System.out.println("分组名称不能为空");
			}
			String groupUrl = sheet.getRow(1).getCell(1).getStringCellValue();

			String lastCaseName = "";
			// 为跳过第一行目录设置count
			int rowIndex = 0;
			for (Row row : sheet) {
				// 跳过第一行的目录
				if (rowIndex < 1) {
					rowIndex++;
					continue;
				}
				if ("END".equalsIgnoreCase(row.getCell(0).getStringCellValue()))
					break;

				Map<String, Object> caseMap = new HashMap<>();
				// 获取总列数(空格的不计算)
				int columnTotalNum = row.getPhysicalNumberOfCells();
				// System.out.println("总列数：" + columnTotalNum);
				// System.out.println("最大列数：" + row.getLastCellNum());

				String caseName = row.getCell(2).getStringCellValue();
				if (!StringUtils.isEmpty(caseName)) {
					lastCaseName = caseName;
				}

				String stepName = row.getCell(3).getStringCellValue();
				String stepAction = row.getCell(4).getStringCellValue();
				String stepUrl = row.getCell(5).getStringCellValue();
				String stepSelector = row.getCell(6).getStringCellValue();
				String stepHeader = row.getCell(7).getStringCellValue();
				String stepContentType = row.getCell(8).getStringCellValue();
				String stepBody = row.getCell(9).getStringCellValue();
				String stepCookies = row.getCell(10).getStringCellValue();

				caseMap.put("group", groupName);
				caseMap.put("groupUrl", groupUrl);
				caseMap.put("caseName", lastCaseName);
				caseMap.put("stepName", stepName);
				caseMap.put("stepAction", stepAction);
				caseMap.put("stepUrl", stepUrl);
				caseMap.put("stepSelector", stepSelector);
				caseMap.put("stepHeader", stepHeader);
				caseMap.put("stepContentType", stepContentType);
				caseMap.put("stepBody", stepBody);
				caseMap.put("stepCookies", stepCookies);
				caseList.add(caseMap);

				// for循环的，不扫描空格的列
				// for (Cell cell : row) {
				// System.out.println(cell);
				// }
			}

			return caseList;
		} catch (Exception e) {
			LOGGER.error("importInDb error, cause: ", e);
			return caseList;
		}
	}

	/**
	 * 保存至数据库
	 * 
	 * @param type
	 * @param caseList
	 * @return
	 */
	private boolean saveDb(byte type, List<Map<String, Object>> caseList) {
		try {
			// 1. 插入组
			String group = MapUtils.getString(caseList.get(0), "group", "");
			String groupUrl = MapUtils.getString(caseList.get(0), "groupUrl", "");
			Map<String, Object> groupMap = new HashMap<>();
			groupMap.put("name", group);
			groupMap.put("type", type);
			groupMap.put("url", groupUrl);
			int groupId = caseGroupService.add(groupMap);
			// 2. 插入用例
			List<String> caseNameList = caseList.stream().map(m -> MapUtils.getString(m, "caseName", "")).distinct()
					.collect(Collectors.toList());
			for (int i = 0; i < caseNameList.size(); i++) {
				String caseName = caseNameList.get(i);
				Map<String, Object> caseMap = new HashMap<>();
				caseMap.put("groupId", groupId);
				caseMap.put("name", caseName);
				caseMap.put("ordinal", i + 1);
				int caseId = caseCaseService.add(caseMap);
				int stepOrdinal = 1;
				for (int j = 0; j < caseList.size(); j++) {
					if (!caseName.equals(MapUtils.getString(caseList.get(j), "caseName", "")))
						continue;
					Map<String, Object> stepMap = new HashMap<>();
					stepMap.put("groupId", groupId);
					stepMap.put("caseId", caseId);
					stepMap.put("name", MapUtils.getString(caseList.get(j), "stepName", ""));
					stepMap.put("action", MapUtils.getString(caseList.get(j), "stepAction", ""));
					stepMap.put("url", MapUtils.getString(caseList.get(j), "stepUrl", ""));
					stepMap.put("selector", MapUtils.getString(caseList.get(j), "stepSelector", ""));
					stepMap.put("header", MapUtils.getString(caseList.get(j), "stepHeader", ""));
					stepMap.put("contentType", MapUtils.getString(caseList.get(j), "stepContentType", ""));
					stepMap.put("body", MapUtils.getString(caseList.get(j), "stepBody", ""));
					stepMap.put("cookies", MapUtils.getString(caseList.get(j), "stepCookies", ""));
					stepMap.put("ordinal", stepOrdinal);
					int stepId = caseStepService.add(stepMap);
					stepOrdinal++;
				}
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 保存分组信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/group")
	public Object saveGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			Map<String, Object> objMap = new HashMap<>();
			objMap.put("name", request.getParameter("name"));
			objMap.put("type", IntegerUtil.toInteger(request.getParameter("type"), 1));
			objMap.put("url", request.getParameter("url"));
			objMap.put("remark", request.getParameter("remark"));
			if (id < 1) {
				this.caseGroupService.add(objMap);
			} else {
				objMap.put("id", id);
				this.caseGroupService.update(objMap);
			}
			return doResult(200, "保存成功");
		} catch (Exception ex) {
			return doFailureResult(400, "保存失败");
		}
	}

	/**
	 * 删除分组信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping("/group")
	public Object deleteGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			int rowCount = this.caseGroupService.delete(id);
			switch (rowCount) {
			case -1:
				return doFailureResult(4001, "删除失败,该分组下有用例数据");
			case 0:
				return doFailureResult(4000, "删除失败,没有可删除的数据");
			default:
				return doResult(200, "删除成功");
			}
		} catch (Exception ex) {
			return doFailureResult(500, "删除失败");
		}
	}

	/**
	 * 分页查找分组信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/groups/paged")
	public Object findGroupPaged(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> pagerMap = super.getPagerMap(request);
		Map<Long, List<Map<String, Object>>> dataMap = this.caseGroupService.findPaged(pagerMap);
		return super.doPagerResult(dataMap);
	}

	/**
	 * 查找分组信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/groups")
	public Object findGroup(HttpServletRequest request, HttpServletResponse response) {
		return this.caseGroupService.findAll();
	}

	/**
	 * 保存用例信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/case")
	public Object saveCase(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			Map<String, Object> objMap = new HashMap<>();
			objMap.put("groupId", IntegerUtil.toInteger(request.getParameter("groupId"), 0));
			objMap.put("name", request.getParameter("name"));
			objMap.put("ordinal", IntegerUtil.toInteger(request.getParameter("ordinal"), 1));
			objMap.put("remark", request.getParameter("remark"));
			if (id < 1) {
				this.caseCaseService.add(objMap);
			} else {
				objMap.put("id", id);
				this.caseCaseService.update(objMap);
			}
			return doResult(200, "保存成功");
		} catch (Exception ex) {
			return doFailureResult(400, "保存失败");
		}
	}

	/**
	 * 删除用例信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping("/case")
	public Object deleteCase(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			this.caseCaseService.delete(id);
			return doResult(200, "删除成功");
		} catch (Exception ex) {
			return doFailureResult(400, "删除失败");
		}
	}

	/**
	 * 分页查找用例信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/cases/paged")
	public Object findCasePaged(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> pagerMap = super.getPagerMap(request);
		Map<Long, List<Map<String, Object>>> dataMap = this.caseCaseService.findPaged(pagerMap);
		return super.doPagerResult(dataMap);
	}

	/**
	 * 保存步骤信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/step")
	public Object saveStep(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			Map<String, Object> objMap = new HashMap<>();
			objMap.put("groupId", IntegerUtil.toInteger(request.getParameter("groupId"), 0));
			objMap.put("caseId", IntegerUtil.toInteger(request.getParameter("caseId"), 0));
			objMap.put("name", request.getParameter("name"));
			objMap.put("action", request.getParameter("action"));
			objMap.put("url", request.getParameter("url"));
			objMap.put("selector", request.getParameter("selector"));
			objMap.put("header", request.getParameter("header"));
			objMap.put("contentType", request.getParameter("contentType"));
			objMap.put("body", request.getParameter("body"));
			objMap.put("cookies", request.getParameter("cookies"));
			objMap.put("ordinal", IntegerUtil.toInteger(request.getParameter("ordinal"), 1));
			objMap.put("remark", request.getParameter("remark"));
			if (id < 1) {
				this.caseStepService.add(objMap);
			} else {
				objMap.put("id", id);
				this.caseStepService.update(objMap);
			}
			return doResult(200, "保存成功");
		} catch (Exception ex) {
			return doFailureResult(400, "保存失败");
		}
	}

	/**
	 * 删除步骤信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping("/step")
	public Object deleteStep(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			this.caseStepService.delete(id);
			return doResult(200, "删除成功");
		} catch (Exception ex) {
			return doFailureResult(400, "删除失败");
		}
	}

	/**
	 * 分页查找步骤信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/steps/paged")
	public Object findStepPaged(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> pagerMap = super.getPagerMap(request);
		Map<Long, List<Map<String, Object>>> dataMap = this.caseStepService.findPaged(pagerMap);
		return super.doPagerResult(dataMap);
	}

	/**
	 * 分页查找用例执行结果信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/results/paged")
	public Object findCaseResultPaged(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> pagerMap = super.getPagerMap(request);
		Map<Long, List<Map<String, Object>>> dataMap = this.caseResultService.findPaged(pagerMap);
		return super.doPagerResult(dataMap);
	}

	/**
	 * 分页查找用例执行结果信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/result/count")
	public Object countSuccess(HttpServletRequest request, HttpServletResponse response) {
		String caseId = request.getParameter("caseId");
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put("caseId", caseId);
		List<Map<String, Object>> dataList = this.caseResultService.countSuccess(whereMap);
		// 转换成图表所需要的数据格式
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String, Object> itemMap : dataList) {
			boolean success = MapUtil.getBoolean(itemMap, "success", true);
			int count = MapUtil.getInteger(itemMap, "count", 0);
			
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("name", success ? "成功" : "失败");
			dataMap.put("value", count);
			list.add(dataMap);
		}
		return doResult(200, "OK", list);
	}
}
