package tgis.report.mmpMng;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.EgovExcel;
import tgis.report.mmpMng.service.MmpMngService;

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
public class MmpMngController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MmpMngController.class);
	
	@Resource(name = "mmpMngService")
	private MmpMngService mmpMngService;

	// 관리대장 > 월별 유지 관리 실적
	@AuthHandler(handler="M006001008")
	@RequestMapping(value = "/report/mmpMng/selectInfo.do")
	public String selectInfo() throws Exception {
		return "report/mmpMng/selectInfo";
	}

	// 관리대장 > 월별 유지 관리 실적 조회
	@AuthHandler(handler="M006001008")
	@RequestMapping(value = "/report/mmpMng/selectAllFacilPopE.do")
	public String allListPage(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		List<?> result = mmpMngService.selectFacilCntList(params);
		EgovMap monthCnt = mmpMngService.selectMonthCnt(params);
		EgovMap yearCnt = mmpMngService.selectYearCnt(params);

		HashMap<String, Object> cntMap = new HashMap<String, Object>();

		for(int i = 0; i < result.size(); i++) {
			EgovMap map = (EgovMap) result.get(i);

			String knd = map.get("knd").toString();

			if("제어기".equals(knd)) {
				cntMap.put("a061Cnt", map.get("cnt").toString());
			} else if("신호등".equals(knd)) {
				cntMap.put("a110Cnt", map.get("cnt").toString());
			} else if("지주".equals(knd)) {
				cntMap.put("a062Cnt", map.get("cnt").toString());
			} else if("부착대".equals(knd)) {
				cntMap.put("a057Cnt", map.get("cnt").toString());
			} else if("검지기".equals(knd)) {
				cntMap.put("a049Cnt", map.get("cnt").toString());
			} else if("안전표지".equals(knd)) {
				cntMap.put("a064Cnt", map.get("cnt").toString());
			} else if("중앙선".equals(knd)) {
				cntMap.put("a083Cnt", map.get("cnt").toString());
			} else if("차선".equals(knd)) {
				cntMap.put("a084Cnt", map.get("cnt").toString());
			} else if("주차금지".equals(knd)) {
				cntMap.put("a082Cnt", map.get("cnt").toString());
			} else if("유턴구역".equals(knd)) {
				cntMap.put("a079Cnt", map.get("cnt").toString());
			} else if("정지선".equals(knd)) {
				cntMap.put("a081Cnt", map.get("cnt").toString());
			} else if("노면방향표시".equals(knd)) {
				cntMap.put("a055Cnt", map.get("cnt").toString());
			} else if("노면문자표시".equals(knd)) {
				cntMap.put("a054Cnt", map.get("cnt").toString());
			} else if("횡단보도".equals(knd)) {
				cntMap.put("a004Cnt", map.get("cnt").toString());
			} else if("횡단보도예고표시".equals(knd)) {
				cntMap.put("a068Cnt", map.get("cnt").toString());
			} else if("정차금지지대".equals(knd)) {
				cntMap.put("a085Cnt", map.get("cnt").toString());
			} else if("주차구획".equals(knd)) {
				cntMap.put("a080Cnt", map.get("cnt").toString());
			} else if("CCTV".equals(knd)) {
				cntMap.put("a069Cnt", map.get("cnt").toString());
			} else if("전광판".equals(knd)) {
				cntMap.put("c097Cnt", map.get("cnt").toString());
			} else if("음향신호기".equals(knd)) {
				cntMap.put("a090Cnt", map.get("cnt").toString());
			} else if("과속방지턱".equals(knd)) {
				cntMap.put("a067Cnt", map.get("cnt").toString());
			} else if("도로반사경".equals(knd)) {
				cntMap.put("c051Cnt", map.get("cnt").toString());
			} else if("충격흡수시설".equals(knd)) {
				cntMap.put("c098Cnt", map.get("cnt").toString());
			} else if("장애물표적표시".equals(knd)) {
				cntMap.put("c096Cnt", map.get("cnt").toString());
			} else if("표지병".equals(knd)) {
				cntMap.put("a065Cnt", map.get("cnt").toString());
			} else if("시선유도봉".equals(knd)) {
				cntMap.put("c092Cnt", map.get("cnt").toString());
			} else if("갈매기표지".equals(knd)) {
				cntMap.put("c088Cnt", map.get("cnt").toString());
			} else if("방호울타리".equals(knd)) {
				cntMap.put("c059Cnt", map.get("cnt").toString());
			} else if("칼라포장".equals(knd)) {
				cntMap.put("c107Cnt", map.get("cnt").toString());
			} else if("고원식교차로".equals(knd)) {
				cntMap.put("c104Cnt", map.get("cnt").toString());
			} else if("교통섬".equals(knd)) {
				cntMap.put("c109Cnt", map.get("cnt").toString());
			} else if("조명시설".equals(knd)) {
				cntMap.put("c100Cnt", map.get("cnt").toString());
			} else if("미끄럼방지시설".equals(knd)) {
				cntMap.put("c091Cnt", map.get("cnt").toString());
			} else if("시선유도표지".equals(knd)) {
				cntMap.put("c093Cnt", map.get("cnt").toString());
			} else if("입체횡단시설".equals(knd)) {
				cntMap.put("c095Cnt", map.get("cnt").toString());
			} else if("어린이보호구역".equals(knd)) {
				cntMap.put("c101Cnt", map.get("cnt").toString());
			} else if("노인보호구역".equals(knd)) {
				cntMap.put("c115Cnt", map.get("cnt").toString());
			} else if("일방통행".equals(knd)) {
				cntMap.put("c103Cnt", map.get("cnt").toString());
			} else if("자전거전용도로".equals(knd)) {
				cntMap.put("c114Cnt", map.get("cnt").toString());
			}
		}

		model.addAttribute("monthCnt", monthCnt);
		model.addAttribute("yearCnt", yearCnt);
		model.addAttribute("cnt", cntMap);
		model.addAttribute("params", params);

		return "report/mmpMng/selectAllFacilPopE";
	}

	/**
	 * 관리대장 > 월별 유지 관리 실적 > 엑셀 다운로드
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/report/mmpMng/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<?> result = mmpMngService.selectFacilCntList(params);
			EgovMap monthCnt = mmpMngService.selectMonthCnt(params);
			EgovMap yearCnt = mmpMngService.selectYearCnt(params);

			String excelPath = request.getSession().getServletContext().getRealPath("/resource/report/");
			FileInputStream file = new FileInputStream(new File(excelPath + "월별유지관리실적.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);

			// 엑셀에 데이터 입력 (교통 지리 정보 시스템 월간 업무 실적)
			EgovExcel.writeExcelValue(sheet, 2, 6, params.get("monthPeriod"));	// 월간 > 기간
			EgovExcel.writeExcelValue(sheet, 4, 5, monthCnt.get("sum1"));		// 월간 > 교통안전 시설물 DB 구축(개수)
			EgovExcel.writeExcelValue(sheet, 4, 6, monthCnt.get("sum2"));		// 월간 > 교통안전 시설물 DB 구축(m)
			EgovExcel.writeExcelValue(sheet, 5, 5, monthCnt.get("sum3"));		// 월간 > 도로안전 시설물 DB 구축(개수)
			EgovExcel.writeExcelValue(sheet, 5, 6, monthCnt.get("sum4"));		// 월간 > 도로안전 시설물 DB 구축(m)
			EgovExcel.writeExcelValue(sheet, 6, 5, monthCnt.get("sum5"));		// 월간 > 특수안전 시설물 DB 구축(개수)
			EgovExcel.writeExcelValue(sheet, 6, 6, monthCnt.get("sum6"));		// 월간 > 특수안전 시설물 DB 구축(m)

			EgovExcel.writeExcelValue(sheet, 9, 3, yearCnt.get("sum1"));		// 연간 > DB 구축(개수)
			EgovExcel.writeExcelValue(sheet, 9, 5, yearCnt.get("sum2"));		// 연간 > DB 구축(m)

			// 엑셀에 데이터 입력 (교통 지리 정보 시스템 일일 업무 실적)
			EgovExcel.writeExcelValue(sheet, 12, 5, params.get("dayPeriod"));	// 일일 > 기간

			for(int i = 0; i < result.size(); i++) {
				EgovMap map = (EgovMap) result.get(i);

				String knd = map.get("knd").toString();

				if("제어기".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 14, 3, map.get("cnt"));
				} else if("신호등".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 15, 3, map.get("cnt"));
				} else if("지주".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 16, 3, map.get("cnt"));
				} else if("부착대".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 17, 3, map.get("cnt"));
				} else if("검지기".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 18, 3, map.get("cnt"));
				} else if("안전표지".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 19, 3, map.get("cnt"));
				} else if("중앙선".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 20, 3, map.get("cnt"));
				} else if("차선".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 21, 3, map.get("cnt"));
				} else if("주차금지".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 22, 3, map.get("cnt"));
				} else if("유턴구역".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 23, 3, map.get("cnt"));
				} else if("정지선".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 24, 3, map.get("cnt"));
				} else if("노면방향표시".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 25, 3, map.get("cnt"));
				} else if("노면문자표시".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 26, 3, map.get("cnt"));
				} else if("횡단보도".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 27, 3, map.get("cnt"));
				} else if("횡단보도예고표시".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 28, 3, map.get("cnt"));
				} else if("정차금지지대".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 29, 3, map.get("cnt"));
				} else if("주차구획".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 30, 3, map.get("cnt"));
				} else if("CCTV".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 31, 3, map.get("cnt"));
				} else if("전광판".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 32, 3, map.get("cnt"));
				} else if("음향신호기".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 33, 3, map.get("cnt"));
				} else if("과속방지턱".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 14, 7, map.get("cnt"));
				} else if("도로반사경".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 15, 7, map.get("cnt"));
				} else if("충격흡수시설".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 16, 7, map.get("cnt"));
				} else if("장애물표적표시".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 17, 7, map.get("cnt"));
				} else if("표지병".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 18, 7, map.get("cnt"));
				} else if("시선유도봉".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 19, 7, map.get("cnt"));
				} else if("갈매기표지".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 20, 7, map.get("cnt"));
				} else if("방호울타리".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 21, 7, map.get("cnt"));
				} else if("칼라포장".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 22, 7, map.get("cnt"));
				} else if("고원식교차로".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 23, 7, map.get("cnt"));
				} else if("교통섬".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 24, 7, map.get("cnt"));
				} else if("조명시설".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 25, 7, map.get("cnt"));
				} else if("미끄럼방지시설".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 26, 7, map.get("cnt"));
				} else if("시선유도표지".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 27, 7, map.get("cnt"));
				} else if("입체횡단시설".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 28, 7, map.get("cnt"));
				} else if("어린이보호구역".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 29, 7, map.get("cnt"));
				} else if("노인보호구역".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 30, 7, map.get("cnt"));
				} else if("일방통행".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 31, 7, map.get("cnt"));
				} else if("자전거전용도로".equals(knd)) {
					EgovExcel.writeExcelValue(sheet, 32, 7, map.get("cnt"));
				}
			}

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("월별유지관리실적", "UTF-8") + "_" + ComDateUtils.getCurDateTime() + ".xls" + ";");
			ServletOutputStream myOut = response.getOutputStream();
			wb.write(myOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}