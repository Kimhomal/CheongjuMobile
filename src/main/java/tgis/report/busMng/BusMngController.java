package tgis.report.busMng;

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
import tgis.common.util.encrypt.EncryptSDK;
import tgis.report.busMng.service.BusMngService;
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
public class BusMngController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BusMngController.class);
	
	@Resource(name = "busMngService")
	private BusMngService busMngService;

	// 관리대장 > 버스승강장 관리카드
	@AuthHandler(handler="M006001001")
	@RequestMapping(value = "/report/busMng/selectInfoList.do")
	public String selectInfoList() throws Exception {
		return "report/busMng/selectInfoList";
	}

	// 관리대장 > 버스승강장 관리카드 조회
	@AuthHandler(handler="M006001001")
	@RequestMapping(value = "/report/busMng/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = busMngService.selectInfoList(params);

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
	@AuthHandler(handler="M006001001")
	@RequestMapping(value = "/report/busMng/selectInfoMap.do")
	public String selectInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = busMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = busMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001001");

		return "report/busMng/selectInfoMap";
	}

	// 수정 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001001")
	@RequestMapping(value = "/report/busMng/updateInfoMap.do")
	public String updateInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = busMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = busMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001001");

		return "report/busMng/updateInfoMap";
	}

	// 수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001001")
	@RequestMapping(value = "/report/busMng/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("name", XssPreventer.escape(params.get("name").toString()));
				params.put("rodNam", XssPreventer.escape(params.get("rodNam").toString()));
				params.put("structure", XssPreventer.escape(params.get("structure").toString()));
				params.put("mngNam", XssPreventer.escape(params.get("mngNam").toString()));
				params.put("subMngNam", XssPreventer.escape(params.get("subMngNam").toString()));

				busMngService.updateInfo(params, request);
			} else {
				busMngService.deleteInfo(params);
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
	 * 관리대장 > 버스승강장 관리카드 > 관리대장 엑셀 다운로드(지도 이미지 임시 저장)
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/report/busMng/imgUpload", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
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
	 * 관리대장 > 버스승강장 관리카드 > 관리대장 엑셀 다운로드
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "report/busMng/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			EgovMap busMngInfo = busMngService.selectInfo(params);

			String excelPath = request.getSession().getServletContext().getRealPath("/resource/report/");
			FileInputStream file = new FileInputStream(new File(excelPath + "버스승강장관리카드.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);

			// 엑셀에 데이터 입력
			EgovExcel.writeExcelValue(sheet, 2, 2, busMngInfo.get("mgrnu"));		// 관리번호
			EgovExcel.writeExcelValue(sheet, 23, 2, busMngInfo.get("esbYmd"));		// 설치년월일
			EgovExcel.writeExcelValue(sheet, 23, 5, busMngInfo.get("rodNam"));		// 노선명
			EgovExcel.writeExcelValue(sheet, 24, 2, busMngInfo.get("name"));		// 설치장소
			EgovExcel.writeExcelValue(sheet, 24, 5, busMngInfo.get("structure"));	// 구조
			EgovExcel.writeExcelValue(sheet, 25, 2, busMngInfo.get("mngNam"));		// 관리책임자
			EgovExcel.writeExcelValue(sheet, 25, 5, busMngInfo.get("subMngNam"));	// 부관리자

			String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
			String fileNam = serverPath + "exImg_" + busMngInfo.get("mgrnu") + ".png";

			File outputfile = new File(fileNam);
			if(outputfile.isFile()) {
				EgovExcel.SetExcelImage(wb,sheet,fileNam,1023,255,1,5,6,22);		// 지도 이미지
				FileUtil.rmFile(fileNam);
			}

			file.close();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("버스승강장관리카드", "UTF-8") + "(" + busMngInfo.get("mgrnu") + ")_" + ComDateUtils.getCurDateTime() + ".xls" + ";");
			ServletOutputStream myOut = response.getOutputStream();
			wb.write(myOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 관리대장 > 버스승강장 관리카드 > 관리대장 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M006001001")
	@RequestMapping(value = "/report/busMng/selectInfoPopM.do")
	public String selectInfoPopM(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		EgovMap result = busMngService.selectInfo(params);

		model.addAttribute("result", result);
		return "report/busMng/selectInfoPopM";
	}

}
