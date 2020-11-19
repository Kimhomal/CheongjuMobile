package tgis.statistics;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.misc.BASE64Decoder;
import tgis.common.annotation.AuthExclude;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.EgovExcel;
import tgis.common.util.com.FileUtil;
import tgis.statistics.service.StatisticsMngService;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Controller
public class StatisticsMngController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsMngController.class);

	@Resource(name = "statisticsMngService")
	private StatisticsMngService masterService;

	// 통계관리 > 전체통계 초기 페이지
	@RequestMapping(value = "/statistics/selectAllInfo.do")
	public String selectAllInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		EgovMap trafficCnt = masterService.trafficCnt();
		EgovMap rodCnt = masterService.rodCnt();
		EgovMap specialCnt = masterService.specialCnt();
		EgovMap baseCnt = masterService.baseCnt();
		EgovMap etcCnt = masterService.etcCnt();

		model.addAttribute("trafficCnt", trafficCnt);
		model.addAttribute("rodCnt", rodCnt);
		model.addAttribute("specialCnt", specialCnt);
		model.addAttribute("baseCnt", baseCnt);
		model.addAttribute("etcCnt", etcCnt);
		model.addAttribute("params", params);
		return "statistics/selectAllInfo";
	}

	// 통계관리 > 교통안전시설물 초기 페이지
	@RequestMapping(value = "/statistics/selectTrafficInfo.do")
	public String selectTrafficInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/selectTrafficInfo";
	}

	// 통계관리 > 도로안전시설물 초기 페이지
	@RequestMapping(value = "/statistics/selectRodInfo.do")
	public String selectRodInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/selectRodInfo";
	}

	// 통계관리 > 특수교통운영구역 초기 페이지
	@RequestMapping(value = "/statistics/selectSpecialInfo.do")
	public String selectSpecialInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/selectSpecialInfo";
	}

	// 통계관리 > 기본도 초기 페이지
	@RequestMapping(value = "/statistics/selectBaseInfo.do")
	public String selectBaseInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/selectBaseInfo";
	}

	// 통계관리 > 기타 초기 페이지
	@RequestMapping(value = "/statistics/selectEtcInfo.do")
	public String selectEtcInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/selectEtcInfo";
	}

	// 통계관리 > 교통안전시설물 > 전체현황 페이지
	@RequestMapping(value = "/statistics/totalStatisticsPopE.do")
	public String totalStatisticsPopE(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/" + params.get("facilityId") + "/" + "totalStatisticsPopE";
	}

	// 통계관리 > 교통안전시설물 > 조건별 현황 페이지
	@RequestMapping(value = "/statistics/whereStatisticsPopE.do")
	public String whereStatisticsPopE(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/" + params.get("facilityId") + "/" + "whereStatisticsPopE";
	}

	// 통계관리 > 교통안전시설물 > 상세내역 페이지
	@RequestMapping(value = "/statistics/detailStatisticsPopE.do")
	public String detailStatisticsPopE(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/" + params.get("facilityId") + "/" + "detailStatisticsPopE";
	}

	// 통계관리 > 교통안전시설물 > 교차로기준 현황 페이지
	@RequestMapping(value = "/statistics/crossStatisticsPopE.do")
	public String crossStatisticsPopE(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "statistics/" + params.get("facilityId") + "/" + "crossStatisticsPopE";
	}

	// 통계관리 > 교통안전시설물 > 전체현황, 조건별 현황 통계 조회
	@RequestMapping(value = "/statistics/selectStatisticsInfo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectStatisticsInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()){
			String key = keys.next();
			String tempStr = String.valueOf(params.get(key));

			if(!"rows".equals(key) && !"page".equals(key) && !"facilityId".equals(key) && !"arrHeader".equals(key) && !"facilityName".equals(key) && !"cssNam".equals(key) && !"stYmd".equals(key) && !"enYmd".equals(key)) {
				if(!"null".equals(tempStr)  && !"".equals(tempStr) ) {
					params.put(key, tempStr.split(",") );
				}
			}
		}

		List<?> GuResult = masterService.selectGuStatistics(params);
		jsonMap.put("GuResult",GuResult);

		List<?> PoliceResult = masterService.selectPoliceStatistics(params);
		jsonMap.put("PoliceResult",PoliceResult);

		List<?> DongResult = masterService.selectDongStatistics(params);
		jsonMap.put("DongResult",DongResult);

		model.addAttribute("jsonView", jsonMap);
		return model;
	}

	// 통계관리 > 교통안전시설물 > 전체현황, 조건별 현황, 교차로기준 현황 > 엑셀 다운로드
	@AuthExclude
	@RequestMapping(value = "statistics/selectStatisticsExcelDown.do")
	public void selectStatisticsExcelDown(@RequestParam HashMap<String, Object> params, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()){
			String key = keys.next();
			String tempStr = String.valueOf(params.get(key));

			if(!"rows".equals(key) && !"page".equals(key) && !"facilityId".equals(key) && !"arrHeader".equals(key) && !"facilityName".equals(key) && !"cssNam".equals(key) && !"stYmd".equals(key) && !"enYmd".equals(key) && !"statType".equals(key)) {
				if(!"null".equals(tempStr)  && !"".equals(tempStr) ) {
					params.put(key, tempStr.split(",") );
				}
			}
		}

		// 리스트조회
		List<?> GuResult = masterService.selectGuStatistics(params);

		List<?> PoliceResult = masterService.selectPoliceStatistics(params);

		List<?> DongResult = masterService.selectDongStatistics(params);

		String[] arrHeader = params.get("arrHeader").toString().split(",");

		String statType = params.get("statType").toString();

		EgovExcel.SetExcelList(workbook, workbook.createSheet("구별 통계"),arrHeader, GuResult, 20, 0);

		// 통계 그래프 이미지 가져오기
		String serverPath = EgovProperties.getProperty("Globals.fileStorePath") + "exImg/";
		String fileNam = serverPath + "exImg_stat.png";

		HSSFSheet sheet = workbook.getSheetAt(0);

		// 엑셀에 그래프 이미지 넣기
		File outputfile = new File(fileNam);
		if(outputfile.isFile()) {
			EgovExcel.SetExcelImage(workbook,sheet,fileNam,255,255,0,0,7,19);
			FileUtil.rmFile(fileNam);
		}

		EgovExcel.SetExcelList(workbook,  workbook.createSheet("경찰서별 통계"),arrHeader, PoliceResult, 0, 0);

		EgovExcel.SetExcelList(workbook,  workbook.createSheet("동별 통계"),arrHeader, DongResult, 0, 0);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(statType+"_"+params.get("facilityName").toString()+"_"+ ComDateUtils.getCurDateTime(), "UTF-8") + ".xls" + ";");
		ServletOutputStream myOut = response.getOutputStream();
		workbook.write(myOut); // 파일 저장
	}

	// 통계관리 > 교통안전시설물 > 상세내역 > 통계 조회
	@RequestMapping(value = "/statistics/selectDetailFcltsList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectDetailFcltsList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()){
			String key = keys.next();
			String tempStr = String.valueOf(params.get(key));

			if(!"rows".equals(key) && !"page".equals(key) && !"facilityId".equals(key) && !"arrHeader".equals(key) && !"facilityName".equals(key) && !"cssNam".equals(key) && !"stYmd".equals(key) && !"enYmd".equals(key)) {
				if(!"null".equals(tempStr)  && !"".equals(tempStr) ) {
					params.put(key, tempStr.split(",") );
				}
			}
		}

		// 리스트조회
		List<?> resultList = masterService.selectDetailFcltsList(params);

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

	// 통계관리 > 교통안전시설물 > 상세내역 > 엑셀 다운로드
	@RequestMapping(value = "statistics/selectDetailFcltsExcelDown.do")
	public void selectDetailFcltsExcelDown(@RequestParam HashMap<String, Object> params, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()){
			String key = keys.next();
			String tempStr = String.valueOf(params.get(key));

			if(!"rows".equals(key) && !"page".equals(key) && !"facilityId".equals(key) && !"arrHeader".equals(key) && !"facilityName".equals(key) && !"cssNam".equals(key) && !"stYmd".equals(key) && !"enYmd".equals(key)) {
				if(!"null".equals(tempStr)  && !"".equals(tempStr) ) {
					params.put(key, tempStr.split(",") );
				}
			}
		}

		// 리스트조회
		List<?> resultList = masterService.selectDetailFcltsExcelDown(params);

		String[] arrHeader = params.get("arrHeader").toString().split(",");

		EgovExcel.SetExcelList(workbook, workbook.createSheet("상세현황"),arrHeader, resultList, 0, 0);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("상세내역_"+params.get("facilityName").toString()+"_"+ ComDateUtils.getCurDateTime(), "UTF-8") + ".xls" + ";");
		ServletOutputStream myOut = response.getOutputStream();
		workbook.write(myOut); // 파일 저장
	}

	// 통계관리 > 교통안전시설물 > 교차로기준 현황 > 통계 조회
	@RequestMapping(value = "/statistics/selectCrossStatisticsInfo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectCrossStatisticsInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()){
			String key = keys.next();
			String tempStr = String.valueOf(params.get(key));

			if(!"rows".equals(key) && !"page".equals(key) && !"facilityId".equals(key) && !"arrHeader".equals(key) && !"facilityName".equals(key) && !"cssNam".equals(key) && !"stYmd".equals(key) && !"enYmd".equals(key)) {
				if(!"null".equals(tempStr)  && !"".equals(tempStr) ) {
					params.put(key, tempStr.split(",") );
				}
			}
		}

		List<?> CrossResult = masterService.selectCrossStatistics(params);
		double totalPage = 0.0;
		Long totalRows = 0L;

		if (CrossResult.size() > 0) {
			totalRows = Long.valueOf(((EgovMap) CrossResult.get(0)).get("totalrows").toString());
			totalPage = Math.ceil(totalRows / rows) + 1;
		}

		jsonMap.put("records", totalRows);	// 총 개수
		jsonMap.put("total", totalPage);	// 총 페이지 수
		jsonMap.put("page", page);			// 현재 페이지
		jsonMap.put("CrossResult",CrossResult);

		model.addAttribute("jsonView", jsonMap);
		return model;
	}

	/**
	 * 통계관리 > 통계 이미지 임시 저장
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/statistics/imgUpload", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
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

		File outputfile = new File(serverPath + "exImg_stat.png");

		ImageIO.write(image, "png", outputfile);

		model.addAttribute("jsonView", jsonMap);

		return model;
	}

}
