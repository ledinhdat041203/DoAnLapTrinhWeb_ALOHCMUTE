package vn.hcmute.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
