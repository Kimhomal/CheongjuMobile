package tgis.system.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.common.annotation.AuthHandler;
import tgis.system.menu.service.MenuAuthService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
public class MenuAuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuAuthController.class);

	@Resource(name = "menuAuthService")
	private MenuAuthService masterService;

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002003")
	@RequestMapping(value = "/system/menu/menuAuthList.do")
	public String indexPage() throws Exception{
		return "system/menu/menuAuthList";
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지 > 메뉴권한리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002003")
	@RequestMapping(value = "/system/menu/menuAuthList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap menuAuthList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		//리스트조회
		List<?> resultList = masterService.menuAuthList(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultList);
//
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지 > 메뉴권한리스트 > 메뉴권한수정
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002003")
	@RequestMapping(value="/system/menu/menuAuthEdit", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public String menuAuthEdit(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try{
			masterService.menuAuthEdit(params);
			jsonMap.put("respFlag",  "Y");
		}catch(Exception e){
			jsonMap.put("respFlag",  "N");
		}

		model.addAttribute("jsonView", jsonMap);

		return "tgis/system/menu/menuAuth";
	}
}