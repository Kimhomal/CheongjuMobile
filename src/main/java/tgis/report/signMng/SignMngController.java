package tgis.report.signMng;

import com.nhncorp.lucy.security.xss.XssPreventer;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.EgovExcel;
import tgis.common.util.com.EgovStringUtil;
import tgis.common.util.com.FileUtil;
import tgis.report.signMng.service.SignMngService;
import tgis.user.auth.service.ResourceVO;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SignMngController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SignMngController.class);
	
	@Resource(name = "signMngService")
	private SignMngService signMngService;

	// 관리대장 > 표지관리대장 리스트 페이지 이동
	@AuthHandler(handler="M006001004")
	@RequestMapping(value = "/report/signMng/selectInfoList.do")
	public String selectInfoList() throws Exception {
		return "report/signMng/selectInfoList";
	}

	// 관리대장 > 표지관리대장 리스트 조회
	@AuthHandler(handler="M006001004")
	@RequestMapping(value = "/report/signMng/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = signMngService.selectInfoList(params);

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
	@AuthHandler(handler="M006001004")
	@RequestMapping(value = "/report/signMng/selectInfoMap.do")
	public String selectInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = signMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = signMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001004");

		return "report/signMng/selectInfoMap";
	}

	// 수정 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001004")
	@RequestMapping(value = "/report/signMng/updateInfoMap.do")
	public String updateInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = signMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = signMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001004");

		return "report/signMng/updateInfoMap";
	}

	// 수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001004")
	@RequestMapping(value = "/report/signMng/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("ctkMgrnu", XssPreventer.escape(params.get("ctkMgrnu").toString()));
				params.put("mrkCde", XssPreventer.escape(params.get("mrkCde").toString()));
				params.put("ntc", XssPreventer.escape(params.get("ntc").toString()));

				signMngService.updateInfo(params, request);
			} else {
				signMngService.deleteInfo(params);
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
	 * 관리대장 > 표지관리대장 > 관리대장 엑셀 다운로드(지도 이미지 임시 저장)
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/report/signMng/imgUpload", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap imgUpload(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
		FileUtil.mkDir(serverPath);

		String imageString = params.get("base64String").toString().split(",")[1];

		byte[] imageByte;
		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		BufferedImage image = ImageIO.read(bis);
		bis.close();

		File outputfile = new File(serverPath + "exImg_" + params.get("mgrnu") + ".png");

		ImageIO.write(image, "png", outputfile);

		jsonMap.put("filePath", serverPath);
		jsonMap.put("fileName", params.get("mgrnu") + ".png");

		model.addAttribute("jsonView", jsonMap);

		return model;
	}

	/**
	 * 관리대장 > 표지관리대장 > 관리대장 엑셀 다운로드
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "report/signMng/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			EgovMap signMngInfo = signMngService.selectInfo(params);

			String excelPath = request.getSession().getServletContext().getRealPath("/resource/report/");
			FileInputStream file = new FileInputStream(new File(excelPath + "표지관리대장.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);

			// 전면
			// 엑셀에 데이터 입력
			EgovExcel.writeExcelValue(sheet, 4, 2, signMngInfo.get("peNam"));		// 경찰서
			EgovExcel.writeExcelValue(sheet, 3, 10, signMngInfo.get("guNam") + " " + signMngInfo.get("dongNam") + " " + signMngInfo.get("jibun"));		// 설치위치

			if(!("null").equals(String.valueOf(signMngInfo.get("esbYmd")))) {
				EgovExcel.writeExcelValue(sheet, 4, 14, signMngInfo.get("esbYmd").toString().substring(0,4));		// 설치 년 월 일 (년)
				EgovExcel.writeExcelValue(sheet, 4, 19, signMngInfo.get("esbYmd").toString().substring(5,7));		// 설치 년 월 일 (월)
				EgovExcel.writeExcelValue(sheet, 4, 22, signMngInfo.get("esbYmd").toString().substring(8,10));		// 설치 년 월 일 (일)
			}
			EgovExcel.writeExcelValue(sheet, 10, 2, signMngInfo.get("sprtMgrnu"));	// 지주번호
			EgovExcel.writeExcelValue(sheet, 10, 8, signMngInfo.get("lgrType"));	// 대장타입
			EgovExcel.writeExcelValue(sheet, 10, 11, signMngInfo.get("lgrGbn"));	// 대장구분
			EgovExcel.writeExcelValue(sheet, 10, 13, signMngInfo.get("mrkKnd"));	// 표지종류
			EgovExcel.writeExcelValue(sheet, 10, 15, signMngInfo.get("mrkCdeWe"));	// 표지번호
			EgovExcel.writeExcelValue(sheet, 10, 24, signMngInfo.get("sptMrk"));	// 보조표지
			EgovExcel.writeExcelValue(sheet, 14, 9, signMngInfo.get("std"));		// 규격
			EgovExcel.writeExcelValue(sheet, 19, 13, signMngInfo.get("sprtKnd"));	// 종류
			EgovExcel.writeExcelValue(sheet, 17, 24, signMngInfo.get("rodKnd"));	// 도로종류
			EgovExcel.writeExcelValue(sheet, 17, 9, signMngInfo.get("mor"));		// 반사재
			EgovExcel.writeExcelValue(sheet, 20, 9, signMngInfo.get("mop"));		// 재질(표지판)
			EgovExcel.writeExcelValue(sheet, 18, 17, signMngInfo.get("rodMgrnu"));	// 도로번호
			EgovExcel.writeExcelValue(sheet, 22, 13, signMngInfo.get("sprtMop"));	// 재질
			EgovExcel.writeExcelValue(sheet, 25, 9, signMngInfo.get("esbNam"));		// 업자명(표지판)
			EgovExcel.writeExcelValue(sheet, 25, 13, signMngInfo.get("esbNam"));	// 업자명(지주)
			EgovExcel.writeExcelValue(sheet, 25, 24, signMngInfo.get("rodFrm"));	// 도로형태

			// 이면
			sheet = wb.getSheetAt(1);

			String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
			String fileNam = serverPath + "exImg_" + signMngInfo.get("mgrnu") + ".png";

			// 이미지 자르기
			BufferedImage oriImg = ImageIO.read(new File(fileNam));

			int imgwidth = Math.min(oriImg.getHeight(), oriImg.getWidth());
			int imgheight = imgwidth;

			BufferedImage sacledImage = Scalr.crop(oriImg, (oriImg.getWidth()-imgwidth)/2, (oriImg.getHeight()-imgheight)/2, imgwidth, imgheight, null);
			BufferedImage resizedImage = Scalr.resize(sacledImage, 300, 800, null);

			ImageIO.write(resizedImage, "png", new File(fileNam));

			// 엑셀에 이미지 넣기
			File outputfile = new File(fileNam);
			if(outputfile.isFile()) {
				EgovExcel.SetExcelImage(wb,sheet,fileNam,900,255,1,3,7,11);		// 지도 이미지
				FileUtil.rmFile(fileNam);
			}

			// 엑셀에 데이터 입력
			EgovExcel.writeExcelValue(sheet, 5, 8, signMngInfo.get("sptMrkEt"));	// 보조표지기록
			EgovExcel.writeExcelValue(sheet, 2, 15, signMngInfo.get("ntc"));		// 고시사항

			file.close();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("표지관리대장", "UTF-8") + "(" + signMngInfo.get("mgrnu") + ")_" + ComDateUtils.getCurDateTime() + ".xls" + ";");
			ServletOutputStream myOut = response.getOutputStream();
			wb.write(myOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 안전표지 리스트 페이지
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report/signMng/igtSignPopW.do")
	public String igtSignPopW(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "report/signMng/igtSignPopW";
	}

	/**
	 * 관리대장 > 표지관리대장 > 관리대장 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M006001004")
	@RequestMapping(value = "/report/signMng/selectInfoPopM.do")
	public String selectInfoPopM(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		EgovMap result = signMngService.selectInfo(params);

		model.addAttribute("result", result);
		return "report/signMng/selectInfoPopM";
	}

}
