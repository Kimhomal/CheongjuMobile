package tgis.search;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.search.service.SearchService;

@Controller
public class SearchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	@Resource(name = "searchService")
	private SearchService searchService;

	@AuthExclude
	@RequestMapping(value = "/search/search{method}.do")
	public String searchMenu(@PathVariable String method,@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
//		if(params.get("method").equals("roadWork")||params.get("method").equals("RoadAdd")){
//			List<?> fileList = roadFacilService.fileListInfo(params);
//			model.addAttribute("fileList", fileList);
//			List<?> stepList = roadFacilService.stepDetail(params);
//			model.addAttribute("stepList", stepList);
//		}
//
//		if(params.get("method").equals("ConstruAdd")||params.get("method").equals("ConstUpdate")||params.get("method").equals("ConstSelect")){
//			List<?> ctkfileList =  construfcltsService.selectCtkfileList(params);
//
//			model.addAttribute("ctkfileList", ctkfileList);
//			model.addAttribute("ctkfileListCnt", ctkfileList.size());
//		}
//		if(params.get("method").equals("ConstruCheck")||params.get("method").equals("ConstruAdd")){
//			if(!params.get("ctkVcnCde").equals(null) && !params.get("ctkVcnCde").equals("")){
//				params.put("scode", params.get("ctkVcnCde"));
//				construreviewService.updateTempCtkvcncde(params);//TBL_DXF_TEMP에 공사상태 업데이트
//				construreviewService.updateRuleCtkvcncde(params);//규제상태에서 공사상태 업데이트
//				construreviewService.updateCtkvcncde1(params);// 공사상태 업데이트
//			}
//		}

		model.addAttribute("params", params);
		return "search/search" + method ;
	}



	//교차로검색
	@AuthHandler(handler="M002001001")
	@RequestMapping(value = "/search/searchCross", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap searchCross(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = searchService.searchCross(params);

		double totalPage = 0.0;
		Long totalRows = 0L;

		if (resultList.size() > 0) {
			totalRows = Long.valueOf(((EgovMap) resultList.get(0)).get("totalrows").toString());
			totalPage = Math.ceil(totalRows / rows) + 1;
		}

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		params.put("key", "MGRNU");
		jsonMap.put("params", params); // 리스트
		jsonMap.put("records", totalRows); // 총 개수
		jsonMap.put("total", totalPage); // 총 페이지 수
		jsonMap.put("page", page); // 현재 페이지
		jsonMap.put("root", resultList); // 리스트
		jsonMap.put("pageName", "searchCross"); // 페이지네임

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	//카카오(지번/도로명)검색
	@AuthExclude
	@RequestMapping(value = "/search/searchDaumAddr", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap searchPoi(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			String query = (String) params.get("searchKeyword");
			if (!query.contains("청주")) {
				query = "청주 " + query;
				params.put("searchKeyword", query);
			}

			String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
			apiUrl += "?page=" + params.get("page");
			apiUrl += "&size=" + params.get("rows");
			apiUrl += "&query=" + URLEncoder.encode((String) params.get("searchKeyword"), "UTF-8");

			params.put("apiUrl", apiUrl);

			Map<String, Object> map = searchService.getDaumAddr(params);

			int page = Integer.parseInt(params.get("page").toString());
			int rows = Integer.parseInt(params.get("rows").toString());

			Long totalRows = Long.valueOf(map.get("totalRows").toString());
			double totalPage = 0.0;

			if (totalRows > 0) {
				totalPage = Math.ceil(totalRows / rows) + 1;
			}

			params.put("key", "MGRNU");
			jsonMap.put("params", params);				// 리스트
			jsonMap.put("records", totalRows); // 총 개수
			jsonMap.put("total", totalPage); // 총 페이지 수
			jsonMap.put("page", page); // 현재 페이지
			jsonMap.put("root", map.get("resultList")); // 리스트
			jsonMap.put("pageName", "searchDaumAddr");		// 페이지네임

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	//건물명 검색
	@AuthExclude
	@RequestMapping(value = "/search/searchKeyword", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap searchKeyword(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			String query = (String) params.get("searchKeyword");
			if (!query.contains("청주")) {
				query = "청주 " + query;
				params.put("searchKeyword", query);
			}

			String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";
			apiUrl += "?page=" + params.get("page");
			apiUrl += "&size=" + params.get("rows");
			apiUrl += "&query=" + URLEncoder.encode((String) params.get("searchKeyword"), "UTF-8");

			params.put("apiUrl", apiUrl);

			Map<String, Object> map = searchService.getKeyword(params);

			int page = Integer.parseInt(params.get("page").toString());
			int rows = Integer.parseInt(params.get("rows").toString());

			Long totalRows = Long.valueOf(map.get("totalRows").toString());
			double totalPage = 0.0;

			if (totalRows > 0) {
				totalPage = Math.ceil(totalRows / rows) + 1;
			}

			params.put("key", "SMRNO");
			jsonMap.put("params", params);				// 리스트
			jsonMap.put("records", totalRows); // 총 개수
			jsonMap.put("total", totalPage); // 총 페이지 수
			jsonMap.put("page", page); // 현재 페이지
			jsonMap.put("root", map.get("resultList")); // 리스트
			jsonMap.put("pageName", "searchKeyword");		// 페이지네임

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	//시설물 검색
	@RequestMapping(value = "/search/searchFacil", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap searchFacil(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		String facId = params.get("facil") == null ? params.get("facId").toString() : params.get("facil").toString();
		params.put("facil", facId);

		// 리스트조회
		List<?> resultList = searchService.selectFacil(params);

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
		jsonMap.put("params", params); // 리스트
		jsonMap.put("pageName", "searchFacil"); // 페이지네임

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}


}
