package com.gatemonitor.db.tabelas;


public class RegistroConfiguracao extends RegistroAbstract {

	private String nameUser;
	private String emailUser;
	private String passwordUser;
	private String adminUser;

	public RegistroConfiguracao(int id, String nameUser, String emailUser,
			String passwordUser, String adminUser) {
		super(id);
		this.nameUser = nameUser;
		this.emailUser = emailUser;
		this.passwordUser = passwordUser;
		this.adminUser = adminUser;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getPasswordUser() {
		return passwordUser;
	}

	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}

	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	@Override
	public String toString() {
		return nameUser;
	}

}
