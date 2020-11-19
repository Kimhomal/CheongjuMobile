package tgis.system.menu.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface MenuAuthService {

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지 > 메뉴권한리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuAuthList(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지 > 메뉴권한리스트 > 메뉴권한수정
	 *
	 * @param params
	 * @throws Exception
	 */
	public void menuAuthEdit(HashMap<String, Object> params) throws Exception;

}
