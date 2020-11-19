package tgis.common.vo;

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
public class UserInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 62491065656834913L;
	
	private String memNo;
	private String id;
	private String name;
	private String pwd;
	private String userAuth;
	private String authority;
	private String regnCode;
	private String sid;
	private String gpkiSid;
	private String age;
	private String gender;
	private String telephone;
	private String handphone;
	private String fax;
	private String email;
	private String grade;
	private String sidoNm;
	private String cggNm;
	private String umdNm;
	private String position;
	private String duty;
	private String transference;
	private String performMon;
	private String aprState;
	private String lastConn;
	private String joinDate;
	private String roleId;
	private String systemCd;
	private String sfTeamCode;
	private String upsSfTeamCode;
	private String sfTeamGbn;
	private String sfTeamLevel;
	private String saeallId;
	private String saeallUserId;
	private String krcnbbAuth;
	private String saeallAuth;
	private String nnjyAuth;
	private String uprCode;
	private String sprmCode;
	private String orgAndTeamCode;
	
	/**
	 * @return the memNo
	 */
	public String getMemNo() {
		return memNo;
	}
	
	/**
	 * @param memNo the memNo to set
	 */
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * @return the userAuth
	 */
	public String getUserAuth() {
		return userAuth;
	}
	
	/**
	 * @param userAuth the userAuth to set
	 */
	public void setUserAuth(String userAuth) {
		this.userAuth = userAuth;
	}
	
	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	/**
	 * @return the regnCode
	 */
	public String getRegnCode() {
		return regnCode;
	}
	
	/**
	 * @param regnCode the regnCode to set
	 */
	public void setRegnCode(String regnCode) {
		this.regnCode = regnCode;
	}
	
	/**
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}
	
	/**
	 * @param sid the sid to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	/**
	 * @return the gpkiSid
	 */
	public String getGpkiSid() {
		return gpkiSid;
	}
	
	/**
	 * @param gpkiSid the gpkiSid to set
	 */
	public void setGpkiSid(String gpkiSid) {
		this.gpkiSid = gpkiSid;
	}
	
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}
	
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	/**
	 * @return the handphone
	 */
	public String getHandphone() {
		return handphone;
	}
	
	/**
	 * @param handphone the handphone to set
	 */
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}
	
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	/**
	 * @return the sidoNm
	 */
	public String getSidoNm() {
		return sidoNm;
	}
	
	/**
	 * @param sidoNm the sidoNm to set
	 */
	public void setSidoNm(String sidoNm) {
		this.sidoNm = sidoNm;
	}
	
	/**
	 * @return the cggNm
	 */
	public String getCggNm() {
		return cggNm;
	}
	
	/**
	 * @param cggNm the cggNm to set
	 */
	public void setCggNm(String cggNm) {
		this.cggNm = cggNm;
	}
	
	/**
	 * @return the umdNm
	 */
	public String getUmdNm() {
		return umdNm;
	}
	
	/**
	 * @param umdNm the umdNm to set
	 */
	public void setUmdNm(String umdNm) {
		this.umdNm = umdNm;
	}
	
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
	 * @return the duty
	 */
	public String getDuty() {
		return duty;
	}
	
	/**
	 * @param duty the duty to set
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	/**
	 * @return the transference
	 */
	public String getTransference() {
		return transference;
	}
	
	/**
	 * @param transference the transference to set
	 */
	public void setTransference(String transference) {
		this.transference = transference;
	}
	
	/**
	 * @return the performMon
	 */
	public String getPerformMon() {
		return performMon;
	}
	
	/**
	 * @param performMon the performMon to set
	 */
	public void setPerformMon(String performMon) {
		this.performMon = performMon;
	}
	
	/**
	 * @return the aprState
	 */
	public String getAprState() {
		return aprState;
	}
	
	/**
	 * @param aprState the aprState to set
	 */
	public void setAprState(String aprState) {
		this.aprState = aprState;
	}
	
	/**
	 * @return the lastConn
	 */
	public String getLastConn() {
		return lastConn;
	}
	
	/**
	 * @param lastConn the lastConn to set
	 */
	public void setLastConn(String lastConn) {
		this.lastConn = lastConn;
	}
	
	/**
	 * @return the joinDate
	 */
	public String getJoinDate() {
		return joinDate;
	}
	
	/**
	 * @param joinDate the joinDate to set
	 */
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * @return the systemCd
	 */
	public String getSystemCd() {
		return systemCd;
	}
	
	/**
	 * @param systemCd the systemCd to set
	 */
	public void setSystemCd(String systemCd) {
		this.systemCd = systemCd;
	}
	
	/**
	 * @return the sfTeamCode
	 */
	public String getSfTeamCode() {
		return sfTeamCode;
	}
	
	/**
	 * @param sfTeamCode the sfTeamCode to set
	 */
	public void setSfTeamCode(String sfTeamCode) {
		this.sfTeamCode = sfTeamCode;
	}
	
	/**
	 * @return the upsSfTeamCode
	 */
	public String getUpsSfTeamCode() {
		return upsSfTeamCode;
	}
	
	/**
	 * @param upsSfTeamCode the upsSfTeamCode to set
	 */
	public void setUpsSfTeamCode(String upsSfTeamCode) {
		this.upsSfTeamCode = upsSfTeamCode;
	}
	
	/**
	 * @return the sfTeamGbn
	 */
	public String getSfTeamGbn() {
		return sfTeamGbn;
	}
	
	/**
	 * @param sfTeamGbn the sfTeamGbn to set
	 */
	public void setSfTeamGbn(String sfTeamGbn) {
		this.sfTeamGbn = sfTeamGbn;
	}
	
	/**
	 * @return the sfTeamLevel
	 */
	public String getSfTeamLevel() {
		return sfTeamLevel;
	}
	
	/**
	 * @param sfTeamLevel the sfTeamLevel to set
	 */
	public void setSfTeamLevel(String sfTeamLevel) {
		this.sfTeamLevel = sfTeamLevel;
	}
	
	/**
	 * @return the saeallId
	 */
	public String getSaeallId() {
		return saeallId;
	}
	
	/**
	 * @param saeallId the saeallId to set
	 */
	public void setSaeallId(String saeallId) {
		this.saeallId = saeallId;
	}
	
	/**
	 * @return the saeallUserId
	 */
	public String getSaeallUserId() {
		return saeallUserId;
	}
	
	/**
	 * @param saeallUserId the saeallUserId to set
	 */
	public void setSaeallUserId(String saeallUserId) {
		this.saeallUserId = saeallUserId;
	}
	
	/**
	 * @return the krcnbbAuth
	 */
	public String getKrcnbbAuth() {
		return krcnbbAuth;
	}
	
	/**
	 * @param krcnbbAuth the krcnbbAuth to set
	 */
	public void setKrcnbbAuth(String krcnbbAuth) {
		this.krcnbbAuth = krcnbbAuth;
	}
	
	/**
	 * @return the saeallAuth
	 */
	public String getSaeallAuth() {
		return saeallAuth;
	}
	
	/**
	 * @param saeallAuth the saeallAuth to set
	 */
	public void setSaeallAuth(String saeallAuth) {
		this.saeallAuth = saeallAuth;
	}
	
	/**
	 * @return the nnjyAuth
	 */
	public String getNnjyAuth() {
		return nnjyAuth;
	}
	
	/**
	 * @param nnjyAuth the nnjyAuth to set
	 */
	public void setNnjyAuth(String nnjyAuth) {
		this.nnjyAuth = nnjyAuth;
	}
	
	/**
	 * @return the uprCode
	 */
	public String getUprCode() {
		return uprCode;
	}
	
	/**
	 * @param uprCode the uprCode to set
	 */
	public void setUprCode(String uprCode) {
		this.uprCode = uprCode;
	}
	
	/**
	 * @return the sprmCode
	 */
	public String getSprmCode() {
		return sprmCode;
	}
	
	/**
	 * @param sprmCode the sprmCode to set
	 */
	public void setSprmCode(String sprmCode) {
		this.sprmCode = sprmCode;
	}
	
	/**
	 * @return the orgAndTeamCode
	 */
	public String getOrgAndTeamCode() {
		return orgAndTeamCode;
	}
	
	/**
	 * @param orgAndTeamCode the orgAndTeamCode to set
	 */
	public void setOrgAndTeamCode(String orgAndTeamCode) {
		this.orgAndTeamCode = orgAndTeamCode;
	}

}
