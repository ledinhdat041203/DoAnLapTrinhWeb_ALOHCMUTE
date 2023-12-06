package com.hcmute.Models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {
		private long postID;
		private long groupID;
		private long userID;
	    private String imageURL;
	    private String content;
	    private Date postDate;
	    private long likeCount;
	    private String userFullName;
}
