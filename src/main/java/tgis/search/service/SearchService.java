package tgis.search.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SearchService {

	List<?> searchCross(HashMap<String, Object> params) throws Exception;

	Map<String, Object> getDaumAddr(HashMap<String, Object> params) throws Exception;

	Map<String, Object> getKeyword(HashMap<String, Object> params) throws Exception;

	List<?> selectFacil(HashMap<String, Object> params) throws Exception;


}
