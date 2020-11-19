package tgis.user.info.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import tgis.user.auth.service.LoginVO;

@Mapper("UserInfoMapper")
public interface UserInfoMapper {

	String getUsrNam(HashMap<String, Object> params) throws Exception;

	public int selectPwCheck(HashMap<String, Object> params) throws Exception;

	public LoginVO updatePw(LoginVO vo) throws Exception;

	public void insertBusiContents(HashMap<String, Object> params) throws Exception;

	public int checkUser(LoginVO vo) throws Exception;

	// 사용자 정보 조회
	public LoginVO selectUsrInfo(LoginVO vo) throws Exception;

	public LoginVO updateUsrInfo(LoginVO vo) throws Exception;

	public void updateWithdraw(LoginVO vo) throws Exception;

}
