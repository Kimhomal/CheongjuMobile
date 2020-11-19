package tgis.user.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tgis.user.auth.service.LoginMapper;
import tgis.user.auth.service.LoginService;
import tgis.user.auth.service.LoginVO;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Resource(name = "loginMapper")
	private LoginMapper loginMapper;
	
	//중복로그인 여부 확인
	public LoginVO memberInfoById(LoginVO vo) throws Exception {

		LoginVO loginVO = loginMapper.memberInfoById(vo);

		if (loginVO != null && !loginVO.getUsrId().equals("") && !loginVO.getPw().equals("")) {
			return loginVO;
		} else {
			loginVO = new LoginVO();
		}

		return loginVO;
	}

	public void updateLoginSuccess(LoginVO vo) throws Exception {
		loginMapper.updateLoginSuccess(vo);
	}
	
	public void updateLoginFail(LoginVO vo) throws Exception {
		loginMapper.updateLoginFail(vo);
	}
	
	public String selectConnDate(LoginVO vo) throws Exception {
		return loginMapper.selectConnDate(vo);
	}
	
	public void insertLoginDate(LoginVO vo) throws Exception {
		loginMapper.insertLoginDate(vo);
	}
	
	public void updateLoginDate(LoginVO vo) throws Exception {
		loginMapper.updateLoginDate(vo);
	}

	public void updateLogoutDate(LoginVO vo) throws Exception {
		loginMapper.updateLogoutDate(vo);
	}
	
	public void updateAccAdmFail(LoginVO vo) throws Exception {
		loginMapper.updateAccAdmFail(vo);
	}
	
	
	

}
