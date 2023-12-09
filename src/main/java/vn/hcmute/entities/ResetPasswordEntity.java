package vn.hcmute.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class ResetPasswordEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final int EXPIRATION = 60*24;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pass", columnDefinition = "BIGINT")
	private Long id;
	private String token;
	@OneToOne(targetEntity = UserEntity.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pass_account", referencedColumnName = "idaccount")
	private UserEntity userResetPass;
	private Date expireDate;
	public ResetPasswordEntity(String token, UserEntity userResetPass) {
		this.token = token;
		this.userResetPass = userResetPass;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserEntity getUserResetPass() {
		return userResetPass;
	}
	public void setUserResetPass(UserEntity userResetPass) {
		this.userResetPass = userResetPass;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	@PrePersist
    private void setExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, EXPIRATION);
        this.expireDate = cal.getTime();
    }
	public ResetPasswordEntity() {
	}

	
	
	
	
	
	

	

}