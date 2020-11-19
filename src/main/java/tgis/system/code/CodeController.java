package tgis.system.code;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.common.annotation.AuthHandler;
import tgis.system.code.service.CodeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 메뉴관리 > 코드관리를 처리하는 Controller 클래스
 *
 */
@Controller
public class CodeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

	@Resource(name = "codeService")
	private CodeService masterService;

	/**
	 * 메뉴관리 > 코드관리 초기 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010004000")
	@RequestMapping(value = "/system/code/selectInfoList.do")
	public String initListPage() throws Exception {
		return "system/code/selectInfoList";
	}

	/**
	 * 메뉴관리 > 코드관리 리스트 조회
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010004000")
	@RequestMapping(value = "/system/code/selectInfoList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = masterService.selectInfoList(params);

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

	/**
	 * 메뉴관리 > 코드관리 > 코드 수정 팝업
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010004000")
	@RequestMapping(value = "/system/code/updateCodePopW.do")
	public String updateCodePopW(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		EgovMap result = masterService.selectInfo(params);
		model.addAttribute("result", result);
		model.addAttribute("params", params);
		return "system/code/updateCodePopW";
	}

	/**
	 * 메뉴관리 > 코드관리 > 코드 등록 팝업
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010004000")
	@RequestMapping(value = "/system/code/insertCodePopW.do")
	public String insertCodePopW() throws Exception {
		return "system/code/insertCodePopW";
	}


	/**
	 * 메뉴관리 > 코드관리 > 코드 등록
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010004000")
	@RequestMapping(value = "/system/code/insertCodeInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap insertCodeInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			masterService.insertInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 메뉴관리 > 코드관리 > 코드 수정
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010004000")
	@RequestMapping(value = "/system/code/updateCodeInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap updateCodeInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			masterService.updateInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 메뉴관리 > 코드관리 > 코드 삭제
	 *
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010004000")
	@RequestMapping(value = "/system/code/deleteCodeInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap deleteCodeInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			masterService.deleteInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}
}