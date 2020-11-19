package tgis.report.signalMng.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

@Mapper("signalMngMapper")
public interface SignalMngMapper {

    List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    EgovMap selectFileInfoDetail(HashMap<String, Object> params) throws Exception;

    void insertFile(HashMap<String, Object> params) throws Exception;

    void insertFileMaster(String atchFileId) throws Exception;

    void insertFileDetail(HashMap<String, Object> params) throws Exception;

    void updateInfo(HashMap<String, Object> params) throws Exception;

    void deleteFileMaster(HashMap<String, Object> params) throws Exception;

    void deleteFileDetail(HashMap<String, Object> params) throws Exception;

    void deleteFileInfo(HashMap<String, Object> params) throws Exception;

    void deleteInfo(HashMap<String, Object> params) throws Exception;

    String atchFileId() throws Exception;

    EgovMap selectInfoPop(HashMap<String, Object> params) throws Exception;

    List<?> selectInfoSinho(HashMap<String, Object> params) throws Exception;

    List<?> selectInfoJiju(HashMap<String, Object> params) throws Exception;

    List<?> selectInfoBu(HashMap<String, Object> params) throws Exception;

}
