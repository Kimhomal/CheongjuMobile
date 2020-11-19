package tgis.user.auth.service;

public interface AuthService {

	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * @return LoginVO - 사용자 ValueObject
	 */
	public LoginVO getAuthenticatedUser();

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public Boolean isAuthenticated();




}
