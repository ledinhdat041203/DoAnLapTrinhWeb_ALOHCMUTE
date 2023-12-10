package vn.hcmute.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel {
	private long commentID;
	private String content;
	private String userFullName;
	private String avata;
	private Date commentDate;
}
