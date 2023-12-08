package vn.hcmute.model;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserInfoModel {
	private long userID;
	@NotEmpty
	private String fullName;
	private Date dateOfBirth;
	private String avata;
	private String address;
	private String phoneNumber;
	private Long countFollower;
	private Long countFollowing;
	private Long countPost;

	private MultipartFile imageFile;
	private Boolean isEdit=false;

}
