/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tgis.system.menu;

import egovframework.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.system.menu.service.MenuInfoService;
import tgis.user.auth.service.AuthService;
import tgis.user.auth.service.LoginVO;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * 메뉴 관리 요청을 처리하는 Controller 클래스
 *
 */
@Controller
public class MenuInfoController {

	@Resource(name = "menuInfoService")
	private MenuInfoService menuInfoService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "authService")
	private AuthService authService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInfoController.class);

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value = "/system/menu/menuInfoList.do")
	public String indexPage() throws Exception {
		return "system/menu/menuInfoList";
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value="/system/menu/menuInfoList", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap menuInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		//메뉴목록조회
		List<?> resultMenuList = menuInfoService.menuInfoList(params);
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultMenuList);
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value = "/system/menu/menuInfoRegPopW.do")
	public String menuInfoRegPopW(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "system/menu/menuInfoRegPopW";
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 추가
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value="/system/menu/insertMenuInfo", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap insertMenuInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			menuInfoService.insertMenuInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}
		model.addAttribute("jsonView", jsonMap);
		return model;
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 수정 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value = "/system/menu/menuInfoUptPopW.do")
	public String menuInfoUptPopW(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "system/menu/menuInfoUptPopW";
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 추가
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value="/system/menu/updateMenuInfo", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap updateMenuInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			menuInfoService.updateMenuInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}
		model.addAttribute("jsonView", jsonMap);
		return model;
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 삭제
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value="/system/menu/deleteMenuInfo", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap deleteMenuInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			menuInfoService.deleteMenuInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}
		model.addAttribute("jsonView", jsonMap);
		return model;
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 순서 이동
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002002")
	@RequestMapping(value="/system/menu/moveMenuInfo", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap moveMenu(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		 int rtnValue = menuInfoService.menuMoveUdt(params);
//		jsonMap.put("success",  true);
		jsonMap.put("moveResult",  rtnValue);
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	//대메뉴콤보
	@AuthHandler(handler="M010002002")
	@RequestMapping(value="/system/menu/menuInfoParseCombo", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap menuInfoParseLvlCombo(ModelMap model) throws Exception {
		//메뉴리스트조회
		List<?> resultMenuCombo = menuInfoService.menuInfoParseLvlCombo();

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("success",  true);
		jsonMap.put("result_list", resultMenuCombo);

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}

	//중메뉴콤보
	@AuthHandler(handler="M010002002")
	@RequestMapping(value="/system/menu/menuInfoLevel2Combo", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap menuInfoLevel2Combo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		//메뉴리스트조회
		List<?> resultMenuL2Combo = menuInfoService.menuInfoLevel2Combo(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("success",  true);
		jsonMap.put("result_list", resultMenuL2Combo);

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}




	/**
	 *  권한별 메뉴 정보
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value="/system/menu/userMenu.json", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap userMenuJson(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		if(!authService.isAuthenticated()){ // 비로그인
			params.put("roleId", "R0002");
		}else{
			LoginVO loginVo =	authService.getAuthenticatedUser();
			params.put("usrId",loginVo.getUsrId());
		}

		List<?> userMenuList = menuInfoService.getUserMenuList(params);

		model.addAttribute("jsonView", userMenuList);

		return model;
	}
}
