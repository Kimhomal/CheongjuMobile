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
package tgis.system.logMng;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.system.logMng.service.LogMngService;
import tgis.user.auth.service.AuthService;
import tgis.user.auth.service.LoginVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


/**
 * 프로그램 관리 요청을 처리하는 Controller 클래스
 *
 */
@Controller
public class LogMngController {

	@Resource(name = "logMngService")
	private LogMngService masterService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "authService")
	private AuthService authService;

	/**
	 * 관리자 > 이력관리 > 업무 이력 관리 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005001")
	@RequestMapping(value = "/system/logMng/adminUseLog.do")
	public String adminUseLog() throws Exception {
		return "system/logMng/adminUseLog";
	}

	/**
	 * 관리자 > 이력관리 > 업무 이력 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005001")
	@RequestMapping(value = "/system/logMng/adminUseLogList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap adminUseLogList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = masterService.adminUseLogList(params);

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
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005002")
	@RequestMapping(value = "/system/logMng/menuConnLog.do")
	public String menuConnLog() throws Exception {
		return "system/logMng/menuConnLog";
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 대 메뉴 조회 이력 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005002")
	@RequestMapping(value = "/system/logMng/menuLConnLog", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap menuLConnLog(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		// 리스트조회
		List<?> resultList = masterService.menuLConnLog(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultList); // 리스트

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 대 메뉴 이용자수 이력 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005002")
	@RequestMapping(value = "/system/logMng/menuLUseLog", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap menuLUseLog(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		// 리스트조회
		List<?> resultList = masterService.menuLUseLog(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultList); // 리스트

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 조회 이력 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005002")
	@RequestMapping(value = "/system/logMng/menuMConnLog", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap menuMConnLog(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {


		List<?> nameList = masterService.getMenuNmM(params);

		params.put("nameList",nameList);

		// 리스트조회
		List<?> resultList = masterService.menuMConnLog(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultList); // 리스트

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 이용자수 이력 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005002")
	@RequestMapping(value = "/system/logMng/menuMUseLog", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap menuMUseLog(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		List<?> nameList = masterService.getMenuNmM(params);

		params.put("nameList",nameList);

		// 리스트조회
		List<?> resultList = masterService.menuMUseLog(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultList); // 리스트

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 이용자수 이력 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010005002")
	@RequestMapping(value = "/system/logMng/getMenuList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap getMenuList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		List<?> nameList = masterService.getMenuNmM(params);

		params.put("nameList",nameList);

//		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
//		jsonMap.put("root", nameList); // 리스트

		model.addAttribute("jsonView", nameList); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}
	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 >
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/system/logMng/insertUseLog", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap insertUseLog(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {

		String usrId="";

		if(authService.isAuthenticated()){
			LoginVO vo = (LoginVO) request.getSession().getAttribute("loginVO");
			 usrId = vo.getUsrId();
		}else{
			usrId="guest";
		}

		String usrIp = request.getHeader("X-FORWARDED-FOR");
		if (usrIp == null) usrIp = request.getRemoteAddr();

		params.put("usrId", usrId);
		params.put("usrIp", usrIp);

		masterService.insertUseLog(params);

		return model;
	}


}
