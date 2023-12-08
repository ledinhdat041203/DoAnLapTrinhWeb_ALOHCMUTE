package vn.hcmute.entities;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class StatusAccountEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Boolean status;
	private int code;
	@OneToOne(targetEntity = UserEntity.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "idcode",referencedColumnName = "idaccount")
	private UserEntity userCode;
	public StatusAccountEntity( int code, UserEntity userCode) {
		this.code = code;
		this.userCode = userCode;
	}
	public StatusAccountEntity() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public UserEntity getUserCode() {
		return userCode;
	}
	public void setUserCode(UserEntity userCode) {
		this.userCode = userCode;
	}
	
	
	
	
	

}
