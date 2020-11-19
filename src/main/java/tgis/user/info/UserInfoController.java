package tgis.user.info;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nhncorp.lucy.security.xss.XssPreventer;
import egovframework.com.cmm.service.EgovProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tgis.common.annotation.AuthExclude;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.encrypt.EgovFileScrty;
import tgis.common.util.encrypt.EncryptSDK;
import tgis.system.menu.AuthInfoController;
import tgis.user.auth.service.LoginService;
import tgis.user.auth.service.LoginVO;
import tgis.user.info.service.UserInfoService;

@Controller
public class UserInfoController {
	
	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;

	@Resource(name = "loginService")
	private LoginService loginService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInfoController.class);

	/**
	 * 비밀번호 변경 페이지 이동(변경 후 90일 이상)
	 */
	@AuthExclude
	@RequestMapping(value = "/user/info/usrPwMod.do")
	public String usrPwMod(HttpServletRequest request, ModelMap model) throws Exception {
		LoginVO vo = (LoginVO)request.getSession().getAttribute("loginVO");
		model.addAttribute("result", vo);
		return "user/info/usrPwMod";
	}

	/**
	 * 비밀번호 변경한다.
	 * @param loginVO - 수정할 정보가 담긴 VO
	 * @param params
	 * @return "forward:/mainPage.do"
	 * @exception Exception
	 */
//	@AuthHandler(handler="M013002000")
	@RequestMapping(value = "/user/info/updatePw", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap updatePw(@ModelAttribute("loginVO") LoginVO loginVO, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try{
			LoginVO vo = (LoginVO)request.getSession().getAttribute("loginVO");

			loginVO.setUsrId(vo.getUsrId());
			params.put("usrId", vo.getUsrId());

			String crntPw = params.get("crntPw").toString();
			String chgPw = params.get("chgPw").toString();
			String crntPwEnc = EgovFileScrty.encryptPassword(crntPw);
			params.put("crntPwEnc", crntPwEnc);

			int pwChk = userInfoService.selectPwCheck(params);

			if(pwChk == 0) {
				jsonMap.put("respFlag", "F");
			} else {
				if(crntPw.equals(chgPw)) {
					jsonMap.put("respFlag", "S");
				} else {
					String pw = (String) loginVO.getPw();
					loginVO.setPw(EgovFileScrty.encryptPassword(pw));
					userInfoService.updatePw(loginVO);

					jsonMap.put("respFlag", "Y");
				}
			}
		}catch (Exception ex){
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	/**
	 * 사용자 개인정보 조회 전 인증 페이지 이동
	 */
	@AuthHandler(handler="M012001000")
	@RequestMapping(value = "/user/info/usrInfoChk.do")
	public String usrInfoChk(HttpServletRequest request, ModelMap model) throws Exception {
		LoginVO vo = (LoginVO)request.getSession().getAttribute("loginVO");
		model.addAttribute("result", vo);
		return "user/info/usrInfoChk";
	}

	/**
	 * 사용자 인증을 처리한다.
	 */
	@AuthHandler(handler="M012001000")
	@RequestMapping(value = "/user/info/usrChkAction.do")
	public String usrChkAction(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {
		int checkUser = userInfoService.checkUser(loginVO);
		if(checkUser == 0) {
			return "redirect:/user/info/usrInfoChk.do";
		}
		return "redirect:/user/info/modUserInfo.do#M009001000";
	}

	@AuthHandler(handler="M009001000")
	@RequestMapping(value = "/user/info/modUserInfo.do")
	public String modUserInfo(HttpServletRequest request, ModelMap model) throws Exception {
		LoginVO vo = userInfoService.selectUsrInfo( (LoginVO)request.getSession().getAttribute("loginVO") );
		model.addAttribute("result", vo);

		return "user/info/modUserInfo";
	}

	/**
	 * 회원정보를 수정한다.
	 * @param loginVO - 수정할 정보가 담긴 VO
	 * @return "forward:/mainPage.do"
	 * @exception Exception
	 */
	@AuthHandler(handler="M013001000")
	@RequestMapping("/user/info/usrUdt.do")
	public String updateUsrInfo(@ModelAttribute("loginVO") LoginVO loginVO, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		LoginVO vo = (LoginVO)request.getSession().getAttribute("loginVO");

		loginVO.setUsrId(vo.getUsrId());

		// XSS Filter 적용
		params.put("usrNam", XssPreventer.escape(params.get("usrNam").toString()));
		params.put("usrPstnNam", XssPreventer.escape(params.get("usrPstnNam").toString()));
		params.put("buseNam", XssPreventer.escape(params.get("buseNam").toString()));

		// 회원정보를 수정한다.
		userInfoService.updateUsrInfo(loginVO);

		// 수정한 정보를 세션에 저장
		LoginVO loginRsltVO = userInfoService.selectUsrInfo(loginVO);

		request.getSession().setAttribute("loginVO", loginRsltVO);

		return "forward:/user/info/modUserInfo.do";
	}

	@AuthHandler(handler="M009002000")
	@RequestMapping(value = "/user/info/usrPwudt.do")
	public String usrPwudt(HttpServletRequest request, ModelMap model) throws Exception {

		LoginVO vo = (LoginVO)request.getSession().getAttribute("loginVO");
		model.addAttribute("result", vo);
		return "user/info/usrPwudt";
	}

	@AuthHandler(handler="M009003000")
	@RequestMapping(value = "/user/info/usrAuth.do")
	public String usrAuth() throws Exception {
		return "user/info/usrAuth";
	}

	@AuthHandler(handler="M009004000")
	@RequestMapping(value = "/user/info/memWithdraw.do")
	public String memWithdraw(HttpServletRequest request, ModelMap model) throws Exception {
		LoginVO vo = (LoginVO)request.getSession().getAttribute("loginVO");
		model.addAttribute("result", vo);
		return "user/info/memWithdraw";
	}

	@AuthExclude
	@RequestMapping("/user/info/usrWithdraw.do")
	public String updateWithdraw(@ModelAttribute("loginVO") LoginVO loginVO,ModelMap model,HttpServletRequest request) throws Exception {
		LoginVO resultVO = loginService.memberInfoById(loginVO); // 사용자 정보
		if (resultVO != null && resultVO.getUsrId() != null && !resultVO.getUsrId().equals("")&& !resultVO.getYno().equals("N")){
			String encPwd = EgovFileScrty.encryptPassword((String)loginVO.getPw());
			if (encPwd.equals(resultVO.getPw())) {//암호화된 비밀번호 확인
				userInfoService.updateWithdraw(loginVO);
				return "redirect:/user/auth/logout.do";
			}
		}

		return "forward:/user/info/memWithdraw.do";
	}

}
