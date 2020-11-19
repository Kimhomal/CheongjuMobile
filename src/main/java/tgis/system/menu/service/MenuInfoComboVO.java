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

/**
 * 메뉴추가 화면 콤보 박스 조회를 위한 vo
 * 
 */
public class MenuInfoComboVO {	
	private String menuId = "";
	private String menuNm = "";
	private String rootlevel = "";
	

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getRootlevel() {
		return rootlevel;
	}
	public void setRootlevel(String rootlevel) {
		this.rootlevel = rootlevel;
	}
}
