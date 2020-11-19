package tgis.report.cpzMng.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface CpzMngService {

    // 관리대장 > 어린이보호구역 관리카드 리스트
    public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

    // 관리대장 > 어린이보호구역 관리카드 상세 조회
    public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

    // 관리대장 > 어린이보호구역 관리카드 사진 조회
    public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

    // 관리대장 > 어린이보호구역 관리카드 수정
    public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

    // 관리대장 > 어린이보호구역 관리카드 삭제
    public void deleteInfo(HashMap<String, Object> params) throws Exception;

    // 관리대장 > 어린이보호구역 관리카드 > 보호구역 내 시설물 설치 내용 조회
    public EgovMap selectFacilInfo(HashMap<String, Object> params) throws Exception;

}
