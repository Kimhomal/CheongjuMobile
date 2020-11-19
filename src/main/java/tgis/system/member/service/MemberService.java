package tgis.system.member.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface MemberService {

	/**
	 * 메인 > 회원가입신청 팝업 > 회원가입신청
	 *
	 * @param params
	 * @throws Exception
	 */
	public void joinMemberInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;
	
	/**
	 * 관리자 > 사용자 관리 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 등록 > 아이디 중복 체크
	 *
	 * @param params
	 * @throws Exception
	 */
	public int userIdCheck(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 등록
	 *
	 * @param params
	 * @throws Exception
	 */
	public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보
	 *
	 * @param params
	 * @throws Exception
	 */
	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 > 랜덤 비밀번호 생성
	 *
	 * @throws Exception
	 */
	public String generatePasswd() throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	public void updateInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 차단
	 *
	 * @param params
	 * @throws Exception
	 */
	public void blockInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 비밀번호 초기화
	 *
	 * @param params
	 * @throws Exception
	 */
	public void updatePwInit(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 사용자 정보 수정 > 사용자 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	public void deleteInfo(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 > 유저별 메뉴 권한 리스트 조회
	 *
	 * @param params
	 * @throws Exception
	 */
	public List<?> usrMenuAuthList(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 리스트 > 유저별 메뉴 권한 수정
	 *
	 * @param params
	 * @throws Exception
	 */
	public void usrMenuAuthEdit(HashMap<String, Object> params) throws Exception;

	/**
	 * 관리자 > 사용자 관리 리스트 페이지 > 유저별 메뉴 권한 리스트 > 유저별 메뉴 권한 삭제
	 *
	 * @param params
	 * @throws Exception
	 */
	public void usrMenuAuthDel(HashMap<String, Object> params) throws Exception;
}
