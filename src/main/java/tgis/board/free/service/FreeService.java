package tgis.board.free.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface FreeService {

    // 자유게시판 리스트
    public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    // 자유게시판 상세조회
    public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    // 자유게시판 파일조회
    public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    public int selectFreeMaxID(HashMap<String, Object> params) throws Exception;

    public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void deleteInfo(HashMap<String, Object> params) throws Exception;

}
