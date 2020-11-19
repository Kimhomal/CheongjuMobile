package tgis.board.traffic;

import com.nhncorp.lucy.security.xss.XssPreventer;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.board.traffic.service.TrafficService;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.FileUtil;
import tgis.user.auth.service.ResourceVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;


@Controller
public class TrafficController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TrafficController.class);
	
	@Resource(name = "trafficService")
	private TrafficService trafficService;

	//연도별 교통량 조회 페이지
	@AuthHandler(handler="M008003000")
	@RequestMapping(value = "/board/traffic/selectTrafficList.do")
	public String trafficPage() throws Exception {
		return "board/traffic/selectInfoList";
	}

	//연도별 교통량 조회
	@AuthHandler(handler="M008003000")
	@RequestMapping(value = "/board/traffic/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = trafficService.selectInfoList(params);

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

	//등록, 수정, 상세보기창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M008003000")
	@RequestMapping(value = "/board/traffic/crudInfo.do")
	public String initPage(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		if (!"insert".equals(method)) {
			EgovMap result = trafficService.selectInfo(params);
			if(Integer.parseInt((result.get("filecnt").toString()))>0){
				List<?> fileList = trafficService.selectFileInfo(params);
				params.put("fileYn", "Y");
				model.addAttribute("fileList", fileList);
			}
			model.addAttribute("result", result);
		}

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M008003000");

		return "board/traffic/" + method + "Info";
	}

	@RequestMapping(value = "/board/traffic/fileDown.do")
	public void fileDown(@RequestParam HashMap<String, Object> params, HttpServletResponse response) throws Exception {
		String path = EgovProperties.getProperty("Globals.fileStorePath") + params.get("path").toString();
		String rlFileNam = params.get("rlFileNam").toString();
		String svFileNam = params.get("svFileNam").toString();
		String fname = URLEncoder.encode(svFileNam, "utf-8");

		response.setCharacterEncoding("UTF-8");
		response.setHeader("Accept-Ranges", "bytes");
		rlFileNam = URLEncoder.encode(rlFileNam, "UTF-8").replace("+","%20");

		response.setHeader("Content-Type", "application/octet-stream;charset=utf-8;");
		response.setHeader("Content-Disposition", "attachment;filename=" + rlFileNam + ";");

		FileUtil.fileDown(path, fname, response);
	}

	//등록,수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M008003000")
	@RequestMapping(value = "/board/traffic/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("insert".equals(method)) {
				HashMap<String, Object> usrInfo = (HashMap<String, Object>)request.getSession().getAttribute("auth");
				String usrId = (String) usrInfo.get("usrId");

				params.put("usrId", usrId);
				int ntMaxId = trafficService.selectTrafficMaxID(params);
				params.put("trafficid", ntMaxId);

				// XSS Filter 적용
				params.put("subject", XssPreventer.escape(params.get("subject").toString()));
				params.put("writerNm", XssPreventer.escape(params.get("writerNm").toString()));
				params.put("buseNam", XssPreventer.escape(params.get("buseNam").toString()));
				params.put("ctt", XssPreventer.escape(params.get("ctt").toString()));

				trafficService.insertInfo(params, request);
			} else if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("subject", XssPreventer.escape(params.get("subject").toString()));
				params.put("writerNm", XssPreventer.escape(params.get("writerNm").toString()));
				params.put("buseNam", XssPreventer.escape(params.get("buseNam").toString()));
				params.put("ctt", XssPreventer.escape(params.get("ctt").toString()));

				trafficService.updateInfo(params, request);
			} else {
				trafficService.deleteInfo(params);
			}
			jsonMap.put("respFlag", "Y");

		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

}
