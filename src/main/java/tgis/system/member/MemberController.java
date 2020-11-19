package tgis.system.member;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhncorp.lucy.security.xss.XssPreventer;

import egovframework.com.cmm.service.EgovProperties;
import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.EgovStringUtil;
import tgis.common.util.encrypt.EgovFileScrty;
import tgis.common.util.encrypt.EncryptSDK;
import tgis.system.member.service.MemberService;
import tgis.user.auth.service.AuthService;
import tgis.user.auth.service.LoginVO;
import tgis.user.info.service.UserInfoService;

@Controller
public class MemberController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
	
	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "authService")
	private AuthService authService;

	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;

	/**
	 * 메인 > 회원가입신청 팝업
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value= "/system/member/joinMemberPopW.do")
	public String joinMemberPopW() throws Exception {
		return "system/member/joinMemberPopW";
	}

	/**
	 * 메인 > 회원가입신청 팝업 > 회원가입신청
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value="/system/member/joinMemberInfo")
	public ModelMap joinMemberInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
	
		try {
			if(params.get("pw") != null){
				params.put("pw", EgovFileScrty.encryptPassword((String)params.get("pw")));
			}

			// XSS Filter 적용
			params.put("usrId", XssPreventer.escape(params.get("usrId").toString()));
			params.put("usrNam", XssPreventer.escape(params.get("usrNam").toString()));
			params.put("usrPstnNam", XssPreventer.escape(params.get("usrPstnNam").toString()));
			params.put("buseNam", XssPreventer.escape(params.get("buseNam").toString()));

			memberService.joinMemberInfo(params,request);

			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		
		return model;
	}
	

	/**
	 * 관리자 > 사용자 관리 리스트 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value="/system/member/selectInfoList.do")
	public String indexPage(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception{
		LoginVO vo = (LoginVO) request.getSession().getAttribute("loginVO");

		params.put("usrId", (String)vo.getUsrId());
		params.put("connIp", (String)vo.getConnIp());

//		int checkUser = masterService.selectAccAdmMge(params);

//		if(checkUser == 1){
		return "system/member/selectInfoList";
//		} else {
//			return "index";
//		}
	}

	/**
	 * 관리자 > 사용자 관리 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/selectInfoList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String usrId = "";
		String usrPsn = "";
		if(authService.isAuthenticated()){
			LoginVO vo = (LoginVO) request.getSession().getAttribute("loginVO");
			usrId = vo.getUsrId();
			usrPsn = vo.getUsrPsn();
		}else{
			usrId="guest";
		}

		String usrIp = request.getHeader("X-FORWARDED-FOR");
		if (usrIp == null) usrIp = request.getRemoteAddr();

		params.put("usrId", usrId);
		params.put("connip", usrIp);
		params.put("usrpsn", usrPsn);
		params.put("busicontents", "사용자권한관리 페이지 접속");
		userInfoService.insertBusiContents(params);						// 관리자 페이지 업무내용 입력

		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = memberService.selectInfoList(params);

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
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 등록 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/insertInfoPopW.do")
	public String insertInfoPopW() throws Exception {
		return "system/member/insertInfoPopW";
	}

	/**
	 * 시스템관리 > 회원관리 > 중복확인
	 *
	 * @param params
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/system/member/userIdCheck", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap userIdCheck(@RequestParam HashMap<String, Object> params, HttpServletRequest request, ModelMap model) throws Exception {
		int idCount = memberService.userIdCheck(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("success", true);
		jsonMap.put("idChkFlag", idCount > 0 ? "N":"Y");

		model.addAttribute("jsonView", jsonMap);

		return model;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 등록
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/insertInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap insertInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if(params.get("pw") != null){
				params.put("pw", EgovFileScrty.encryptPassword((String)params.get("pw")));
			}

			// XSS Filter 적용
			params.put("usrId", XssPreventer.escape(params.get("usrId").toString()));
			params.put("usrNam", XssPreventer.escape(params.get("usrNam").toString()));
			params.put("usrPstnNam", XssPreventer.escape(params.get("usrPstnNam").toString()));
			params.put("buseNam", XssPreventer.escape(params.get("buseNam").toString()));

			memberService.insertInfo(params,request);
			jsonMap.put("respFlag", "Y");

			String usrId = "";
			String usrPsn = "";
			if(authService.isAuthenticated()){
				LoginVO vo = (LoginVO) request.getSession().getAttribute("loginVO");
				usrId = vo.getUsrId();
				usrPsn = vo.getUsrPsn();
			}else{
				usrId="guest";
			}

			String usrIp = request.getHeader("X-FORWARDED-FOR");
			if (usrIp == null) usrIp = request.getRemoteAddr();

			params.put("busicontents", "ID : " + params.get("usrId") + " 계정 등록");
			params.put("usrId", usrId);
			params.put("connip", usrIp);
			params.put("usrpsn", usrPsn);

			userInfoService.insertBusiContents(params);						// 관리자 페이지 업무내용 입력
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/updateInfoPopW.do")
	public String updateInfoPopW(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {

		String usrId = "";
		String usrPsn = "";
		if(authService.isAuthenticated()){
			LoginVO vo = (LoginVO) request.getSession().getAttribute("loginVO");
			usrId = vo.getUsrId();
			usrPsn = vo.getUsrPsn();
		}else{
			usrId="guest";
		}

		String usrIp = request.getHeader("X-FORWARDED-FOR");
		if (usrIp == null) usrIp = request.getRemoteAddr();

		params.put("usrId", usrId);
		params.put("connip", usrIp);
		params.put("usrpsn", usrPsn);

		EgovMap result = memberService.selectInfo(params);

		params.put("busicontents", "ID : " + result.get("usrId") + " 정보 열람");
		userInfoService.insertBusiContents(params);						// 관리자 페이지 업무내용 입력

		model.addAttribute("result", result);
		model.addAttribute("params", params);
		model.addAttribute("initPass", memberService.generatePasswd());
		return "system/member/updateInfoPopW";
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 수정
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/updateInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap updateInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if(params.get("pw") != null){
				params.put("pw", EgovFileScrty.encryptPassword((String)params.get("pw")));
			}

			// XSS Filter 적용
			params.put("usrNam", XssPreventer.escape(params.get("usrNam").toString()));
			params.put("usrPstnNam", XssPreventer.escape(params.get("usrPstnNam").toString()));
			params.put("buseNam", XssPreventer.escape(params.get("buseNam").toString()));
			params.put("rmk", XssPreventer.escape(params.get("rmk").toString()));

			memberService.updateInfo(params);

			String usrId = "";
			String usrPsn = "";
			if(authService.isAuthenticated()){
				LoginVO vo = (LoginVO) request.getSession().getAttribute("loginVO");
				usrId = vo.getUsrId();
				usrPsn = vo.getUsrPsn();
			}else{
				usrId="guest";
			}

			String usrIp = request.getHeader("X-FORWARDED-FOR");
			if (usrIp == null) usrIp = request.getRemoteAddr();

			params.put("usrId", usrId);
			params.put("connip", usrIp);
			params.put("usrpsn", usrPsn);

			EgovMap result = memberService.selectInfo(params);
			params.put("busicontents", "ID : "+ result.get("usrId") +" "+ params.get("busiContent"));
			userInfoService.insertBusiContents(params);						// 관리자 페이지 업무내용 입력

			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}
		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 차단
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/blockInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap blockInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			memberService.blockInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 비밀번호 초기화
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/updatePwInit", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap updatePwInit(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			params.put("pw", EgovFileScrty.encryptPassword((String)params.get("initPass")));
			memberService.updatePwInit(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 삭제
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/deleteInfo", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap deleteInfo(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			memberService.deleteInfo(params);
			jsonMap.put("respFlag", "Y");
		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 수정 페이지
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value="/system/member/usrMenuAuthPopW.do")
	public String usrMenuAuthPopW(@RequestParam HashMap<String, Object> params,ModelMap model) throws Exception{
		model.addAttribute("params", params);
		return "system/member/usrMenuAuthPopW";
	}

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 리스트 조회
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value = "/system/member/usrMenuAuthList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap usrMenuAuthList(@RequestParam HashMap<String, Object> params,ModelMap model) throws Exception{
		//리스트조회
		List<?> resultList = memberService.usrMenuAuthList(params);

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("root", resultList);

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 리스트 > 유저별 메뉴 권한 수정
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value="/system/member/usrMenuAuthEdit", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public String usrMenuAuthEdit(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		String respFlag = "N";

		try{
			memberService.usrMenuAuthEdit(params);
			respFlag = "Y";
		}catch(Exception e){
			LOGGER.debug("######### 사용자 권한 정보 수정에 실패하였습니다 ##########{}", e.toString());
		}
		jsonMap.put("respFlag", respFlag);

		model.addAttribute("jsonView", jsonMap);
		return "admin/member/memberMenuAuthPop";
	}

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 리스트 > 유저별 메뉴 권한 초기화(삭제)
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthHandler(handler="M010001000")
	@RequestMapping(value="/system/member/usrAuthInit", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public String usrAuthInit(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		String respFlag = "N";

		try{
			memberService.usrMenuAuthDel(params);
			respFlag = "Y";
		}catch(Exception e){
			LOGGER.debug("######### 사용자 권한 정보 초기화에 실패하였습니다 ##########{}", e.toString());
		}
		jsonMap.put("respFlag", respFlag);

		model.addAttribute("jsonView", jsonMap);
		return "admin/member/memberMenuAuthPop";
	}

}
