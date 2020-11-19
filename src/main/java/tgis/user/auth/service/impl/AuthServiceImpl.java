package tgis.user.auth.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import tgis.user.auth.service.AuthService;
import tgis.user.auth.service.LoginVO;

@Service("authService")
public class AuthServiceImpl extends EgovAbstractServiceImpl implements AuthService {

	@Override
	public LoginVO getAuthenticatedUser() {
		return (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
	}

	@Override
	public Boolean isAuthenticated() {
		return (this.getAuthenticatedUser() != null) ? true : false;
	}
}
