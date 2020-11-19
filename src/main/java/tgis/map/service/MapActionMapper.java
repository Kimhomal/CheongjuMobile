package tgis.map.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("mapActionMapper")
public interface MapActionMapper {

	String getCenterAddr(HashMap<String, Object> params) throws Exception;

	EgovMap roadPop(HashMap<String, Object> params) throws Exception;

	List<?> selectGu(HashMap<String, Object> params) throws Exception;

}
