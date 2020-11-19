package tgis.system.menu.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("authInfoMapper")
public interface AuthInfoMapper {

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 리스트
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 > 상세조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 > 등록
	 *
	 * @param params
	 * @throws Exception
	 */
	public void insertInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 > 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	public void updateInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 시스템관리 > 메뉴관리 > 권한관리 > 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	public void deleteInfo(HashMap<String, Object> params) throws Exception;
}
