package tgis.user.auth.service;

public interface LoginService {

	LoginVO memberInfoById(LoginVO loginVO) throws Exception;

	void updateLoginSuccess(LoginVO loginVO) throws Exception;

	String selectConnDate(LoginVO loginVO) throws Exception;

	void insertLoginDate(LoginVO loginVO) throws Exception;

	void updateLoginDate(LoginVO loginVO) throws Exception;

	void updateLoginFail(LoginVO loginRsltVO) throws Exception;

	void updateAccAdmFail(LoginVO loginRsltVO) throws Exception;

	void updateLogoutDate(LoginVO vo) throws Exception;

}
