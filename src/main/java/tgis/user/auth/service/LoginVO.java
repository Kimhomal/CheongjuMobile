package tgis.user.auth.service;

import java.io.Serializable;

/**
 * @Class Name : LoginVO.java
 * @Description : Login VO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.03.03    박지욱          최초 생성
 *
 *  @author 공통서비스 개발팀 박지욱
 *  @since 2009.03.03
 *  @version 1.0
 *  @see
 *
 */
public class LoginVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8274004534207618049L;

	/** 아이디 */
	private String usrId;
	private String pw;
	private String usrNam;
	private String usrPsn;
	private String rmk;
	private String usrTel;
	private String buseNam;
	private String email;
	private String roleCde;
	private String startYmd;
	private String endYmd;
	private String regYmd;
	private String yno;
	private int bssNum;
	private String bssNam;
	private String siMuCde;
	private String siMuNam;
	private String usrPstnNam;
	private String useyno;
	private String buseCde;
	private String ctkVcnCde;
	private int cnt;
	private String latestLoginDate;
	private String latestFailDate;
	private int loginFailCnt;
	private String menuCde;
	private String grntRole;
	private String useYn;
	private String extentYn;

	private String usrmgeid;

	private String selectIdPw;
	/** 공지사항관리번호 */
//	private String menucde;

	/** 접속IP **/
	private String connIp;

	/** 비밀번호 변경 주기 **/
	private String pwChgYmd;
	private String pwExtYmd;

	public String getUsrmgeid() {
		return usrmgeid;
	}
	public void setUsrmgeid(String usrmgeid) {
		this.usrmgeid = usrmgeid;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getUsrNam() {
		return usrNam;
	}
	public void setUsrNam(String usrNam) {
		this.usrNam = usrNam;
	}
	public String getUsrPsn() {
		return usrPsn;
	}
	public void setUsrPsn(String usrPsn) {
		this.usrPsn = usrPsn;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getUsrTel() {
		return usrTel;
	}
	public void setUsrTel(String usrTel) {
		this.usrTel = usrTel;
	}
	public String getBuseNam() {
		return buseNam;
	}
	public void setBuseNam(String buseNam) {
		this.buseNam = buseNam;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleCde() {
		return roleCde;
	}
	public void setRoleCde(String roleCde) {
		this.roleCde = roleCde;
	}
	public String getStartYmd() {
		return startYmd;
	}
	public void setStartYmd(String startYmd) {
		this.startYmd = startYmd;
	}
	public String getEndYmd() {
		return endYmd;
	}
	public void setEndYmd(String endYmd) {
		this.endYmd = endYmd;
	}
	public String getRegYmd() {
		return regYmd;
	}
	public void setRegYmd(String regYmd) {
		this.regYmd = regYmd;
	}
	public String getYno() {
		return yno;
	}
	public void setYno(String yno) {
		this.yno = yno;
	}
	public int getBssNum() {
		return bssNum;
	}
	public void setBssNum(int bssNum) {
		this.bssNum = bssNum;
	}
	public String getBssNam() {
		return bssNam;
	}
	public void setBssNam(String bssNam) {
		this.bssNam = bssNam;
	}
	public String getSiMuCde() {
		return siMuCde;
	}
	public void setSiMuCde(String siMuCde) {
		this.siMuCde = siMuCde;
	}
	public String getSiMuNam() {
		return siMuNam;
	}
	public void setSiMuNam(String siMuNam) {
		this.siMuNam = siMuNam;
	}
	public String getUsrPstnNam() {
		return usrPstnNam;
	}
	public void setUsrPstnNam(String usrPstnNam) {
		this.usrPstnNam = usrPstnNam;
	}
	public String getUseyno() {
		return useyno;
	}
	public void setUseyno(String useyno) {
		this.useyno = useyno;
	}
	public String getBuseCde() {
		return buseCde;
	}
	public void setBuseCde(String buseCde) {
		this.buseCde = buseCde;
	}
	public String getCtkVcnCde() {
		return ctkVcnCde;
	}
	public void setCtkVcnCde(String ctkVcnCde) {
		this.ctkVcnCde = ctkVcnCde;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getLatestLoginDate() {
		return latestLoginDate;
	}
	public void setLatestLoginDate(String latestLoginDate) {
		this.latestLoginDate = latestLoginDate;
	}
	public String getLatestFailDate() {
		return latestFailDate;
	}
	public void setLatestFailDate(String latestFailDate) {
		this.latestFailDate = latestFailDate;
	}
	public int getLoginFailCnt() {
		return loginFailCnt;
	}
	public void setLoginFailCnt(int loginFailCnt) {
		this.loginFailCnt = loginFailCnt;
	}
	public String getmenuCde() {
		return menuCde;
	}
	public void setmenuCde(String menuCde) {
		this.menuCde = menuCde;
	}
	public String getGrntRole() {
		return grntRole;
	}
	public void setGrntRole(String grntRole) {
		this.grntRole = grntRole;
	}
	public String getUseYn() {
		return useYn;
	}
	public String getExtentYn() {
		return extentYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public void setExtentYn(String extentYn) {
		this.extentYn = extentYn;
	}
	public String getSelectIdPw() {
		return selectIdPw;
	}
	public void setSelectIdPw(String selectIdPw) {
		this.selectIdPw = selectIdPw;
	}
	public String getConnIp() {
		return connIp;
	}
	public void setConnIp(String connIp) {
		this.connIp = connIp;
	}
	public String getPwChgYmd() {
		return pwChgYmd;
	}
	public void setPwChgYmd(String pwChgYmd) {
		this.pwChgYmd = pwChgYmd;
	}
	public String getPwExtYmd() {
		return pwExtYmd;
	}
	public void setPwExtYmd(String pwExtYmd) {
		this.pwExtYmd = pwExtYmd;
	}

//	public String getMenucde() {
//		return menucde;
//	}
//	public void setMenucde(String menucde) {
//		this.menucde = menucde;
//	}









}
