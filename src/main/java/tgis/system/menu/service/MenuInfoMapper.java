/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tgis.system.menu.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper("menuInfoMapper")
public interface MenuInfoMapper {

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> menuInfoList(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 추가
	 *
	 * @param params
	 * @throws Exception
	 */
	public void insertMenuInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	public void updateMenuInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	public void deleteMenuInfo(HashMap<String, Object> params) throws Exception;

	//총 갯수
	public int menuInfoListTotCnt(HashMap<String, Object> params) throws Exception;

	//대메뉴콤보
	public List<MenuInfoComboVO> menuInfoParseLvlCombo() throws Exception;

	//중메뉴콤보
	public List<MenuInfoComboVO> menuInfoLevel2Combo(HashMap<String, Object> params) throws Exception;

	//대메뉴등록
	public void instMenu_1(HashMap<String, Object> params) throws Exception;

	//중메뉴등록
	public int instGetMaxOrd_2(HashMap<String, Object> params) throws Exception;
	public void instUdtOrd_2(@Param("maxMenuOrderNo") int maxMenuOrderNo) throws Exception;
	public void instMenu_2(HashMap<String, Object> params) throws Exception;

	//소메뉴등록
	public int instGetMaxOrd_3(HashMap<String, Object> params) throws Exception;
	public int instGetMaxOrd2_4(HashMap<String, Object> params) throws Exception;
	public void instMenu_3(HashMap<String, Object> params) throws Exception;
	public void instMenu_2_3(HashMap<String, Object> params) throws Exception;
	public void instMenu_2_4(HashMap<String, Object> params) throws Exception;

	//메뉴삭제
	public void deleteUdtMenu(HashMap<String, Object> params) throws Exception;
	public void deleteUdtMenuOrder(HashMap<String, Object> params) throws Exception;

	//메뉴이동
	public EgovMap moveMenuGetParMenuId(HashMap<String, Object> params) throws Exception;
	public List<String> updateMenuIdList(String comMenuId) throws Exception;
	public void menuMoveOrderUdt(@Param("orderNo") String orderNo, @Param("menuId") String menuId) throws Exception;
	public void menuMoveMenuOrderUdt(@Param("orderNo") int orderNo, @Param("menuId") String menuId) throws Exception;


	public List<?> getUserMenuList(HashMap<String, Object> params) throws Exception;
}
