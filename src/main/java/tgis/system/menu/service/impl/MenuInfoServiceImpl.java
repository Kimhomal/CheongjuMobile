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
package tgis.system.menu.service.impl;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tgis.system.menu.AuthInfoController;
import tgis.system.menu.service.MenuInfoComboVO;
import tgis.system.menu.service.MenuInfoMapper;
import tgis.system.menu.service.MenuInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

@Service("menuInfoService")
public class MenuInfoServiceImpl implements MenuInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInfoController.class);

	@Resource(name = "menuInfoMapper")
	private MenuInfoMapper menuInfoMapper;

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public List<?> menuInfoList(HashMap<String, Object> params) throws Exception {
		return menuInfoMapper.menuInfoList(params);
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 추가
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void insertMenuInfo(HashMap<String, Object> params) throws Exception {

		String menuLevel	= params.get("menuLevel").toString();
//		String leafFlag	= params.get("leafFlag").toString();
		String parMenuLevel	= params.get("parMenuLevel").toString();

		if("0".equals(menuLevel)){	//대메뉴 등록
			menuInfoMapper.instMenu_1(params);
		}else if("1".equals(menuLevel)){	//중메뉴 등록
			int maxMenuOrderNo = menuInfoMapper.instGetMaxOrd_2(params);	//max 메뉴순서 get
			menuInfoMapper.instUdtOrd_2(maxMenuOrderNo-1);						//메뉴 순서 변경
			params.put("maxMenuOrderNo", maxMenuOrderNo);	//등록인자로 사용하기 위하여 POJO객체에 셋팅
			menuInfoMapper.instMenu_2(params);								//메뉴등록
		}else if("2".equals(menuLevel)){	//depth4중메뉴 및 소메뉴
//			if("M".equals(leafFlag)){	//중메뉴에서 중메뉴 등록할때
//				//int maxMenuOrderNo = menuInfoService.instGetMaxOrd_2(menuInfoUdtVO);	//max 메뉴순서 get
//				int maxMenuOrderNo = menuInfoMapper.instGetMaxOrd_3(params);
//				menuInfoMapper.instUdtOrd_2(maxMenuOrderNo-1);						//메뉴 순서 변경
//				params.put("maxMenuOrderNo", maxMenuOrderNo);
//				menuInfoMapper.instMenu_2_3(params);								//메뉴등록
//			}else
			if("2".equals(parMenuLevel)){	//중메뉴안에 중메뉴에서 소메뉴 등록할때
				int maxMenuOrderNo = menuInfoMapper.instGetMaxOrd2_4(params);
				menuInfoMapper.instUdtOrd_2(maxMenuOrderNo-1);
				params.put("maxMenuOrderNo", maxMenuOrderNo);
				menuInfoMapper.instMenu_2_4(params);
			}else{
				int maxMenuOrderNo = menuInfoMapper.instGetMaxOrd_3(params);
				menuInfoMapper.instUdtOrd_2(maxMenuOrderNo-1);
				params.put("maxMenuOrderNo", maxMenuOrderNo);
				menuInfoMapper.instMenu_3(params);
			}
		}
	}

	/**
	 * 관리 > 메뉴관리 > 메뉴관리 페이지 > 메뉴 추가 팝업 > 메뉴 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void updateMenuInfo(HashMap<String, Object> params) throws Exception {
			menuInfoMapper.updateMenuInfo(params);
	}

	//총갯수
	@Override
	public int menuInfoListTotCnt(HashMap<String, Object> params) throws Exception {
		return menuInfoMapper.menuInfoListTotCnt(params);
	}

	//대메뉴 콤보
	@Override
	public List<MenuInfoComboVO> menuInfoParseLvlCombo() throws Exception {
		return menuInfoMapper.menuInfoParseLvlCombo();
	}

	//중메뉴 콤보
	@Override
	public List<?> menuInfoLevel2Combo(HashMap<String, Object> params) throws Exception {
		return menuInfoMapper.menuInfoLevel2Combo(params);
	}



	//메뉴삭제
	@Override
	public void deleteMenuInfo(HashMap<String, Object> params) throws Exception {
		menuInfoMapper.deleteUdtMenu(params);
		menuInfoMapper.deleteUdtMenuOrder(params);
		menuInfoMapper.deleteMenuInfo(params);
	}

	//메뉴이동
	@Override
	public int menuMoveUdt(HashMap<String, Object> params) throws Exception {
		Vector<String> vctUpdateMenuId 	= new Vector<String>();		//피 이동 메뉴id 그룹 담을 변수
		Vector<String> vctCurrentMenuId = new Vector<String>();		//메뉴이동으로 선택된 id 그룹을 담을 변수

		int rtnValue = 0;		//return값 초기화
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("success",  true);

		String upDownGb = (String)params.get("upDownGb");			//메뉴이동 (위,아래)
		String parMenuId = (String)params.get("parMenuId");			//선택된 메뉴의 부모아이디
		String menuLevel = (String)params.get("menuLevel");			//메뉴레벨:대[1],중[2],소[3]
		String menuId = (String)params.get("menuId");						//선택된 메뉴아이디
		String orderNo = (String)params.get("orderNo");					//선택된 메뉴 순서
		String parMenuLevel = (String)params.get("parMenuLevel");	//선택된 메뉴 부모 레벨

		/*수정데이터 기준값 조회*/
		int up_order_no 		= 0;
		String update_order_no 	= "";
		String update_menu_id 	= "";
		String update_menu_lvl 		= "";
		String update_par_menu_lvl 	= "";

		EgovMap getBaseVo = menuInfoMapper.moveMenuGetParMenuId(params);

		LOGGER.debug("getBaseVo....................{}",getBaseVo);

		/*결과값 setting*/
		if(getBaseVo == null){
			rtnValue = 99;			//메뉴이동 불가
			return rtnValue;		//프로세스 종료
		}else{
			if(!"".equals(String.valueOf(getBaseVo.get("menuOrderNo"))) || String.valueOf(getBaseVo.get("menuOrderNo")) !=null){
				up_order_no = Integer.parseInt(String.valueOf(getBaseVo.get("menuOrderNo")));
			}
			update_order_no 	= String.valueOf(getBaseVo.get("orderNo"));
			update_menu_id 		= (String)getBaseVo.get("menuId");
			update_menu_lvl		= (String)getBaseVo.get("menuLevel");
			update_par_menu_lvl	= (String)getBaseVo.get("parMenuLevel");
		}

		//업데이트 시킬 데이터 조회
		List<String> udtMenuIdList = null;
		List<String> curMenuIdList = null;
		if("1".equals(menuLevel)){
			udtMenuIdList = menuInfoMapper.updateMenuIdList(update_menu_id);
			curMenuIdList = menuInfoMapper.updateMenuIdList(menuId);
		}else if("2".equals(menuLevel) && "1".equals(parMenuLevel)){
			udtMenuIdList = menuInfoMapper.updateMenuIdList(update_menu_id);
			curMenuIdList = menuInfoMapper.updateMenuIdList(menuId);
		}
		else if("2".equals(menuLevel) && "2".equals(parMenuLevel)){
			if("2".equals(update_menu_lvl) && "2".equals(update_par_menu_lvl)){
				udtMenuIdList = menuInfoMapper.updateMenuIdList(update_menu_id);
				curMenuIdList = menuInfoMapper.updateMenuIdList(menuId);
			}else{
				curMenuIdList = menuInfoMapper.updateMenuIdList(menuId);
			}
		}else{
			if("2".equals(update_menu_lvl) && "2".equals(update_par_menu_lvl)){
				udtMenuIdList = menuInfoMapper.updateMenuIdList(update_menu_id);
			}
		}

		//조회된 데이터 vector에 추가
		vctUpdateMenuId.add(update_menu_id);
		vctCurrentMenuId.add(menuId);
		if("1".equals(menuLevel)){
			for(int ix = 0; ix < udtMenuIdList.size(); ix++ ){
				vctUpdateMenuId.add(udtMenuIdList.get(ix));
			}
			for(int ix = 0; ix < curMenuIdList.size(); ix++ ){
				vctCurrentMenuId.add(curMenuIdList.get(ix));
			}
		}else if("2".equals(menuLevel) && "1".equals(parMenuLevel)){
			for(int ix = 0; ix < udtMenuIdList.size(); ix++ ){
				vctUpdateMenuId.add(udtMenuIdList.get(ix));
			}
			for(int ix = 0; ix < curMenuIdList.size(); ix++ ){
				vctCurrentMenuId.add(curMenuIdList.get(ix));
			}
		}
		else if("2".equals(menuLevel) && "2".equals(parMenuLevel)){
			if("2".equals(update_menu_lvl) && "2".equals(update_par_menu_lvl)){
				for(int ix = 0; ix < udtMenuIdList.size(); ix++ ){
					vctUpdateMenuId.add(udtMenuIdList.get(ix));
				}
				for(int ix = 0; ix < curMenuIdList.size(); ix++ ){
					vctCurrentMenuId.add(curMenuIdList.get(ix));
				}
			}else{
				for(int ix = 0; ix < curMenuIdList.size(); ix++ ){
					vctCurrentMenuId.add(curMenuIdList.get(ix));
				}
			}
		}else{
			if("2".equals(update_menu_lvl) && "2".equals(update_par_menu_lvl)){
				for(int ix = 0; ix < udtMenuIdList.size(); ix++ ){
					vctUpdateMenuId.add(udtMenuIdList.get(ix));
				}
			}
		}

		//ORDER_NO 변경 [같은 레벨의 순서를 변경]
		menuInfoMapper.menuMoveOrderUdt(update_order_no,menuId);
		menuInfoMapper.menuMoveOrderUdt(orderNo,update_menu_id);

		//MENU_ORDER_NO 변경 [전체 순서 변경]
		if("up".equals(upDownGb)){
			for(int i =0; i < vctCurrentMenuId.size(); i++){
				String curMenuId = vctCurrentMenuId.get(i).toString();
				menuInfoMapper.menuMoveMenuOrderUdt(up_order_no,curMenuId);
				up_order_no++;
				rtnValue++;
			}

			for(int i =0; i < vctUpdateMenuId.size(); i++){
				String udtMenuId = vctUpdateMenuId.get(i).toString();
				menuInfoMapper.menuMoveMenuOrderUdt(up_order_no,udtMenuId);
				up_order_no++;
				rtnValue++;
			}
		}else if("down".equals(upDownGb)){
			up_order_no -= vctCurrentMenuId.size();	//변경이 이루어지는 피선택자,변경선택한 데이터 순서로 치환해줘야 하기때문
			for(int i =0; i < vctUpdateMenuId.size(); i++){
				String udtMenuId = vctUpdateMenuId.get(i).toString();
				menuInfoMapper.menuMoveMenuOrderUdt(up_order_no,udtMenuId);
				up_order_no++;
				rtnValue++;
			}

			for(int i =0; i < vctCurrentMenuId.size(); i++){
				String curMenuId = vctCurrentMenuId.get(i).toString();
				menuInfoMapper.menuMoveMenuOrderUdt(up_order_no,curMenuId);
				up_order_no++;
				rtnValue++;
			}
		}

		return rtnValue;
	}


	@Override
	public List<?> getUserMenuList(HashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return menuInfoMapper.getUserMenuList(params);
	}
}
