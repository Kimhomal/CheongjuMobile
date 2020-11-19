package tgis.user.auth.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("loginMapper")
public interface LoginMapper {

	LoginVO memberInfoById(LoginVO vo) throws Exception;

	void updateLoginSuccess(LoginVO vo) throws Exception;

	String selectConnDate(LoginVO vo) throws Exception;

	void insertLoginDate(LoginVO vo) throws Exception;

	void updateLoginDate(LoginVO vo) throws Exception;

	void updateLoginFail(LoginVO vo) throws Exception;

	void updateAccAdmFail(LoginVO vo) throws Exception;

	void updateLogoutDate(LoginVO vo) throws Exception;

}
