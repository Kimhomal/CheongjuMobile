package tgis.board.traffic.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface TrafficService {

    // 연도별 교통량 조회 리스트
    public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    // 연도별 교통량 조회 상세조회
    public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    // 연도별 교통량 조회 파일조회
    public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    public int selectTrafficMaxID(HashMap<String, Object> params) throws Exception;

    public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    public void deleteInfo(HashMap<String, Object> params) throws Exception;

}
