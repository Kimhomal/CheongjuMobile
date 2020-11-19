package tgis.common.util.code.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("commonCodeMapper")
public interface CommonCodeMapper {

	/**
	 * 공통 코드
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getCommonCode(Map<String, Object> param) throws Exception;

	/**
	 * 권한 코드
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getRoleCode(Map<String, Object> param) throws Exception;

	/**
	 * 경찰서 코드
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getPeCde(Map<String, Object> param) throws Exception;

	/**
	 * 구 코드
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getGuCde(Map<String, Object> param) throws Exception;

	/**
	 * 동 코드
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getEmdCde(Map<String, Object> param) throws Exception;

	/**
	 * 동 코드(선택 시군구 동)
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getEmdCdeByGu(Map<String, Object> param) throws Exception;
	
	/**
	 * 안전표지
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getMrkKnd(Map<String, Object> param) throws Exception;
	

}

