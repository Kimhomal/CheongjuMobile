package tgis.board.qna.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface QnaService {

    // Q&A 리스트
    public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    // Q&A 상세조회
    public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    // Q&A 파일조회
    public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    public int selectQnaMaxID(HashMap<String, Object> params) throws Exception;

    public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void updateSeq(HashMap<String, Object> params) throws Exception;

    public void answerInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void deleteInfo(HashMap<String, Object> params) throws Exception;

}
