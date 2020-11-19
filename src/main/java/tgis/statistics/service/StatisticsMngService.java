package tgis.statistics.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

public interface StatisticsMngService {

    public EgovMap trafficCnt() throws Exception;

    public EgovMap rodCnt() throws Exception;

    public EgovMap specialCnt() throws Exception;

    public EgovMap baseCnt() throws Exception;

    public EgovMap etcCnt() throws Exception;

    public List<?>selectGuStatistics(HashMap<String, Object> params) throws Exception;

    public List<?> selectPoliceStatistics(HashMap<String, Object> params) throws Exception;

    public List<?> selectDongStatistics(HashMap<String, Object> params) throws Exception;

    public List<?> selectCrossStatistics(HashMap<String, Object> params) throws Exception;

    public List<?> selectDetailFcltsList(HashMap<String, Object> params) throws Exception;

    public List<?> selectDetailFcltsExcelDown(HashMap<String, Object> params) throws Exception;

}
