package tgis.board.reference.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface ReferenceService {

    // 교통안전시설법령/지침정보 리스트
    public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    // 교통안전시설법령/지침정보 상세조회
    public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    // 교통안전시설법령/지침정보 파일조회
    public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    public int selectReferenceMaxID(HashMap<String, Object> params) throws Exception;

    public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void deleteInfo(HashMap<String, Object> params) throws Exception;

}
