package vn.hcmute.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoModel {
	private String fullName;
	private Date dateOfBirth;
	private String avata;
	private String phoneNumber;

}
