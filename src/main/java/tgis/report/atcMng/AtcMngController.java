package tgis.report.atcMng;

import com.nhncorp.lucy.security.xss.XssPreventer;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.EgovExcel;
import tgis.common.util.com.FileUtil;
import tgis.report.atcMng.service.AtcMngService;
import tgis.user.auth.service.ResourceVO;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;


@Controller
public class AtcMngController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AtcMngController.class);
	
	@Resource(name = "atcMngService")
	private AtcMngService atcMngService;

	// 관리대장 > 물품관리대장 리스트 페이지 이동
	@AuthHandler(handler="M006001012")

	@RequestMapping(value = "/report/atcMng/selectInfoList.do")
	public String selectInfoList() throws Exception {
		return "report/atcMng/selectInfoList";
	}

	// 관리대장 > 물품관리대장 리스트 조회
	@AuthHandler(handler="M006001012")
	@RequestMapping(value = "/report/atcMng/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = atcMngService.selectInfoList(params);

		double totalPage = 0.0;
		Long totalRows = 0L;

		if (resultList.size() > 0) {
			totalRows = Long.valueOf(((EgovMap) resultList.get(0)).get("totalrows").toString());
			totalPage = Math.ceil(totalRows / rows) + 1;
		}

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("records", totalRows);	// 총 개수
		jsonMap.put("total", totalPage);	// 총 페이지 수
		jsonMap.put("page", page);			// 현재 페이지
		jsonMap.put("root", resultList);	// 리스트

		model.addAttribute("jsonView", jsonMap);	// JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	// 상세보기창 이동
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001012")
	@RequestMapping(value = "/report/atcMng/selectInfoMap.do")
	public String selectInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = atcMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0) {
			List<?> fileList = atcMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001012");

		return "report/atcMng/selectInfoMap";
	}

	// 등록 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001012")
	@RequestMapping(value = "/report/atcMng/insertInfoMap.do")
	public String insertInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001012");

		return "report/atcMng/insertInfoMap";
	}

	// 수정 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001012")
	@RequestMapping(value = "/report/atcMng/updateInfoMap.do")
	public String updateInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = atcMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = atcMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001012");

		return "report/atcMng/updateInfoMap";
	}

	// 수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001012")
	@RequestMapping(value = "/report/atcMng/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("insert".equals(method)) {
				params.put("atcGroup", XssPreventer.escape(params.get("atcGroup").toString()));
				params.put("atc", XssPreventer.escape(params.get("atc").toString()));
				params.put("std", XssPreventer.escape(params.get("std").toString()));
				params.put("stdNum", XssPreventer.escape(params.get("stdNum").toString()));
				params.put("mkCpy", XssPreventer.escape(params.get("mkCpy").toString()));
				params.put("mkYmd", XssPreventer.escape(params.get("mkYmd").toString()));
				params.put("cptYmd", XssPreventer.escape(params.get("cptYmd").toString()));
				params.put("delYmd", XssPreventer.escape(params.get("delYmd").toString()));
				params.put("loca", XssPreventer.escape(params.get("loca").toString()));
				params.put("uses", XssPreventer.escape(params.get("uses").toString()));
				params.put("cfgCpy", XssPreventer.escape(params.get("cfgCpy").toString()));
				params.put("bidNm", XssPreventer.escape(params.get("bidNm").toString()));
				params.put("mtCode", XssPreventer.escape(params.get("mtCode").toString()));
				params.put("etcDesc", XssPreventer.escape(params.get("etcDesc").toString()));

				atcMngService.insertInfo(params, request);
			} else if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("atcGroup", XssPreventer.escape(params.get("atcGroup").toString()));
				params.put("atc", XssPreventer.escape(params.get("atc").toString()));
				params.put("std", XssPreventer.escape(params.get("std").toString()));
				params.put("stdNum", XssPreventer.escape(params.get("stdNum").toString()));
				params.put("mkCpy", XssPreventer.escape(params.get("mkCpy").toString()));
				params.put("mkYmd", XssPreventer.escape(params.get("mkYmd").toString()));
				params.put("cptYmd", XssPreventer.escape(params.get("cptYmd").toString()));
				params.put("delYmd", XssPreventer.escape(params.get("delYmd").toString()));
				params.put("loca", XssPreventer.escape(params.get("loca").toString()));
				params.put("uses", XssPreventer.escape(params.get("uses").toString()));
				params.put("cfgCpy", XssPreventer.escape(params.get("cfgCpy").toString()));
				params.put("bidNm", XssPreventer.escape(params.get("bidNm").toString()));
				params.put("mtCode", XssPreventer.escape(params.get("mtCode").toString()));
				params.put("etcDesc", XssPreventer.escape(params.get("etcDesc").toString()));

				atcMngService.updateInfo(params, request);
			} else {
				atcMngService.deleteInfo(params);
			}
			jsonMap.put("respFlag", "Y");

		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리대장 > 물품관리대장 > 관리대장 엑셀 다운로드
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/report/atcMng/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			EgovMap atcMngInfo = atcMngService.selectInfo(params);

			String excelPath = request.getSession().getServletContext().getRealPath("/resource/report/");
			FileInputStream file = new FileInputStream(new File(excelPath + "물품관리대장카드.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);

			// 엑셀에 데이터 입력
			EgovExcel.writeExcelValue(sheet, 1, 9, atcMngInfo.get("atcGroup"));		// 품목
			EgovExcel.writeExcelValue(sheet, 2, 9, atcMngInfo.get("atc"));			// 제품명
			EgovExcel.writeExcelValue(sheet, 3, 9, atcMngInfo.get("std"));			// 규격 및 모델명
			EgovExcel.writeExcelValue(sheet, 4, 9, atcMngInfo.get("mkCpy"));		// 제조사
			EgovExcel.writeExcelValue(sheet, 5, 9, atcMngInfo.get("mkYmd"));		// 제조일
			EgovExcel.writeExcelValue(sheet, 6, 9, atcMngInfo.get("stdNum"));		// 제조 및 시리얼 No
			EgovExcel.writeExcelValue(sheet, 7, 9, atcMngInfo.get("cptYmd"));		// 준공일(설치일)
			EgovExcel.writeExcelValue(sheet, 8, 9, atcMngInfo.get("loca"));			// 설치위치
			EgovExcel.writeExcelValue(sheet, 9, 9, atcMngInfo.get("uses"));			// 사용용도
			EgovExcel.writeExcelValue(sheet, 10, 9, atcMngInfo.get("cfgCpy"));		// 설치업체
			EgovExcel.writeExcelValue(sheet, 11, 9, atcMngInfo.get("bidNm"));		// 공사명
			EgovExcel.writeExcelValue(sheet, 12, 9, atcMngInfo.get("mtCode"));		// 관할청
			EgovExcel.writeExcelValue(sheet, 13, 2, atcMngInfo.get("etcDesc"));		// 특이사항

			String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "ATC/";
			String fileNam = serverPath + atcMngInfo.get("streFileNm");

			File outputfile = new File(fileNam);
			if(outputfile.isFile()) {
				EgovExcel.SetExcelImage(wb,sheet,fileNam,900,200,0,1,6,12);			// 사진 이미지
			}

			file.close();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("물품관리대장", "UTF-8") + "(" + atcMngInfo.get("gvmAtcNo") + ")_" + ComDateUtils.getCurDateTime() + ".xls" + ";");
			ServletOutputStream myOut = response.getOutputStream();
			wb.write(myOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 관리대장 > 물품관리대장 리스트 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001012")
	@RequestMapping(value = "/report/atcMng/excelListDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 리스트조회
		List<?> resultList = atcMngService.selectExcelList(params);

		String[] arrHeader = {"번호","품목","제품명","규격 및 모델명","제조사","제조일","제품 및 시리얼 No","준공일(설치일)","철거일","설치위치","사용용도","설치업체","공사명","관할청","기타"};

		params.put("dataList", resultList);
		params.put("arrHeader", arrHeader);
		params.put("sheetName", "물품관리대장");
		params.put("fileName", "물품관리대장");

		EgovExcel.buildExcelDocument(params, workbook, request, response);
	}

}
