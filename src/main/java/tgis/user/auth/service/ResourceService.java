package tgis.user.auth.service;

import java.util.List;


public interface ResourceService {

	List<ResourceVO> getAuthResource(LoginVO loginvo) throws Exception;

	void setSessionAuthResource(LoginVO loginvo) throws Exception;



	List<ResourceVO> getNotAuthResource() throws Exception;

	void setSessionNotAuthResource() throws Exception;

}
