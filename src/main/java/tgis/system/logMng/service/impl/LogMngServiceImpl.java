package tgis.system.logMng.service.impl;

import org.springframework.stereotype.Service;
import tgis.system.logMng.service.LogMngMapper;
import tgis.system.logMng.service.LogMngService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("logMngService")
public class LogMngServiceImpl implements LogMngService {

	@Resource(name = "logMngMapper")
	private LogMngMapper masterMapper;

	/**
	 * 관리자 > 이력관리 > 업무 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public List<?> adminUseLogList(HashMap<String, Object> params) throws Exception {
		return masterMapper.adminUseLogList(params);
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 대 메뉴 조회 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuLConnLog(HashMap<String, Object> params) throws Exception {
		return masterMapper.menuLConnLog(params);
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 대 메뉴 이용자수 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuLUseLog(HashMap<String, Object> params) throws Exception {
		return masterMapper.menuLUseLog(params);
	}


	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 조회 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuMConnLog(HashMap<String, Object> params) throws Exception {
		return masterMapper.menuMConnLog(params);
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 이용자수 이력 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuMUseLog(HashMap<String, Object> params) throws Exception {
		return masterMapper.menuMUseLog(params);
	}

	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 중 메뉴 이름
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> getMenuNmM(HashMap<String, Object> params) throws Exception {
		return masterMapper.getMenuNmM(params);
	}
	/**
	 * 관리자 > 이력관리 > 접속 이력 관리 페이지 > 사용자 접속 이력
	 *
	 * @param params
	 * @throws Exception
	 */
	public void insertUseLog(HashMap<String, Object> params) throws Exception {
		masterMapper.insertUseLog(params);
	}
}
