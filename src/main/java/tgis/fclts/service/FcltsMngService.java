package tgis.fclts.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FcltsMngService {

	int insertFacility(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	EgovMap addr(HashMap<String, Object> params) throws Exception;

	EgovMap css(HashMap<String, Object> params) throws Exception;

	EgovMap sch(HashMap<String, Object> params) throws Exception;

	EgovMap police(HashMap<String, Object> params) throws Exception;

	EgovMap road(HashMap<String, Object> params) throws Exception;

	public List<?> getIgtSignList(HashMap<String, Object> params) throws Exception;

	EgovMap selectFcltsInfo(HashMap<String, Object> params)  throws Exception;

	int deleteFacility(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	int updateFacility(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	EgovMap selectReportFileInf(HashMap<String, Object> params) throws Exception;

	EgovMap selectFacFileInf(HashMap<String, Object> params) throws Exception;

	public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception;

	List<?> selectWorkList(HashMap<String, Object> params) throws Exception;

	int insertWorkList(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

	int updateWorkList(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	int deleteWorkList(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

}
