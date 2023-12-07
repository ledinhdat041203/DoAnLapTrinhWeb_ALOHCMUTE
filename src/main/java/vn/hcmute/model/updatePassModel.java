package vn.hcmute.model;

public class updatePassModel {
	private String newPass;
	private String confirmPass;
	private String token;

	public updatePassModel(String newPass, String confirmPass, String token) {
		this.newPass = newPass;
		this.confirmPass = confirmPass;
		this.token = token;
	}

	public updatePassModel() {
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
