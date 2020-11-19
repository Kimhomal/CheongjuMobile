package tgis.system.code.service.impl;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import tgis.system.code.service.CodeMapper;
import tgis.system.code.service.CodeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 시스템관리 > 코드관리 요청을 처리하는 비즈니스(ServiceImpl) 클래스
 *
 */
@Service("codeService")
public class CodeServiceImpl implements CodeService {

	@Resource(name = "codeMapper")
	private CodeMapper masterMapper;

	@Override
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception {
		return masterMapper.selectInfoList(params);
	}

	@Override
	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception {
		return masterMapper.selectInfo(params);
	}

	@Override
	public void insertInfo(HashMap<String, Object> params) throws Exception {
		masterMapper.insertInfo(params);
	}

	@Override
	public void updateInfo(HashMap<String, Object> params) throws Exception {
		masterMapper.updateInfo(params);
	}

	@Override
	public void deleteInfo(HashMap<String, Object> params) throws Exception {
		masterMapper.deleteInfo(params);
	}

}
