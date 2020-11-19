package tgis.report.mmpMng.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface MmpMngService {
    public List<?> selectFacilCntList(HashMap<String, Object> params) throws Exception;

    public EgovMap selectMonthCnt(HashMap<String, Object> params) throws Exception;

    public EgovMap selectYearCnt(HashMap<String, Object> params) throws Exception;
}
