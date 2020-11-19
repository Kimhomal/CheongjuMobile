package tgis.board.notice.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Transactional
public interface NoticeService {
	
	//공지사항 리스트
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

	public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

	public int selectNoticeMaxID(HashMap<String, Object> params) throws Exception;

	public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	public void deleteInfo(HashMap<String, Object> params) throws Exception;

}
