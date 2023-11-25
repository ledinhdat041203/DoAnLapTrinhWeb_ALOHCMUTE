package vn.iostar.entities;

import java.io.Serializable;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "UserAccount")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idaccount", columnDefinition = "BIGINT")
	private Long idAccount;
	@Column(name = "username", columnDefinition = "nvarchar(255)", nullable = false)
	private String userName;
	@Column(name = "email", columnDefinition = "nvarchar(255)", nullable = false)
	private String email;
	@Column(name = "pass", columnDefinition = "nvarchar(32)", nullable = false)
	private String pass;

	@OneToOne
	@JoinColumn(name = "User_info_id", referencedColumnName = "userid")
	private UserInfoEntity userInfo;

	public Long getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Long idAccount) {
		this.idAccount = idAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public UserInfoEntity getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
