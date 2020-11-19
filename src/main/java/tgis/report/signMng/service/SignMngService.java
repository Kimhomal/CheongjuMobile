package tgis.report.signMng.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface SignMngService {

    // 관리대장 > 표지관리대장 리스트
    public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    // 관리대장 > 표지관리대장 상세 조회
    public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    // 관리대장 > 표지관리대장 사진 조회
    public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    // 관리대장 > 표지관리대장 수정
    public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    // 관리대장 > 표지관리대장 삭제
    public void deleteInfo(HashMap<String, Object> params) throws Exception;

}
