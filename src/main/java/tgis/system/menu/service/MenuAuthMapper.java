package tgis.system.menu.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper("menuAuthMapper")
public interface MenuAuthMapper {

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지 > 메뉴권한리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuAuthList(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지 > 조회 권한 삭제
	 *
	 * @param authVal
	 * @throws Exception
	 */
	public void roleMenuAuthDelete(String authVal) throws Exception;

	/**
	 * 관리 > 메뉴관리 > 메뉴권한관리 페이지 > 조회 권한 등록
	 *
	 * @param params
	 * @throws Exception
	 */
	public void roleMenuAuthInsert(@Param("menuId") String menuId, @Param("roleId") String roleId, @Param("roleKnd") String roleKnd) throws Exception;

}
