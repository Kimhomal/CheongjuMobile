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

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface MenuInfoService {

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

	//데이터 총 카운트
	public int menuInfoListTotCnt(HashMap<String, Object> params) throws Exception;

	//대메뉴콤보
	public List<?> menuInfoParseLvlCombo() throws Exception;

	//중메뉴콤보
	public List<?> menuInfoLevel2Combo(HashMap<String, Object> params) throws Exception;



	//메뉴 이동
	public int menuMoveUdt(HashMap<String, Object> params) throws Exception;

	public List<?> getUserMenuList(HashMap<String, Object> params) throws Exception;
}
