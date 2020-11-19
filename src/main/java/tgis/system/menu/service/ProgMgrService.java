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

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface ProgMgrService {

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> selectProgList(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 상세 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public EgovMap selectProgInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 추가
	 *
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	public void insertProgInfo(HashMap<String, Object> params) throws Exception;


	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	public void updateProgInfo(HashMap<String, Object> params) throws Exception;


//	@Transactional
//	public void updateProgMenu(HashMap<String, Object> params) throws Exception;


	/**
	 * 관리자 > 프로그램 리스트 > 프로그램 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	public void deleteProgInfo(HashMap<String, Object> params) throws Exception;

	public List<?> progSearchList(HashMap<String, Object> params) throws Exception;


}
