package tgis.user.auth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.slm.EgovHttpSessionBindingListener;
import egovframework.com.utl.slm.EgovMultiLoginPreventor;
import tgis.common.annotation.AuthExclude;
import tgis.common.util.com.ComObjectUtils;
import tgis.common.util.com.EgovStringUtil;
import tgis.common.util.encrypt.EgovFileScrty;
import tgis.common.util.encrypt.EncryptSDK;
import tgis.user.auth.service.LoginService;
import tgis.user.auth.service.LoginVO;
import tgis.user.auth.service.ResourceService;

@Controller
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
//	@Resource(name = "authService")
//	private AuthService authService;

	@Resource(name = "authResourceService")
	private ResourceService authResourceService;



	//로그인 페이지 이동
	@AuthExclude
	@RequestMapping(value="/user/auth/login.do")
	public String loginPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

		if(flashMap !=null) {
			model.addAttribute("LOGIN_MESSAGE", flashMap.get("LOGIN_MESSAGE"));
			if(flashMap.size()>1){
				model.addAttribute("loginId", flashMap.get("loginId"));
				model.addAttribute("LOGIN_MESSAGE", null);
				model.addAttribute("EXTENT_MESSAGE", flashMap.get("EXTENT_MESSAGE"));
			}
		}
		
		return "user/auth/login";
	}

	//중복로그인여부
	@AuthExclude
	@RequestMapping(value="/user/auth/checkLoginUser", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public ModelMap checkLoginUser(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception {

		String loginId = loginVO.getUsrId();
		String encPwd = EgovFileScrty.encryptPassword((String)loginVO.getPw());

		HashMap<String, String> rslt = new HashMap<String, String>();
		rslt.put("confirm", "N");
		rslt.put("loginId", loginId);
		LoginVO loginRsltVO = loginService.memberInfoById(loginVO); // 사용자 정보
		try {
			if (loginRsltVO != null && loginRsltVO.getUsrId() != null && !loginRsltVO.getUsrId().equals("")){
				if (!encPwd.equals(loginRsltVO.getPw())) {//암호화된 비밀번호 확인
					rslt.put("confirm", "S");
					rslt.put("message","아이디 또는 비밀번호를 다시 확인하세요." + "시스템에 등록되지 않은 아이디이거나," + "아이디 또는 비밀번호를 잘못 입력하셨습니다.");
				}else{
					if (loginId != null && EgovMultiLoginPreventor.findByLoginId(loginId)) {
						rslt.put("confirm", "Y");
						rslt.put("message", "해당ID는 다른 사용자가 사용중입니다. 기존 접속을 끊고 로그인하시겠습니까?");
					}
				}
			} else {
				rslt.put("confirm", "S");
				rslt.put("message","아이디 또는 비밀번호를 다시 확인하세요." + "시스템에 등록되지 않은 아이디이거나," + "아이디 또는 비밀번호를 잘못 입력하셨습니다.");
			}
		} catch (Exception ex) {
			LOGGER.debug("EgovMultiLoginPreventor.findByLoginId [" + loginId + "]", ex);
		}
		model.addAttribute("jsonView", rslt);
		return model;
	}
	
	//로그인실행
	@AuthExclude
	@RequestMapping(value = "/user/auth/loginAction.do")
	public String loginAction(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {

		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);

		LoginVO loginRsltVO = new LoginVO();

		String loginMsg = null;

		String encPwd = EgovFileScrty.encryptPassword((String)loginVO.getPw());

		loginRsltVO = loginService.memberInfoById(loginVO); // 사용자 정보

		loginVO.setPw(encPwd);

		if (loginRsltVO != null && loginRsltVO.getUsrId() != null && !loginRsltVO.getUsrId().equals("")){
				if (!encPwd.equals(loginRsltVO.getPw())) {//암호화된 비밀번호 확인
						loginMsg = "아이디 또는 비밀번호를 다시 확인하세요.\\n"+"시스템에 등록되지 않은 아이디이거나,\\n"+"아이디 또는 비밀번호를 잘못 입력하셨습니다.";
				}
			if ("Y".equals(loginRsltVO.getYno())){//아이디 사용여부
				loginVO.setLoginFailCnt(0);
				loginVO.setLatestLoginDate(EgovStringUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));	// 로그인 성공 업데이트 20170725_lsg
				loginService.updateLoginSuccess(loginVO);//로그인 성공 날짜 입력 및 실패 카운터 초기화

				loginRsltVO.setConnIp(request.getRemoteAddr());  //접속자IP로 세팅  // request.getRemoteAddr());
				String conDate = loginService.selectConnDate(loginRsltVO);

				if ( EgovStringUtil.isEmpty(conDate) ) {
					loginService.insertLoginDate(loginRsltVO);
				} else {
					loginService.updateLoginDate(loginRsltVO);
				}

				EgovHttpSessionBindingListener listener = new EgovHttpSessionBindingListener(); // 중복세션 처리
				request.getSession().setAttribute(loginRsltVO.getUsrId(), listener); // 일단 주석처리

//				String key = EgovProperties.getProperty("Globals.AriaKey");

//				String buseNam = String.valueOf(loginRsltVO.getBuseNam());

//				if(!("null").equals(buseNam)) {
//						loginRsltVO.setBuseNam(EncryptSDK.decrypt(loginRsltVO.getBuseNam(), key));
//				}
//				loginRsltVO.setEmail(EncryptSDK.decrypt(loginRsltVO.getEmail(), key));
//				loginRsltVO.setUsrTel(EncryptSDK.decrypt(loginRsltVO.getUsrTel(), key));

				request.getSession().setAttribute("auth", ComObjectUtils.ObjectToMap(loginRsltVO));
				request.getSession().setAttribute("loginVO", loginRsltVO);

				authResourceService.setSessionAuthResource(loginRsltVO); // 접속권한정보 세션저장

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				Date today = new Date();
				String pwChgYmd = String.valueOf(loginRsltVO.getPwChgYmd());	// 비밀번호 변경일자

				if(!("null").equals(pwChgYmd)) {
					Date chgYmd = df.parse(pwChgYmd);

					long diff = today.getTime() - chgYmd.getTime();
					long diffDays = diff / (24 * 60 * 60 * 1000) + 1;

					if(diffDays >= 90) {	// 비밀번호 변경일자가 90일 이상 됐을 경우
						return "redirect:/user/info/usrPwMod.do";
					}
				}

				return "redirect:/index.do"; // 로그인 성공
			}else{
				loginMsg = "아이디 또는 비밀번호를 다시 확인하세요.\\n"+"시스템에 등록되지 않은 아이디이거나,\\n"+"아이디 또는 비밀번호를 잘못 입력하셨습니다.";
			}
		}

		//10분 시간 변수 설정
		int failcnt = loginRsltVO.getLoginFailCnt();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = formatter.parse(EgovStringUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
		Date failDate = formatter.parse(EgovStringUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
		if(loginRsltVO.getLatestFailDate()==null){
			failDate = formatter.parse(EgovStringUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
		}else{
			failDate = formatter.parse(loginRsltVO.getLatestFailDate());
		}
		long soyotime = (nowDate.getTime() - failDate.getTime()) / (60 * 1000);

		if(loginMsg != null){//로그인 실패시
			loginRsltVO.setLoginFailCnt(failcnt + 1);																					// 로그인 실패 업데이트 20170725_lsg
			loginRsltVO.setLatestFailDate(EgovStringUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));	// 로그인 실패 업데이트	// 로그인 실패 업데이트 20170725_lsg
			loginService.updateLoginFail(loginRsltVO);//실패카운터 1추가 및 로그인 실패 날짜
		}
		if(failcnt >= 5){
			if(soyotime <= 1){//시간이 10분 안지났을때
				loginService.updateAccAdmFail(loginRsltVO);
				loginMsg = "10분뒤 로그인 시도를 해주세요.";
			}else{
				loginRsltVO.setYno("Y");//시간이 10분 지났을때
				loginRsltVO.setLoginFailCnt(0);	// 로그인 실패 업데이트 20170725_lsg
				loginRsltVO.setLatestFailDate(EgovStringUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));	// 로그인 실패 업데이트	// 로그인 실패 업데이트 20170725_lsg
				loginService.updateLoginFail(loginRsltVO);
			}
		}

		flashMap.put("LOGIN_MESSAGE", loginMsg);
		return "redirect:/user/auth/login.do";
	}

	//로그아웃
    @AuthExclude
	@RequestMapping(value = "/user/auth/logout.do")
	public String logout(HttpServletRequest request, ModelMap model) throws Exception {

		if(request.getSession().getAttribute("loginVO") != null){
			LoginVO vo = (LoginVO) request.getSession().getAttribute("loginVO");
			vo.setLatestLoginDate(EgovStringUtil.removeMinusChar(vo.getLatestLoginDate()));
			loginService.updateLogoutDate(vo);
			request.getSession().setAttribute(vo.getUsrId(), null);
		}

		request.getSession().setAttribute("auth", null);
		request.getSession().setAttribute("loginVO", null);
		request.getSession().setAttribute("authResourceVO", null);
		request.getSession().invalidate();
		return "redirect:/index.do";

	}

	/**
	 * 이용안내 > 이용약관
	 *
	 * @return
	 * @throws Exception
	 */
	@AuthExclude
	@RequestMapping(value = "/user/auth/termsOfUse.do")
	public String termsOfUse() throws Exception{
		return "user/auth/termsOfUse";
	}

}
