package tgis.map.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface MapActionService {

	String getCenterAddr(HashMap<String, Object> params) throws Exception;

	EgovMap roadPop(HashMap<String, Object> params)throws Exception;

	List<?> selectGu(HashMap<String, Object> params) throws Exception;

}
