package tgis.system.menu;

import java.util.HashMap;
import java.util.List;

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
import tgis.system.menu.service.AuthInfoService;

@Controller
public class AuthInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInfoController.class);

	@Resource(name = "authInfoService")
	private AuthInfoService authInfoService;

	@AuthHandler(handler="M010003000")
	@RequestMapping(value = "/system/menu/authInfoList.do")
	public String baseAuthInfo() throws Exception{
		return "system/menu/authInfoList";
	}

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 리스트 조회
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010003000")
	@RequestMapping(value = "/system/menu/authInfoList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = authInfoService.selectInfoList(params);

		double totalPage = 0.0;
		Long totalRows = 0L;

		if (resultList.size() > 0) {
			totalRows = Long.valueOf(resultList.size());
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

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 > 등록, 수정, 상세조회 창
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010003000")
	@RequestMapping(value = "/system/menu/authCUInfoPopW.do")
	public String initModalPage(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		String method = (String) params.get("method");

		if (!"insert".equals(method)) {
			EgovMap result = authInfoService.selectInfo(params);
			model.addAttribute("result", result);
		}

		model.addAttribute("params", params);

		return "system/menu/authCUInfoPopW";
	}

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 > 등록, 수정, 삭제
	 *
	 * @param method
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010003000")
	@RequestMapping(value = "/system/menu/{method}AuthInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("insert".equals(method)) {
				authInfoService.insertInfo(params);
			} else if ("update".equals(method)) {
				authInfoService.updateInfo(params);
			} else {
				authInfoService.deleteInfo(params);
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