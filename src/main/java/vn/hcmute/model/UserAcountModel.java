package vn.hcmute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAcountModel {
	private String userName;
	private String email;
	private String pass;
	private String checkPass;
	private boolean remember;
}