package tgis.user.info.service;

import tgis.user.auth.service.LoginVO;

import java.util.HashMap;
import java.util.List;

public interface UserInfoService {

	String getUsrNam(HashMap<String, Object> params) throws Exception;

	public int selectPwCheck(HashMap<String, Object> params) throws Exception;

	public LoginVO updatePw(LoginVO vo) throws Exception;

	public void insertBusiContents(HashMap<String, Object> params) throws Exception;

	public int checkUser(LoginVO vo) throws Exception;

	public LoginVO selectUsrInfo(LoginVO vo) throws Exception;

	/**
	 * 회원정보를 수정한다.
	 * @param vo - 수정할 정보가 담긴 NoticeVO
	 * @return void형
	 * @exception Exception
	 */
	public LoginVO updateUsrInfo(LoginVO vo) throws Exception;

	public void updateWithdraw(LoginVO vo) throws Exception;

}
