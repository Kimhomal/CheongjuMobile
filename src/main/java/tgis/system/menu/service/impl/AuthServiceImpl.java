package tgis.system.menu.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.system.menu.service.AuthInfoMapper;
import tgis.system.menu.service.AuthInfoService;

@Service("authInfoService")
public class AuthServiceImpl implements AuthInfoService {

	@Resource(name = "authInfoMapper")
	private AuthInfoMapper authInfoMapper;

	@Override
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception {
		return authInfoMapper.selectInfoList(params);
	}

	@Override
	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception {
		return authInfoMapper.selectInfo(params);
	}

	@Override
	public void insertInfo(HashMap<String, Object> params) throws Exception {
		authInfoMapper.insertInfo(params);
	}

	@Override
	public void updateInfo(HashMap<String, Object> params) throws Exception {
		authInfoMapper.updateInfo(params);
	}

	@Override
	public void deleteInfo(HashMap<String, Object> params) throws Exception {
		authInfoMapper.deleteInfo(params);
	}

}
