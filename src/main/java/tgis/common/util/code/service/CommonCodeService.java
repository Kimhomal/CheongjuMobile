package tgis.common.util.code.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommonCodeService {

	List<HashMap<String, String>> getCodeList(Map<String, Object> params) throws Exception;

}
