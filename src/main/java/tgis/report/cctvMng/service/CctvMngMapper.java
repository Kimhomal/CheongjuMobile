package tgis.report.cctvMng.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

@Mapper("cctvMngMapper")
public interface CctvMngMapper {

    List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    EgovMap selectFileInfoDetail(HashMap<String, Object> params) throws Exception;

    void insertFile(HashMap<String, Object> params) throws Exception;

    void insertFileMaster(String atchFileId) throws Exception;

    void insertFileDetail(HashMap<String, Object> params) throws Exception;

    void insertInfo(HashMap<String, Object> params) throws Exception;

    void updateInfo(HashMap<String, Object> params) throws Exception;

    void deleteFileMaster(HashMap<String, Object> params) throws Exception;

    void deleteFileDetail(HashMap<String, Object> params) throws Exception;

    void deleteFileInfo(HashMap<String, Object> params) throws Exception;

    void deleteInfo(HashMap<String, Object> params) throws Exception;

    String atchFileId() throws Exception;

    public List<?> selectExcelList(HashMap<String, Object> params) throws Exception;

}
