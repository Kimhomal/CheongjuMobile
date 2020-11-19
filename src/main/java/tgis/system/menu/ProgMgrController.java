/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:/www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tgis.system.menu;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.common.annotation.AuthHandler;
import tgis.system.menu.service.ProgMgrService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * 프로그램 관리 요청을 처리하는 Controller 클래스
 *
 */
@Controller
public class ProgMgrController {

	@Resource(name = "progMgrService")
	private ProgMgrService masterService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 관리자 > 프로그램 리스트 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002001")
	@RequestMapping(value = "/system/menu/progInfoList.do")
	public String baseProgList() throws Exception {
		return "system/menu/progInfoList";
	}

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 추가 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002001")
	@RequestMapping(value = "/system/menu/progRegPopW.do")
	public String progRegPopW() throws Exception {
		return "system/menu/progRegPopW";
	}

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 수정 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002001")
	@RequestMapping(value = "/system/menu/progUptPopW.do")
	public String progUptPopW(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		EgovMap result = masterService.selectProgInfo(params);
		model.addAttribute("result", result);
		model.addAttribute("params", params);
		return "system/menu/progUptPopW";
	}

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 추가
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002001")
	@RequestMapping(value="/system/menu/insertProgInfo", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap insertProgInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			masterService.insertProgInfo(params);
			jsonMap.put("respFlag",  "Y");
		}catch(Exception e){
			jsonMap.put("respFlag",  "N");
		}
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 수정
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002001")
	@RequestMapping(value="/system/menu/updateProgInfo", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap updateProgInfo(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			masterService.updateProgInfo(params);
			jsonMap.put("respFlag",  "Y");
		}catch(Exception e){
			jsonMap.put("respFlag",  "N");
		}
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 삭제
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010002001")
	@RequestMapping(value="/system/menu/deleteProgInfo",method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap deleteProg(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try{
			masterService.deleteProgInfo(params);
			jsonMap.put("respFlag",  "Y");
		}catch(Exception e){
			jsonMap.put("respFlag",  "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}

	//프로그램 리스트 조회
	@AuthHandler(handler="M010002001")
	@RequestMapping(value="/system/menu/progInfoList", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap menuList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		// 리스트조회
		List<?> resultList = masterService.selectProgList(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultList); // 리스트

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

// 프로그램목록 조회팝업
	@AuthHandler(handler="M010002001")
	@RequestMapping(value = "/system/menu/progSearchPopW.do")
	public String progSearchPopW(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		model.addAttribute("params", params);
		return "system/menu/progSearchPopW";
	}

	// 프로그램조회팝업 리스트
	@AuthHandler(handler="M010002001")
	@RequestMapping(value="/system/menu/progSearchList", method= RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap progSearchList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		//메뉴리스트조회
		List<?> resultProgList = masterService.progSearchList(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultProgList);

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함

		return model;
	}

}
