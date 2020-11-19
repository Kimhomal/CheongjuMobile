package tgis.fclts;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MethodInvoker;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.common.annotation.AuthExclude;
import tgis.common.util.com.ComStringUtils;
import tgis.common.util.com.EgovStringUtil;
import tgis.common.util.com.FileUtil;
import tgis.fclts.service.FcltsMngService;

@Controller
public class FcltsMngController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FcltsMngController.class);

	@Resource(name = "fcltsMngService")
	private FcltsMngService fcltsMngService;

	/**시설물 등록, 수정, 조회창 */
	@RequestMapping(value = "/fclts/modalInfoPop.do")
	public String initModalPage(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		String method = (String) params.get("method");

		if("select".equals(method)) {
			EgovMap result = fcltsMngService.selectFcltsInfo(params);

			model.addAttribute("result", result);
		}

		model.addAttribute("params", params);

		return "fclts/" + params.get("facilityId") + "/" + method + "InfoPop";

	}

	/** 시설물 등록, 수정, 삭제*/
	@RequestMapping(value = "/fclts/{method}Facility", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap insertFacility(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		int cnt = 0;
		String facId = params.get("facilityId") == null ? params.get("facId").toString() : params.get("facilityId").toString();
		params.put("facilityId", facId);

		try {
			if("insert".equals(method)) {
				//주소(시군구, 읍면동, 리, 지번, pnu)
				EgovMap addr = fcltsMngService.addr(params);
				params.put("guNam", addr.get("sigKorNm").toString());
				params.put("dongNam", addr.get("emdKorNm").toString());
				params.put("guCde", addr.get("sigCd").toString());
				params.put("dongCde", addr.get("emdCd").toString());
				String liKorNm = String.valueOf(addr.get("liKorNm"));
				if(!"null".equals(liKorNm)) {
					params.put("liNam", liKorNm);
				}

				String liCde = String.valueOf(addr.get("liCd"));
				if(!"null".equals(liCde)) {
					params.put("liCde", liCde);
				}

				params.put("pnu", addr.get("pnu").toString());
				params.put("jibun", addr.get("jibun").toString());

				//교차로
				EgovMap css = fcltsMngService.css(params);
				if(css!=null) {
					String cssNam = String.valueOf(css.get("cssNam"));
					if(!"null".equals(cssNam)) {
						params.put("cssNam", cssNam);
					}

					String cssMgrnu = String.valueOf(css.get("mgrnu"));
					if(!"null".equals(cssMgrnu)) {
						params.put("cssMgrnu", cssMgrnu);
					}
				}

				//어린이보호구역
				EgovMap sch = fcltsMngService.sch(params);
				if(sch!=null) {
					String schNam = String.valueOf(sch.get("schNam"));
					if(!"null".equals(schNam)) {
						params.put("schNam", schNam);
					}

					String schMgrnu = String.valueOf(sch.get("schMgrnu"));
					if(!"null".equals(schMgrnu)) {
						params.put("schMgrnu", schMgrnu);
					}
				}

				//경찰서
				EgovMap police = fcltsMngService.police(params);
				params.put("peCde", police.get("peCde").toString());
				params.put("peNam", police.get("peNam").toString());

				//도로
				EgovMap road = fcltsMngService.road(params);
				if(road!=null) {
					String mgrnu = String.valueOf(road.get("mgrnu"));
					if(!"null".equals(mgrnu)) {
						params.put("rodMgrnu", mgrnu);
					}

					String roadNam = String.valueOf(road.get("roadNam"));
					if(!"null".equals(roadNam)) {
						params.put("rodNam", roadNam);
					}
				}

  				cnt = fcltsMngService.insertFacility(params, request);
  				jsonMap.put("cnt", cnt);

			}else if("delete".equals(method)){
				params.put("facilityId", facId);
				cnt = fcltsMngService.deleteFacility(params,request);
				jsonMap.put("cnt", cnt);

			}else if("update".equals(method)) {
				params.put("facilityId", facId);

				//주소(시군구, 읍면동, 리, 지번, pnu)
				EgovMap addr = fcltsMngService.addr(params);
				params.put("guNam", addr.get("sigKorNm").toString());
				params.put("dongNam", addr.get("emdKorNm").toString());
				params.put("guCde", addr.get("sigCd").toString());
				params.put("dongCde", addr.get("emdCd").toString());
				String liKorNm = String.valueOf(addr.get("liKorNm"));
				if(!"null".equals(liKorNm)) {
					params.put("liNam", liKorNm);
				}

				String liCde = String.valueOf(addr.get("liCd"));
				if(!"null".equals(liCde)) {
					params.put("liCde", liCde);
				}

				params.put("pnu", addr.get("pnu").toString());
				params.put("jibun", addr.get("jibun").toString());

				//교차로
				EgovMap css = fcltsMngService.css(params);
				if(css!=null) {
					String cssNam = String.valueOf(css.get("cssNam"));
					if(!"null".equals(cssNam)) {
						params.put("cssNam", cssNam);
					}

					String cssMgrnu = String.valueOf(css.get("mgrnu"));
					if(!"null".equals(cssMgrnu)) {
						params.put("cssMgrnu", cssMgrnu);
					}
				}

				//어린이보호구역
				EgovMap sch = fcltsMngService.sch(params);
				if(sch!=null) {
					String schNam = String.valueOf(sch.get("schNam"));
					if(!"null".equals(schNam)) {
						params.put("schNam", schNam);
					}

					String schMgrnu = String.valueOf(sch.get("schMgrnu"));
					if(!"null".equals(schMgrnu)) {
						params.put("schMgrnu", schMgrnu);
					}
				}

				//경찰서
				EgovMap police = fcltsMngService.police(params);
				params.put("peCde", police.get("peCde").toString());
				params.put("peNam", police.get("peNam").toString());

				//도로
				EgovMap road = fcltsMngService.road(params);
				if(road!=null) {
					String mgrnu = String.valueOf(road.get("roadMgrnu"));
					if(!"null".equals(mgrnu)) {
						params.put("roadMgrnu", mgrnu);
					}

					String roadNam = String.valueOf(road.get("roadNam"));
					if(!"null".equals(roadNam)) {
						params.put("roadNam", roadNam);
					}
				}

				cnt = fcltsMngService.updateFacility(params,request);
				jsonMap.put("cnt", cnt);
			}

		}catch(Exception ex) {
			jsonMap.put("cnt", cnt);
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}

	//안전표지 이미지 가져오기
	@RequestMapping(value = "/fclts/A064_P/getSafetySignImg.do")
	public void getFeatureImage(@RequestParam HashMap<String,Object> params,HttpServletRequest request, ModelMap model, HttpServletResponse response) throws Exception{
		String uploadPath = EgovProperties.getProperty("Globals.fileStorePath") + "mark/";
		String fileNam = (String) params.get("mrkCde")+".png";

		FileUtil.setImage(ComStringUtils.filterSystemPath(uploadPath+fileNam),fileNam,response);
	}

	/**
	 * 안전표지 리스트 페이지
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fclts/A064_P/igtSignPopW.do")
	public String igtSignPopW(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "fclts/GT_A064_P/igtSignPopW";
	}

	/**
	 * 안전표지 리스트
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fclts/A064_P/getIgtSignList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap getIgtSignList(@RequestParam HashMap<String, Object> params, HttpServletRequest request, ModelMap model) throws Exception {

		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("page", page);
		params.put("rows", rows);

		List<?> resultList = fcltsMngService.getIgtSignList(params);
//
		double totalPage = 0.0;
		Long totalRows = 0L;

		if (resultList.size() > 0) {
			totalRows = Long.valueOf(((EgovMap) resultList.get(0)).get("totalrows").toString());
			totalPage = Math.ceil(totalRows / rows) + 1;
		}

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("records", totalRows); // 총 개수
		jsonMap.put("total", totalPage); // 총 페이지 수
		jsonMap.put("page", page); // 현재 페이지
		jsonMap.put("root", resultList); // 리스트

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}

	/**시설물 사진첨부 */
	@RequestMapping(value = "/fclts/photoPopE.do")
	public String photoPage() throws Exception {
		return "fclts/photoPopE";
	}


	/**시설물 사진첨부 */
	@RequestMapping(value = "/fclts/selectPhotoPopE.do")
	public String selectPhotoPopE(@RequestParam HashMap<String, Object> params, HttpServletRequest request, ModelMap model) throws Exception {
			List<?> fileList = fcltsMngService.selectFileInfo(params);
			if(fileList.size() == 0){
				params.put("fileYn", "N");
			}else{
				params.put("fileYn", "Y");
				model.addAttribute("fileList", fileList);
			}
			model.addAttribute("params", params);
		return "fclts/selectPhotoPopE";
	}

	/**시설물 사진첨부 */
	@RequestMapping(value = "/fclts/updatePhotoPopE.do")
	public String updatePhotoPopE(@RequestParam HashMap<String, Object> params, HttpServletRequest request, ModelMap model) throws Exception {
		List<?> fileList = fcltsMngService.selectFileInfo(params);
		if(fileList.size() == 0){
			params.put("fileYn", "N");
		}else{
			params.put("fileYn", "Y");
			model.addAttribute("fileList", fileList);
		}
		model.addAttribute("params", params);
		return "fclts/updatePhotoPopE";
	}




	/**
	 * 시설물 이미지 view
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/fclts/getFacImageView.do")
	public void geFactImageInf(@RequestParam HashMap<String,Object> params, HttpServletRequest request, ModelMap model, HttpServletResponse response, Map<String, Object> commandMap) throws Exception{
		String gbn = params.get("gbn").toString();

		EgovMap result = new EgovMap();

		if("atc".equals(gbn)) {
			result = fcltsMngService.selectReportFileInf(params);
		} else {
			result = fcltsMngService.selectFacFileInf(params);
		}

		File file = new File(result.get("filePath").toString(),result.get("fileNam").toString());
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream in = new BufferedInputStream(fis);
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();

		int imgByte;
		while ((imgByte = in.read()) != -1) {
			bStream.write(imgByte);
		}
		in.close();

		String type = "";
		String oglnam = result.get("oglNam").toString();

		String fileExt = oglnam.substring(oglnam.lastIndexOf('.'));

		type = "image/" + EgovStringUtil.lowerCase(fileExt);

		response.setContentType(type);

		response.setHeader("Content-Type", type);
		response.setContentLength(bStream.size());

		bStream.writeTo(response.getOutputStream());

		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	//이력조회창
	@RequestMapping(value = "/fclts/workListPopW.do")
	public String initPage(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		model.addAttribute("params", params);

		return "fclts/work/workListPopW";
	}

	//이력조회
	@RequestMapping(value = "/fclts/selectWorkList")
	public ModelMap selectWorkListPopW(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		if("select".equals(method)) {
			String facId = params.get("facilityId") == null ? params.get("facId").toString() : params.get("facilityId").toString();
			params.put("facilityId", facId);

			int rows = Integer.parseInt(params.get("rows").toString());
			int page = Integer.parseInt(params.get("page").toString());

			params.put("rows", rows);
			params.put("page", page);

			List<?> resultList = fcltsMngService.selectWorkList(params);

			double totalPage = 0.0;
			Long totalRows = 0L;

			if (resultList.size() > 0) {
				totalRows = Long.valueOf(((EgovMap) resultList.get(0)).get("totalrows").toString());
				totalPage = Math.ceil(totalRows / rows) + 1;

			}

			HashMap<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("records", totalRows); // 총 개수
			jsonMap.put("total", totalPage); // 총 페이지 수
			jsonMap.put("page", page); // 현재 페이지
			jsonMap.put("root", resultList); // 리스트

			model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		}

		return model;
	}

	//이력등록, 수정, 상세보기 창
	@RequestMapping(value = "/fclts/crudPopW.do")
	public String crudPage(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		if(!"insert".equals(method)) {
			String facId = params.get("facilityId") == null ? params.get("facId").toString() : params.get("facilityId").toString();
			params.put("facilityId", facId);
			EgovMap result = fcltsMngService.selectInfo(params);
			model.addAttribute("result", result);
		}


		model.addAttribute("params", params);

		return "fclts/work/" + method +"WorkListPopW";
	}

	//이력등록, 수정, 삭제
	@RequestMapping(value = "/fclts/{method}WorkList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		int cnt = 0;
		String facId = params.get("facilityId") == null ? params.get("facId").toString() : params.get("facilityId").toString();
		params.put("facilityId", facId);

		try {
			if("insert".equals(method)) {
				cnt = fcltsMngService.insertWorkList(params, request);
  				jsonMap.put("cnt", cnt);

			} else if("update".equals(method)) {
				cnt = fcltsMngService.updateWorkList(params, request);
  				jsonMap.put("cnt", cnt);
			} else {
				cnt = fcltsMngService.deleteWorkList(params, request);
  				jsonMap.put("cnt", cnt);
			}

		} catch(Exception ex) {
			jsonMap.put("cnt", cnt);
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}

}
