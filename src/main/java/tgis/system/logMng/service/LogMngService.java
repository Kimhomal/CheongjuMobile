package tgis.system.logMng.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface LogMngService {

	/**
	 * 관리자 > 이력관리 > 업무 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> adminUseLogList(HashMap<String, Object> params) throws Exception;


	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 대 메뉴 조회 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuLConnLog(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 대 메뉴 이용자수 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuLUseLog(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 조회 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuMConnLog(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 이용자수 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuMUseLog(HashMap<String, Object> params) throws Exception;



	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 이름
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> getMenuNmM(HashMap<String, Object> params) throws Exception;



	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 사용자 접속 이력
	 *
	 * @param params
	 * @throws Exception
	 */
	public void insertUseLog(HashMap<String, Object> params) throws Exception;
}
