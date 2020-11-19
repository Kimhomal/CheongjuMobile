package tgis.user.info.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tgis.common.util.encrypt.EgovFileScrty;
import tgis.user.auth.service.LoginVO;
import tgis.user.info.service.UserInfoMapper;
import tgis.user.info.service.UserInfoService;



@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	
	@Resource(name="UserInfoMapper")
    private UserInfoMapper userInfoMapper;
	
	public String getUsrNam(HashMap<String, Object> params) throws Exception {
		return userInfoMapper.getUsrNam(params);
	}

	public int selectPwCheck(HashMap<String, Object> params) throws Exception {
		return userInfoMapper.selectPwCheck(params);
	}

	public LoginVO updatePw(LoginVO vo) throws Exception {
		return userInfoMapper.updatePw(vo);
	}

	public void insertBusiContents(HashMap<String, Object> params) throws Exception{
		userInfoMapper.insertBusiContents(params);
	}

	public int checkUser(LoginVO vo) throws Exception {
		String pw = EgovFileScrty.encryptPassword(vo.getPw().toString());
		vo.setPw(pw);

		return userInfoMapper.checkUser(vo);
	}

	// 사용자 정보 조회
	public LoginVO selectUsrInfo(LoginVO vo) throws Exception {
		return userInfoMapper.selectUsrInfo(vo);
	}

	/**
	 * 사용자 정보를 수정한다.
	 * @param vo - 수정할 정보가 담긴 LoginVO
	 * @return void형
	 * @exception Exception
	 */
	public LoginVO updateUsrInfo(LoginVO vo) throws Exception {
		return userInfoMapper.updateUsrInfo(vo);
	}

	public void updateWithdraw(LoginVO vo) throws Exception{
		userInfoMapper.updateWithdraw(vo);
	}

}
