package tgis.report.signalMng;

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
import tgis.report.signalMng.service.SignalMngService;
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
public class SignalMngController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SignalMngController.class);
	
	@Resource(name = "signalMngService")
	private SignalMngService signalMngService;

	// 관리대장 > 교통신호기 관리대장 리스트 페이지 이동
	@AuthHandler(handler="M006001006")
	@RequestMapping(value = "/report/signalMng/selectInfoList.do")
	public String selectInfoList() throws Exception {
		return "report/signalMng/selectInfoList";
	}

	// 관리대장 > 교통신호기 관리대장 리스트 조회
	@AuthHandler(handler="M006001006")
	@RequestMapping(value = "/report/signalMng/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = signalMngService.selectInfoList(params);

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
	@AuthHandler(handler="M006001006")
	@RequestMapping(value = "/report/signalMng/selectInfoMap.do")
	public String selectInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = signalMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = signalMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001006");

		return "report/signalMng/selectInfoMap";
	}

	// 수정 창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001006")
	@RequestMapping(value = "/report/signalMng/updateInfoMap.do")
	public String updateInfoMap(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		EgovMap result = signalMngService.selectInfo(params);
		if(Integer.parseInt((result.get("filecnt").toString()))>0){
			List<?> fileList = signalMngService.selectFileInfo(params);
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("result", result);

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M006001006");

		return "report/signalMng/updateInfoMap";
	}

	// 수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M006001006")
	@RequestMapping(value = "/report/signalMng/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("layLoca", XssPreventer.escape(params.get("layLoca").toString()));
				params.put("mkMdl", XssPreventer.escape(params.get("mkMdl").toString()));

				signalMngService.updateInfo(params, request);
			} else {
				signalMngService.deleteInfo(params);
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
	 * 관리대장 > 교통신호기 관리대장 > 관리대장 엑셀 다운로드(지도 이미지 임시 저장)
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/report/signalMng/imgUpload", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
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
	 * 관리대장 > 교통신호기 관리대장 > 관리대장 엑셀 다운로드
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "report/signalMng/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String excelPath = request.getSession().getServletContext().getRealPath("/resource/report/");
			FileInputStream file = new FileInputStream(new File(excelPath + "교통신호기관리대장.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);

			EgovMap result = signalMngService.selectInfoPop(params);

			params.put("cssMgrnu", result.get("cssMgrnu"));

			// 전면
			// 엑셀에 데이터 입력
			EgovExcel.writeExcelValue(sheet, 4, 0, result.get("peNam"));	// 경찰서명
			EgovExcel.writeExcelValue(sheet, 4, 3, result.get("rodM"));		// 도로명
			EgovExcel.writeExcelValue(sheet, 8, 0, result.get("esbYmd"));	// 설치 년 월 일
			EgovExcel.writeExcelValue(sheet, 8, 3, result.get("addr"));		// 설치 위치(주소)

			if("1".equals(result.get("ctlMet"))) {
				EgovExcel.writeExcelValue(sheet, 8, 9, "V");		// 제어방식 (전자)
			} else if("2".equals(result.get("ctlMet"))) {
				EgovExcel.writeExcelValue(sheet, 9, 9, "V");		// 제어방식 (일반)
			}

			if("1".equals(result.get("rodKnd"))) {
				EgovExcel.writeExcelValue(sheet, 8, 12, "V");		// 도로구분 (교차로)
			} else if("2".equals(result.get("rodKnd"))) {
				EgovExcel.writeExcelValue(sheet, 9, 12, "V");		// 도로구분 (단일로)
			}

			if("1".equals(result.get("conFor"))) {
				EgovExcel.writeExcelValue(sheet, 11, 1, "V");		// 신호기 제식 (내민식)
			} else if("2".equals(result.get("conFor"))) {
				EgovExcel.writeExcelValue(sheet, 12, 1, "V");		// 신호기 제식 (측주식)
			} else if("3".equals(result.get("conFor"))) {
				EgovExcel.writeExcelValue(sheet, 13, 1, "V");		// 신호기 제식 (현수식)
			} else if("4".equals(result.get("conFor"))) {
				EgovExcel.writeExcelValue(sheet, 14, 1, "V");		// 신호기 제식 (문현식)
			}

			if("1".equals(result.get("lineMet"))) {
				EgovExcel.writeExcelValue(sheet, 15, 1, "V");		// 배선 (지하)
			} else if("2".equals(result.get("lineMet"))) {
				EgovExcel.writeExcelValue(sheet, 16, 1, "V");		// 배선 (가공)
			} else if("3".equals(result.get("lineMet"))) {
				EgovExcel.writeExcelValue(sheet, 17, 1, "V");		// 배선 (혼합)
			}

			List<?> sinho = signalMngService.selectInfoSinho(params);

			EgovMap sinhoInfo1 = new EgovMap();
			EgovMap sinhoInfo2 = new EgovMap();
			EgovMap sinhoInfo4 = new EgovMap();

			int seq1 = 1;
			int seq2 = 1;
			int seq4 = 1;

			for(int i=0; i<sinho.size(); i++) {
				EgovMap rMap = (EgovMap) sinho.get(i);

				if(rMap.get("gubun").equals("1")) {
					if(seq1 == 1) {
						EgovExcel.writeExcelValue(sheet, 13, 3, rMap.get("mop").toString());		// 차량신호등 재질 (첫번째)
						EgovExcel.writeExcelValue(sheet, 13, 4, rMap.get("knd").toString());		// 차량신호등 종류 (첫번째)
						EgovExcel.writeExcelValue(sheet, 13, 6, rMap.get("cnt").toString());		// 차량신호등 수량 (첫번째)
					} else if(seq1 == 2) {
						EgovExcel.writeExcelValue(sheet, 14, 3, rMap.get("mop").toString());		// 차량신호등 재질 (두번째)
						EgovExcel.writeExcelValue(sheet, 14, 4, rMap.get("knd").toString());		// 차량신호등 종류 (두번째)
						EgovExcel.writeExcelValue(sheet, 14, 6, rMap.get("cnt").toString());		// 차량신호등 수량 (두번째)
					} else if(seq1 == 3) {
						EgovExcel.writeExcelValue(sheet, 15, 3, rMap.get("mop").toString());		// 차량신호등 재질 (세번째)
						EgovExcel.writeExcelValue(sheet, 15, 4, rMap.get("knd").toString());		// 차량신호등 종류 (세번째)
						EgovExcel.writeExcelValue(sheet, 15, 6, rMap.get("cnt").toString());		// 차량신호등 수량 (세번째)
					}
					seq1++;
				} else if(rMap.get("gubun").equals("2")) {
					if(seq2 == 1) {
						EgovExcel.writeExcelValue(sheet, 25, 3, rMap.get("mop").toString());		// 보행등 재질 (첫번째)
						EgovExcel.writeExcelValue(sheet, 25, 4, rMap.get("knd").toString());		// 보행등 종류 (첫번째)
						EgovExcel.writeExcelValue(sheet, 25, 6, rMap.get("cnt").toString());		// 보행등 수량 (첫번째)
					} else if(seq2 == 2) {
						EgovExcel.writeExcelValue(sheet, 26, 3, rMap.get("mop").toString());		// 보행등 재질 (두번째)
						EgovExcel.writeExcelValue(sheet, 26, 4, rMap.get("knd").toString());		// 보행등 종류 (두번째)
						EgovExcel.writeExcelValue(sheet, 26, 6, rMap.get("cnt").toString());		// 보행등 수량 (두번째)
					} else if(seq2 == 3) {
						EgovExcel.writeExcelValue(sheet, 27, 3, rMap.get("mop").toString());		// 보행등 재질 (세번째)
						EgovExcel.writeExcelValue(sheet, 27, 4, rMap.get("knd").toString());		// 보행등 종류 (세번째)
						EgovExcel.writeExcelValue(sheet, 27, 6, rMap.get("cnt").toString());		// 보행등 수량 (세번째)
					}
					seq2++;
				} else if(rMap.get("gubun").equals("4")) {
					if(seq4 == 1) {
						EgovExcel.writeExcelValue(sheet, 17, 3, rMap.get("mop").toString());		// 차량경보등 재질 (첫번째)
						EgovExcel.writeExcelValue(sheet, 17, 4, rMap.get("knd").toString());		// 차량경보등 종류 (첫번째)
						EgovExcel.writeExcelValue(sheet, 17, 6, rMap.get("cnt").toString());		// 차량경보등 수량 (첫번째)
					} else if(seq4 == 2) {
						EgovExcel.writeExcelValue(sheet, 18, 3, rMap.get("mop").toString());		// 차량경보등 재질 (두번째)
						EgovExcel.writeExcelValue(sheet, 18, 4, rMap.get("knd").toString());		// 차량경보등 종류 (두번째)
						EgovExcel.writeExcelValue(sheet, 18, 6, rMap.get("cnt").toString());		// 차량경보등 수량 (두번째)
					} else if(seq4 == 3) {
						EgovExcel.writeExcelValue(sheet, 19, 3, rMap.get("mop").toString());		// 차량경보등 재질 (세번째)
						EgovExcel.writeExcelValue(sheet, 19, 4, rMap.get("knd").toString());		// 차량경보등 종류 (세번째)
						EgovExcel.writeExcelValue(sheet, 19, 6, rMap.get("cnt").toString());		// 차량경보등 수량 (세번째)
					}
					seq4++;
				}
			}

			List<?> jiju = signalMngService.selectInfoJiju(params);

			List<?> bu = signalMngService.selectInfoBu(params);

			for(int i = 0; i<bu.size(); i++) {
				EgovMap rMap = (EgovMap) bu.get(i);

				if(rMap.get("asnLen").toString().equals("9")) {
					EgovExcel.writeExcelValue(sheet, 18, 9, rMap.get("cnt").toString());		// 신호등 부착대 (9m)
				} else if(rMap.get("asnLen").toString().equals("7")) {
					EgovExcel.writeExcelValue(sheet, 19, 9, rMap.get("cnt").toString());		// 신호등 부착대 (7m)
				} else if(rMap.get("asnLen").toString().equals("5")) {
					EgovExcel.writeExcelValue(sheet, 20, 9, rMap.get("cnt").toString());		// 신호등 부착대 (5m)
				} else if(rMap.get("asnLen").toString().equals("3")) {
					EgovExcel.writeExcelValue(sheet, 21, 9, rMap.get("cnt").toString());		// 신호등 부착대 (3m)
				}
			}

			// 이면
			sheet = wb.getSheetAt(1);

			String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
			String fileNam = serverPath + "exImg_" + result.get("mgrnu") + ".png";

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
				EgovExcel.SetExcelImage(wb,sheet,fileNam,900,255,1,6,7,15);		// 지도 이미지
				FileUtil.rmFile(fileNam);
			}

			file.close();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("교통신호기관리대장", "UTF-8") + "(" + result.get("mgrnu") + ")_" + ComDateUtils.getCurDateTime() + ".xls" + ";");
			ServletOutputStream myOut = response.getOutputStream();
			wb.write(myOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 관리대장 > 교통신호기 관리대장 > 관리대장 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M006001006")
	@RequestMapping(value = "/report/signalMng/selectInfoPopM.do")
	public String selectInfoPopM(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		EgovMap result = signalMngService.selectInfoPop(params);

		params.put("cssMgrnu", result.get("cssMgrnu"));

		List<?> sinho = signalMngService.selectInfoSinho(params);

		EgovMap sinhoInfo1 = new EgovMap();
		EgovMap sinhoInfo2 = new EgovMap();
		EgovMap sinhoInfo4 = new EgovMap();

		int seq1 = 1;
		int seq2 = 1;
		int seq4 = 1;

		for(int i=0; i<sinho.size(); i++) {
			EgovMap rMap = (EgovMap) sinho.get(i);

			if(rMap.get("gubun").equals("1")) {
				sinhoInfo1.put("gubun", rMap.get("gubun").toString());
				sinhoInfo1.put("knd", rMap.get("knd").toString());
				sinhoInfo1.put("mop", rMap.get("mop").toString());
				sinhoInfo1.put("cnt", rMap.get("cnt").toString());

				model.addAttribute("signal1_" + seq1, sinhoInfo1);
				model.addAttribute("signal1_cnt", seq1);
				seq1++;
			} else if(rMap.get("gubun").equals("2")) {
				sinhoInfo2.put("gubun", rMap.get("gubun").toString());
				sinhoInfo2.put("knd", rMap.get("knd").toString());
				sinhoInfo2.put("mop", rMap.get("mop").toString());
				sinhoInfo2.put("cnt", rMap.get("cnt").toString());

				model.addAttribute("signal2_" + seq2, sinhoInfo2);
				model.addAttribute("signal2_cnt", seq2);
				seq2++;
			} else if(rMap.get("gubun").equals("4")) {
				sinhoInfo4.put("gubun", rMap.get("gubun").toString());
				sinhoInfo4.put("knd", rMap.get("knd").toString());
				sinhoInfo4.put("mop", rMap.get("mop").toString());
				sinhoInfo4.put("cnt", rMap.get("cnt").toString());

				model.addAttribute("signal4_" + seq4, sinhoInfo4);
				model.addAttribute("signal4_cnt", seq4);
				seq4++;
			}
		}

		List<?> jiju = signalMngService.selectInfoJiju(params);

		List<?> bu = signalMngService.selectInfoBu(params);

		for(int i = 0; i<bu.size(); i++) {
			EgovMap rMap = (EgovMap) bu.get(i);

			model.addAttribute("bu9_cnt", "0");
			model.addAttribute("bu7_cnt", "0");
			model.addAttribute("bu5_cnt", "0");
			model.addAttribute("bu3_cnt", "0");

			if(rMap.get("asnLen").toString().equals("9")) {
				model.addAttribute("bu9_cnt", rMap.get("cnt"));
			} else if(rMap.get("asnLen").toString().equals("7")) {
				model.addAttribute("bu7_cnt", rMap.get("cnt"));
			} else if(rMap.get("asnLen").toString().equals("5")) {
				model.addAttribute("bu5_cnt", rMap.get("cnt"));
			} else if(rMap.get("asnLen").toString().equals("3")) {
				model.addAttribute("bu3_cnt", rMap.get("cnt"));
			}
		}

		model.addAttribute("result", result);
		model.addAttribute("jiju", jiju);
		model.addAttribute("bu", bu);

		return "report/signalMng/selectInfoPopM";
	}

}
