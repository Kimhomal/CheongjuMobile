package tgis.report.mmpMng.service.impl;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import tgis.report.mmpMng.service.MmpMngMapper;
import tgis.report.mmpMng.service.MmpMngService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service("mmpMngService")
public class MmpMngServiceImpl implements MmpMngService {
	
	@Resource(name = "mmpMngMapper")
	private MmpMngMapper mmpMngMapper;

	public List<?> selectFacilCntList(HashMap<String, Object> params) throws Exception {
		return mmpMngMapper.selectFacilCntList(params);
	}

	public EgovMap selectMonthCnt(HashMap<String, Object> params) throws Exception {
		return mmpMngMapper.selectMonthCnt(params);
	}

	public EgovMap selectYearCnt(HashMap<String, Object> params) throws Exception {
		return mmpMngMapper.selectYearCnt(params);
	}



}
