package tgis.map.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.map.service.MapActionMapper;
import tgis.map.service.MapActionService;

@Service("mapActionService")
public class MapActionServiceImpl implements MapActionService {

	@Resource(name = "mapActionMapper")
	private MapActionMapper mapActionMapper;

	public String getCenterAddr(HashMap<String, Object> params) throws Exception {
		return mapActionMapper.getCenterAddr(params);
	}

	public EgovMap roadPop(HashMap<String, Object> params) throws Exception {
		return mapActionMapper.roadPop(params);
	}

	public List<?> selectGu(HashMap<String, Object> params) throws Exception {
		return mapActionMapper.selectGu(params);
	}

}
