package tgis.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.common.annotation.AuthExclude;
import tgis.map.service.MapActionService;

@Controller
public class MapActionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapActionController.class);

	@Resource(name = "mapActionService")
	private MapActionService mapActionService;

	@AuthExclude
	@RequestMapping(value = "/map/mapMain.do")
	public String indexPage(@RequestParam HashMap<String, Object> params, ModelMap model/*, HttpServletRequest request*/) throws Exception {

		model.addAttribute("params", params);

		return "map/mapMain";
	}

	//현재위치 가져오기
	@AuthExclude
	@RequestMapping(value = "/map/getCenterAddr", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap getCenterAddr(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		params.put("x", params.get("coord").toString().split(",")[0]);
		params.put("y", params.get("coord").toString().split(",")[1]);
		String addr = mapActionService.getCenterAddr(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("addr", addr);

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}

	//도로폭 조회
	@AuthExclude
	@RequestMapping(value = "/map/roadPop.do")
	public String roadPop(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		params.put("x", params.get("xce").toString());
		params.put("y", params.get("yce").toString());

		EgovMap road = mapActionService.roadPop(params);

		model.addAttribute("params", params);
		model.addAttribute("road", road);

		return "map/roadPop";
	}

	//다음 로드뷰
	@AuthExclude
	@RequestMapping(value = "/map/roadView.do")
	public String roadViewPage( @RequestParam HashMap<String, Object> params, ModelMap model ) throws Exception {

		model.addAttribute("params", params);

		return "map/roadView";
	}

	@RequestMapping(value = "/map/printMapPop.do")
	public String printMapPop( @RequestParam HashMap<String, Object> params, ModelMap model ) throws Exception {

		model.addAttribute("params", params);

		return "map/printMapPop";
	}


}
