package tgis.system.member.service.impl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.FileUtil;
import tgis.system.member.service.MemberMapper;
import tgis.system.member.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Resource(name = "memberMapper")
	private MemberMapper memberMapper;

	/**
	 * 메인 > 회원가입신청 팝업 > 회원가입신청
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void joinMemberInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		memberMapper.joinMemberInfo(params);
	}
	
	/**
	 * 관리자 > 사용자 관리 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception {
		return memberMapper.selectInfoList(params);
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 등록 > 아이디 중복 체크
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public int userIdCheck(HashMap<String, Object> params) throws Exception {
		return memberMapper.userIdCheck(params);
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 등록
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		memberMapper.insertInfo(params);
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception {
		return memberMapper.selectInfo(params);
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 > 랜덤 비밀번호 생성
	 *
	 * @throws Exception
	 */
	@Override
	public String generatePasswd() throws Exception {
		String initPass = "";

		Random rd = new Random();
		String[] array1 = {"a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z"};
		String[] array2 = {"2","3","4","5","6","7","8","9"};
		String[] array3 = {"!","@","#","$","%","&","*"};
		for(int i=0; i<6; i++){ // 1~5번째 자리까지는 소문자
			initPass += array1[rd.nextInt(array1.length)];
		}
		initPass += array2[rd.nextInt(array2.length)];
		initPass += array2[rd.nextInt(array2.length)];
		// 6,7번째 자리는 숫자
		initPass += array3[rd.nextInt(array3.length)]; // 8번째는 특수문자

		return initPass;
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void updateInfo(HashMap<String, Object> params) throws Exception {
		memberMapper.updateInfo(params);
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 차단
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void blockInfo(HashMap<String, Object> params) throws Exception {
		memberMapper.blockInfo(params);
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 비밀번호 초기화
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void updatePwInit(HashMap<String, Object> params) throws Exception {
		memberMapper.updatePwInit(params);
	}

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void deleteInfo(HashMap<String, Object> params) throws Exception {
		memberMapper.deleteInfo(params);
	}

    /**
     * 관리자 > 사용자 관리 리스트 > 유저별 메뉴 권한 리스트 조회
     *
     * @param params
     * @throws Exception
     */
    @Override
    public List<?> usrMenuAuthList(HashMap<String, Object> params) throws Exception {
        return memberMapper.usrMenuAuthList(params);
    }

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 리스트 > 유저별 메뉴 권한 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void usrMenuAuthEdit(HashMap<String, Object> params) throws Exception {

		//기존 매핑목록 제거
		memberMapper.usrMenuAuthDel(params);
		setUsrMenuAuthth("R",(String)params.get("rCheckList"),(String)params.get("roleId"),(String)params.get("usrId"));
		setUsrMenuAuthth("CU",(String)params.get("cuCheckList"),(String)params.get("roleId"),(String)params.get("usrId"));
//		setUsrMenuAuthth("U",(String)params.get("uCheckList"),(String)params.get("roleId"),(String)params.get("usrId"));
		setUsrMenuAuthth("D",(String)params.get("dCheckList"),(String)params.get("roleId"),(String)params.get("usrId"));
	}

	public void setUsrMenuAuthth(String roleKnd,String mappingIdVal, String roleId,String usrId) throws Exception {

		if(!"".equals(mappingIdVal) &&  mappingIdVal != null){
			List<String> goneMenuId = new ArrayList<String>();
			List<String> newMenuId = new ArrayList<String>();

			//화면에서 전달받은 인자
			StringTokenizer st = new StringTokenizer(mappingIdVal, ",");
			String[] spVal = mappingIdVal.split(",");
			int spValLength = 0;
			if(spVal != null) {
				spValLength = spVal.length;
			}

			//권한별 메뉴권한 목록 가져오기
			List<?> getAuthList = memberMapper.menuMappingList(roleId,roleKnd);

			int listSize = 0;
			if(getAuthList != null) {
				listSize = getAuthList.size();
			}

			//기존에 있었지만 없어진 메뉴 (USE_YN = 'N')
			for(int i=0; i<listSize; i++) {
				EgovMap getMenuId = (EgovMap)getAuthList.get(i);

				//루트 메뉴 PASS
				if("M000000000".equals(getMenuId.get("menuId"))) {
					continue;
				}

				boolean stateGubun = true;
				for(int j=0; j<spValLength; j++) {
					if(getMenuId.get("menuId").equals(spVal[j])) {
						stateGubun = false;
						break;
					}
				}

				if(stateGubun) {
					goneMenuId.add((String)getMenuId.get("menuId"));
				}
			}

			//기존엔 없었지만 추가된 메뉴 (USE_YN = 'Y')
			while(st.hasMoreTokens()) {
				String stMenuId = st.nextToken();
				boolean stateGubun2 = true;
				for(int i=0; i < listSize; i++){
					EgovMap getMenuId2 = (EgovMap)getAuthList.get(i);
					if(stMenuId.equals(getMenuId2.get("menuId"))){
						stateGubun2 =false;
						break;
					}
				}

				if(stateGubun2){
					newMenuId.add(stMenuId);
				}
			}

			int goneMenuIdSize = 0;
			int newMenuIdSize = 0;

			if(goneMenuId != null){
				goneMenuIdSize = goneMenuId.size();
			}

			if(newMenuId != null){
				newMenuIdSize = newMenuId.size();
			}

			for(int ix=0; ix<goneMenuIdSize;ix++){
				memberMapper.usrMenuAuthInsert(roleKnd,usrId, goneMenuId.get(ix),"N");
			}
			for(int ik=0; ik<newMenuIdSize;ik++){
				memberMapper.usrMenuAuthInsert(roleKnd,usrId, newMenuId.get(ik),"Y");
			}
		}
	}

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 리스트 > 유저별 메뉴 권한 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void usrMenuAuthDel(HashMap<String, Object> params) throws Exception {
		memberMapper.usrMenuAuthDel(params);
	}
}
