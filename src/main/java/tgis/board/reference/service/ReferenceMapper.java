package tgis.board.reference.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

@Mapper("referenceMapper")
public interface ReferenceMapper {

    List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    Object selectMaxVisited(HashMap<String, Object> params) throws Exception;

    void updateVisited(HashMap<String, Object> params) throws Exception;

    EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    int selectReferenceMaxID(HashMap<String, Object> params) throws Exception;

    void insertFile(HashMap<String, Object> params) throws Exception;

    void insertInfo(HashMap<String, Object> params) throws Exception;

    void updateInfo(HashMap<String, Object> params) throws Exception;

    void deleteFileInfo(HashMap<String, Object> params) throws Exception;

    void deleteInfo(HashMap<String, Object> params) throws Exception;

}
