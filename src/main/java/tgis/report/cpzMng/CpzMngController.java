package tgis.report.cpzMng;

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
import sun.misc.BASE64Decoder;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.EgovExcel;
import tgis.common.util.com.FileUtil;
import tgis.report.cpzMng.service.CpzMngService;
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
public class CpzMngController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CpzMngController.class);
	
	@Resource(name = "cpzMngService")
	private CpzMngService cpzMngService;

	// 관리대장 > 어린이보호구역 관리카드
	@AuthHandler(handler="M006001003")
	@RequestMapping(value = "/report/cpzMng/selectInfoList.do")
	public String selectInfoList() throws Exception {
		return "report/cpzMng/selectInfoList";
	}

	// 관리대장 > 어린이보호구역 관리카드 조회
	@AuthHandler(handler="M006001003")
	@RequestMapping(value = "/report/cpzMng/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = cpzMngService.selectInfoList(params);

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

	// 상세보기창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001003")
	@RequestMapping(value = "/report/cpzMng/selectInfoMap.do")
	public String selectInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		if("null".equals(String.valueOf(params.get("sMgrnu")))) {
			params.put("mgrnu", params.get("mgrnu"));
		} else {
			params.put("mgrnu", params.get("sMgrnu"));
		}

		EgovMap result = cpzMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = cpzMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);
		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001003");

		return "report/cpzMng/selectInfoMap";
	}

	// 수정 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001003")
	@RequestMapping(value = "/report/cpzMng/updateInfoMap.do")
	public String updateInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = cpzMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = cpzMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001003");

		return "report/cpzMng/updateInfoMap";
	}

	// 수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001003")
	@RequestMapping(value = "/report/cpzMng/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("ctkMgrnu", XssPreventer.escape(params.get("ctkMgrnu").toString()));
				params.put("schNam", XssPreventer.escape(params.get("schNam").toString()));
				params.put("jibun", XssPreventer.escape(params.get("jibun").toString()));
				params.put("schTel", XssPreventer.escape(params.get("schTel").toString()));
				params.put("die", XssPreventer.escape(params.get("die").toString()));
				params.put("inj", XssPreventer.escape(params.get("inj").toString()));
				params.put("len", XssPreventer.escape(params.get("len").toString()));
				params.put("rodWid", XssPreventer.escape(params.get("rodWid").toString()));
				params.put("spdLtd", XssPreventer.escape(params.get("spdLtd").toString()));
				params.put("memo", XssPreventer.escape(params.get("memo").toString()));

				cpzMngService.updateInfo(params, request);
			} else {
				cpzMngService.deleteInfo(params);
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
	 * 관리대장 > 어린이보호구역 관리카드 > 관리대장 엑셀 다운로드(지도 이미지 임시 저장)
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/report/cpzMng/imgUpload", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
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

		model.addAttribute("jsonView", jsonMap);

		return model;
	}

	/**
	 * 관리대장 > 어린이보호구역 관리카드 > 관리대장 엑셀 다운로드
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "report/cpzMng/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			EgovMap cpzMngInfo = cpzMngService.selectInfo(params);

			String excelPath = request.getSession().getServletContext().getRealPath("/resource/report/");
			FileInputStream file = new FileInputStream(new File(excelPath + "어린이보호구역관리카드.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);

			// 엑셀에 데이터 입력
			EgovExcel.writeExcelValue(sheet, 3, 2, cpzMngInfo.get("schNam"));		// 학교(유치원)명
			EgovExcel.writeExcelValue(sheet, 4, 2, cpzMngInfo.get("guNam") + " " + cpzMngInfo.get("dongNam") + " " + cpzMngInfo.get("jibun"));		// 소재지
			EgovExcel.writeExcelValue(sheet, 4, 10, cpzMngInfo.get("schTel"));		// 전화번호
			EgovExcel.writeExcelValue(sheet, 5, 2, cpzMngInfo.get("regYmd"));		// 지정일자
			EgovExcel.writeExcelValue(sheet, 5, 8, cpzMngInfo.get("schCeo"));		// 학교(유치원)장 성명
			EgovExcel.writeExcelValue(sheet, 9, 0, cpzMngInfo.get("mgrnu"));		// 일련번호
			EgovExcel.writeExcelValue(sheet, 9, 5, cpzMngInfo.get("len"));			// 연장거리
			EgovExcel.writeExcelValue(sheet, 9, 6, cpzMngInfo.get("rodWid"));		// 도로폭

			// 시설물 설치 내용 입력
			EgovMap facilInfo = cpzMngService.selectFacilInfo(params);

			EgovExcel.writeExcelValue(sheet, 9, 7, "○ 횡단보도 : " + facilInfo.get("gtA004ACount").toString());
			EgovExcel.writeExcelValue(sheet, 10, 7, "○ 노면문자표시 : " + facilInfo.get("gtA054PCount").toString());
			EgovExcel.writeExcelValue(sheet, 11, 7, "○ 노면방향표시 : " + facilInfo.get("gtA055PCount").toString());
			EgovExcel.writeExcelValue(sheet, 12, 7, "○ 안전표지 : " + facilInfo.get("gtA064PCount").toString());
			EgovExcel.writeExcelValue(sheet, 13, 7, "○ 표지병 : " + facilInfo.get("gtA065LCount").toString());
			EgovExcel.writeExcelValue(sheet, 14, 7, "○ 과속방지턱 : " + facilInfo.get("gtA067ACount").toString());
			EgovExcel.writeExcelValue(sheet, 15, 7, "○ 정지선 : " + facilInfo.get("gtA081LCount").toString());
			EgovExcel.writeExcelValue(sheet, 16, 7, "○ 주차금지 : " + facilInfo.get("gtA082LCount").toString());
			EgovExcel.writeExcelValue(sheet, 17, 7, "○ 중앙선 : " + facilInfo.get("gtA083LCount").toString());
			EgovExcel.writeExcelValue(sheet, 18, 7, "○ 도로반사경 : " + facilInfo.get("gtC051PCount").toString());
			EgovExcel.writeExcelValue(sheet, 19, 7, "○ 방호울타리 : " + facilInfo.get("gtC059ACount").toString());
			EgovExcel.writeExcelValue(sheet, 20, 7, "○ 갈매기표지 : " + facilInfo.get("gtC088PCount").toString());
			EgovExcel.writeExcelValue(sheet, 21, 7, "○ 점자블럭 : " + facilInfo.get("gtC094ACount").toString());
			EgovExcel.writeExcelValue(sheet, 22, 7, "○ 조명시설 : " + facilInfo.get("gtC100PCount").toString());
			EgovExcel.writeExcelValue(sheet, 23, 7, "○ 고원식교차로 : " + facilInfo.get("gtC104ACount").toString());
			EgovExcel.writeExcelValue(sheet, 24, 7, "○ 컬러포장 : " + facilInfo.get("gtC107ACount").toString());
			EgovExcel.writeExcelValue(sheet, 25, 7, "○ 횡단보도예고표시 : " + facilInfo.get("gtA068PCount").toString());

			// 첨부파일 이미지 넣기
//			List<?> atchFileList = cpzMngService.selectFileInfo(params);
//
//			String atchFilePath = EgovProperties.getProperty("Globals.fileStorePath") + cpzMngInfo.get("mgrnu") + "/";
//			String atchFileNam = "";
//
//			for(int i=0; i<atchFileList.size(); i++) {
//				EgovMap atchFileInfo = (EgovMap)atchFileList.get(i);
//
//				atchFileNam = atchFilePath + atchFileInfo.get("fileNam");
//
//				File outputfile = new File(atchFileNam);
//
//				if(outputfile.isFile()) {
//					if(i == 0) {
//						EgovExcel.SetExcelImage(wb,sheet,atchFileNam,800,255,1,13,6,16);			// 사진 이미지
//					} else {
//						EgovExcel.SetExcelImage(wb,sheet,atchFileNam,800,255,1,17,6,20);			// 사진 이미지
//					}
//				}
//			}

			sheet = wb.getSheetAt(1);
			EgovExcel.writeExcelValue(sheet, 1, 0, cpzMngInfo.get("schNam") + " 주변약도");		// 어린이보호구역명 (주변약도)

			String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
			String fileNam = serverPath + "exImg_" + cpzMngInfo.get("mgrnu") + ".png";

			// 이미지 자르기
//			BufferedImage oriImg = ImageIO.read(new File(fileNam));
//
//			int imgwidth = Math.min(oriImg.getHeight(), oriImg.getWidth());
//			int imgheight = imgwidth;
//
//			BufferedImage sacledImage = Scalr.crop(oriImg, (oriImg.getWidth()-imgwidth)/2, (oriImg.getHeight()-imgheight)/2, imgwidth, imgheight, null);
//			BufferedImage resizedImage = Scalr.resize(sacledImage, 300, 800, null);
//
//			ImageIO.write(resizedImage, "png", new File(fileNam));

			// 엑셀에 지도 이미지 넣기
			File outputfile = new File(fileNam);
			if(outputfile.isFile()) {
				EgovExcel.SetExcelImage(wb,sheet,fileNam,255,255,0,5,11,29);		// 지도 이미지
				FileUtil.rmFile(fileNam);
			}

			file.close();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("어린이보호구역관리카드", "UTF-8") + "(" + cpzMngInfo.get("mgrnu") + ")_" + ComDateUtils.getCurDateTime() + ".xls" + ";");
			ServletOutputStream myOut = response.getOutputStream();
			wb.write(myOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 관리대장 > 어린이보호구역 관리카드 > 관리대장 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M006001003")
	@RequestMapping(value = "/report/cpzMng/selectInfoPopM.do")
	public String selectInfoPopM(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		EgovMap result = cpzMngService.selectInfo(params);

		// 시설물 설치 내용 입력
		EgovMap facilInfo = cpzMngService.selectFacilInfo(params);

		model.addAttribute("result", result);
		model.addAttribute("facilInfo", facilInfo);
		return "report/cpzMng/selectInfoPopM";
	}

}
