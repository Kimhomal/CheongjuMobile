package tgis.user.auth.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tgis.common.util.com.ComObjectUtils;
import tgis.user.auth.service.LoginVO;
import tgis.user.auth.service.ResourceMapper;
import tgis.user.auth.service.ResourceService;
import tgis.user.auth.service.ResourceVO;


@Service("authResourceService")
public class ResourceServiceImpl implements ResourceService {

	@Resource(name="authResourceMapper")
	private ResourceMapper authResourceMapper;


	@Override
	public List<ResourceVO> getAuthResource(LoginVO loginvo) throws Exception {

		List<ResourceVO> resources = authResourceMapper.getAuthResourceList(loginvo);

		return resources;
	}

	@Override
	public List<ResourceVO> getNotAuthResource() throws Exception {

		List<ResourceVO> resources = authResourceMapper.getNotAuthResourceList();

		return resources;
	}

	@Override
	public void setSessionAuthResource(LoginVO loginvo) throws Exception {
		List<ResourceVO> resources = this.getAuthResource(loginvo);

		HashMap<String,Object> params =  new HashMap<String,Object>();

		for(int i=0;i<resources.size();i++){
			params.put(resources.get(i).getMenuId(), resources.get(i));
		}


		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = servletRequestAttribute.getRequest().getSession(true);
		session.setAttribute("authResourceVO", params);
	}

	@Override
	public void setSessionNotAuthResource() throws Exception {
		List<ResourceVO> resources = this.getNotAuthResource();

		HashMap<String,Object> params =  new HashMap<String,Object>();

		for(int i=0;i<resources.size();i++){
			params.put(resources.get(i).getMenuId(), resources.get(i));
		}

		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = servletRequestAttribute.getRequest().getSession(true);
		session.setAttribute("authResourceVO", params);

	}
}
