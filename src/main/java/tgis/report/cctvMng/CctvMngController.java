package tgis.report.cctvMng;

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
import sun.misc.BASE64Decoder;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.EgovExcel;
import tgis.common.util.com.FileUtil;
import tgis.report.cctvMng.service.CctvMngService;
import tgis.user.auth.service.ResourceVO;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;


@Controller
public class CctvMngController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CctvMngController.class);
	
	@Resource(name = "cctvMngService")
	private CctvMngService cctvMngService;

	// 관리대장 > 교통정보수집용 CCTV 관리카드 리스트 페이지 이동
	@AuthHandler(handler="M006001009")

	@RequestMapping(value = "/report/cctvMng/selectInfoList.do")
	public String selectInfoList() throws Exception {
		return "report/cctvMng/selectInfoList";
	}

	// 관리대장 > 교통정보수집용 CCTV 관리카드 리스트 조회
	@AuthHandler(handler="M006001009")
	@RequestMapping(value = "/report/cctvMng/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = cctvMngService.selectInfoList(params);

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
	@AuthHandler(handler="M006001009")
	@RequestMapping(value = "/report/cctvMng/selectInfoMap.do")
	public String selectInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = cctvMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0) {
			List<?> fileList = cctvMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001009");

		return "report/cctvMng/selectInfoMap";
	}

	// 등록 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001009")
	@RequestMapping(value = "/report/cctvMng/insertInfoMap.do")
	public String insertInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001009");

		return "report/cctvMng/insertInfoMap";
	}

	// 수정 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001009")
	@RequestMapping(value = "/report/cctvMng/updateInfoMap.do")
	public String updateInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = cctvMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = cctvMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001009");

		return "report/cctvMng/updateInfoMap";
	}

	// 수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001009")
	@RequestMapping(value = "/report/cctvMng/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("esbCpy", XssPreventer.escape(params.get("esbCpy").toString()));
				params.put("ctkMgrnu", XssPreventer.escape(params.get("ctkMgrnu").toString()));
				params.put("sprtMgrnu", XssPreventer.escape(params.get("sprtMgrnu").toString()));
				params.put("asnMgrnu", XssPreventer.escape(params.get("asnMgrnu").toString()));
				params.put("mkMdl", XssPreventer.escape(params.get("mkMdl").toString()));
				params.put("mkCpy", XssPreventer.escape(params.get("mkCpy").toString()));
				params.put("esbHit", XssPreventer.escape(params.get("esbHit").toString()));
				params.put("cnnSite", XssPreventer.escape(params.get("cnnSite").toString()));
				params.put("cnnMain", XssPreventer.escape(params.get("cnnMain").toString()));
				params.put("sgr", XssPreventer.escape(params.get("sgr").toString()));

				cctvMngService.updateInfo(params, request);
			} else {
				cctvMngService.deleteInfo(params);
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
	 * 관리대장 > 교통정보수집용 CCTC 관리카드 리스트 엑셀 다운로드
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001009")
	@RequestMapping(value = "/report/cctvMng/excelListDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 리스트조회
		List<?> resultList = cctvMngService.selectExcelList(params);

		String[] arrHeader = {"번호","CCTV 관리번호","교차로명","재원","통신방식(현장)","통신방식(센터)","특이사항"};

		params.put("dataList", resultList);
		params.put("arrHeader", arrHeader);
		params.put("sheetName", "CCTV관리대장");
		params.put("fileName", "CCTV관리대장");

		EgovExcel.buildExcelDocument(params, workbook, request, response);
	}

	/**
	 * 관리대장 > 교통정보수집용 CCTV 관리카드 > 관리대장 엑셀 다운로드(지도 이미지 임시 저장)
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/report/cctvMng/imgUpload", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap imgUpload(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		String seq = params.get("seq").toString();

		String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
		FileUtil.mkDir(serverPath);

		String imageString = params.get("base64String").toString().split(",")[1];

		byte[] imageByte;
		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		BufferedImage image = ImageIO.read(bis);
		bis.close();

		if("1".equals(seq)) {
			File outputfile = new File(serverPath + "exImg_" + params.get("mgrnu") + "_1.png");

			ImageIO.write(image, "png", outputfile);
		} else {
			File outputfile = new File(serverPath + "exImg_" + params.get("mgrnu") + "_2.png");

			ImageIO.write(image, "png", outputfile);
		}

		model.addAttribute("jsonView", jsonMap);

		return model;
	}

	/**
	 * 관리대장 > 교통정보수집용 CCTV 관리카드 > 관리대장 엑셀 다운로드
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "report/cctvMng/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			EgovMap cctvMngInfo = cctvMngService.selectInfo(params);

			String excelPath = request.getSession().getServletContext().getRealPath("/resource/report/");
			FileInputStream file = new FileInputStream(new File(excelPath + "CCTV관리대장.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);

			// 엑셀에 데이터 입력

			String cssNam = String.valueOf(cctvMngInfo.get("cssNam"));
			if("null".equals(cssNam)) {
				cssNam = "";
			}
			String esbHit = String.valueOf(cctvMngInfo.get("esbHit"));
			if("null".equals(esbHit)) {
				esbHit = "";
			}
			String esbCpy = String.valueOf(cctvMngInfo.get("esbCpy"));
			if("null".equals(esbCpy)) {
				esbCpy = "";
			}
			String esbYmd = String.valueOf(cctvMngInfo.get("esbYmd"));
			if("null".equals(esbYmd)) {
				esbYmd = "";
			}
			String cnnSite = String.valueOf(cctvMngInfo.get("cnnSite"));
			if("null".equals(cnnSite)) {
				cnnSite = "";
			}
			String cnnMain = String.valueOf(cctvMngInfo.get("cnnMain"));
			if("null".equals(cnnMain)) {
				cnnMain = "";
			}
			String mgeChung = String.valueOf(cctvMngInfo.get("mgeChung"));
			if("null".equals(mgeChung)) {
				mgeChung = "";
			}
			String sgr = String.valueOf(cctvMngInfo.get("sgr"));
			if("null".equals(sgr)) {
				sgr = "";
			}

			EgovExcel.writeExcelValue(sheet, 2, 0, cssNam);			// 교차로명
			EgovExcel.writeExcelValue(sheet, 9, 2, esbHit + " " + esbCpy + " " + esbYmd);		// CCTV 재원 (설치높이, 설치회사, 설치일자)
			EgovExcel.writeExcelValue(sheet, 10, 2, cnnSite);		// 통신방식 (현장)
			EgovExcel.writeExcelValue(sheet, 11, 2, cnnMain);		// 통신방식 (센터)
			EgovExcel.writeExcelValue(sheet, 12, 2, mgeChung);		// 관리청
			EgovExcel.writeExcelValue(sheet, 13, 2, sgr);			// 특이사항

			// 첨부파일 이미지 넣기
			List<?> atchFileList = cctvMngService.selectFileInfo(params);

			String atchFilePath = EgovProperties.getProperty("Globals.fileStorePath") + "GT_A069_P/";
			String atchFileNam = "";

			for(int i=atchFileList.size()-1; i>=atchFileList.size()-2; i--) {
				EgovMap atchFileInfo = (EgovMap)atchFileList.get(i);

				atchFileNam = atchFilePath + atchFileInfo.get("fileNam");

				File outputfile = new File(atchFileNam);

				if(outputfile.isFile()) {
					if(i == atchFileList.size()-1) {
						EgovExcel.SetExcelImage(wb,sheet,atchFileNam,700,255,0,4,3,5);			// 사진 이미지(처음 등록한 사진)
					} else {
						EgovExcel.SetExcelImage(wb,sheet,atchFileNam,700,255,4,4,7,5);			// 사진 이미지(두번째 등록한 사진)
					}
				}
			}

			String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
			String fileNam = serverPath + "exImg_" + cctvMngInfo.get("mgrnu") + "_1.png"; // 첫번째 지도 이미지
			String fileNam2 = serverPath + "exImg_" + cctvMngInfo.get("mgrnu") + "_2.png"; // 두번째 지도 이미지

			// 엑셀에 지도 이미지 넣기
			File outputfile = new File(fileNam);
			if(outputfile.isFile()) {
				EgovExcel.SetExcelImage(wb,sheet,fileNam,700,255,0,7,3,8);		// 지도 이미지
				FileUtil.rmFile(fileNam);
			}

			File outputfile2 = new File(fileNam2);
			if(outputfile2.isFile()) {
				EgovExcel.SetExcelImage(wb,sheet,fileNam2,700,255,4,7,7,8);		// 지도 이미지
				FileUtil.rmFile(fileNam2);
			}

			file.close();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("CCTV관리대장", "UTF-8") + "(" + cctvMngInfo.get("mgrnu") + ")_" + ComDateUtils.getCurDateTime() + ".xls" + ";");
			ServletOutputStream myOut = response.getOutputStream();
			wb.write(myOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
