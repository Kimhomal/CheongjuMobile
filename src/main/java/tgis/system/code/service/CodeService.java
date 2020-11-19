package tgis.system.code.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

/**
 * 메뉴관리 > 코드관리를 처리하는 Service 인터페이스
 *
 */
public interface CodeService {

	/**
	 * 메뉴관리 > 코드관리 리스트
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

	/**
	 * 메뉴관리 > 코드관리 > 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 메뉴관리 > 코드관리 > 등록
	 *
	 * @param params
	 * @throws Exception
	 */
	public void insertInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 메뉴관리 > 코드관리 > 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	public void updateInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 메뉴관리 > 코드관리 > 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	public void deleteInfo(HashMap<String, Object> params) throws Exception;
}
