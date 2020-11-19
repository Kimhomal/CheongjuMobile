package tgis.system.menu.service.impl;

import org.springframework.stereotype.Service;
import tgis.system.menu.service.MenuAuthMapper;
import tgis.system.menu.service.MenuAuthService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

@Service("menuAuthService")
public class MenuAuthServiceImpl implements MenuAuthService {

	@Resource(name = "menuAuthMapper")
	private MenuAuthMapper masterMapper;

	@Override
	public List<?> menuAuthList(HashMap<String, Object> params) throws Exception {
		return masterMapper.menuAuthList(params);
	}

	@Override
	public void menuAuthEdit(HashMap<String, Object> params) throws Exception {

		String authVal = params.get("authVal").toString();
		String rCheckList = params.get("rCheckList").toString();
		String cuCheckList = params.get("cuCheckList").toString();
		String dCheckList = params.get("dCheckList").toString();

		masterMapper.roleMenuAuthDelete(authVal); // 조회 권한 삭제

		if(!"".equals(rCheckList) &&  rCheckList != null){
			StringTokenizer st = new StringTokenizer(rCheckList, ",");
			while (st.hasMoreTokens()) {
				masterMapper.roleMenuAuthInsert(st.nextToken(),authVal,"R");
			}
		}

		if(!"".equals(cuCheckList) &&  cuCheckList != null){
			StringTokenizer st = new StringTokenizer(cuCheckList, ",");
			while (st.hasMoreTokens()) {
				masterMapper.roleMenuAuthInsert(st.nextToken(),authVal,"CU");
			}
		}

		if(!"".equals(dCheckList) &&  dCheckList != null){
			StringTokenizer st = new StringTokenizer(dCheckList, ",");
			while (st.hasMoreTokens()) {
				masterMapper.roleMenuAuthInsert(st.nextToken(),authVal,"D");
			}
		}
	}

}
