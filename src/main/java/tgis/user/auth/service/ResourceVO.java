package tgis.user.auth.service;

import java.io.Serializable;

public class ResourceVO implements Serializable {

	private static final long serialVersionUID = -8334586868069592567L;

	private String usrId;

	private String progUrl;

	private String menuId;

	private String menuNam;

	private String rMenuId;

	private String cuMenuId;

	private String dMenuId;

	public String getUsrId() {
		return usrId;
	}

	public String getrMenuId() {
		return rMenuId;
	}

	public void setrMenuId(String rMenuId) {
		this.rMenuId = rMenuId;
	}

	public String getcuMenuId() {
		return cuMenuId;
	}

	public void setcuMenuId(String cuMenuId) {
		this.cuMenuId = cuMenuId;
	}

	public String getdMenuId() {
		return dMenuId;
	}

	public void setdMenuId(String dMenuId) {
		this.dMenuId = dMenuId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getProgUrl() {
		return progUrl;
	}

	public void setProgUrl(String progUrl) {
		this.progUrl = progUrl;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNam() {
		return menuNam;
	}

	public void setMenuNam(String menuNam) {
		this.menuNam = menuNam;
	}


}
