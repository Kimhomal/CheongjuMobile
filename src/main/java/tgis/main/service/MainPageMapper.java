package tgis.main.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("mainPageMapper")
public interface MainPageMapper {

	List<?> mainNoticeList(HashMap<String, Object> params) throws Exception;



}
