package tgis.system.code.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

/**
 * 메뉴관리 > 코드관리 요청을 처리하는 Mapper 인터페이스
 *
 */
@Mapper("codeMapper")
public interface CodeMapper {

	/**
	 * 메뉴관리 > 코드관리 리스트
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

	/**
	 * 메뉴관리 > 코드관리 > 상세조회
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
