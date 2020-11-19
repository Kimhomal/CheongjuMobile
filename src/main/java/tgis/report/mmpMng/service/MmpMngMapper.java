package tgis.report.mmpMng.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

@Mapper("mmpMngMapper")
public interface MmpMngMapper {

    public List<?> selectFacilCntList(HashMap<String, Object> params) throws Exception;

    EgovMap selectMonthCnt(HashMap<String, Object> params) throws Exception;

    EgovMap selectYearCnt(HashMap<String, Object> params) throws Exception;
}
