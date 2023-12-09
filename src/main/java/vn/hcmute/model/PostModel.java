package vn.hcmute.model;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.hcmute.entities.CommentEntity;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostModel {
		private long postID;
		private long groupID;
		private long userID;
		private String userAccountName;
		private String avata;
	    private String imageURL;
	    private String content;
	    private Date postDate;
	    private long likeCount;
	    private String userFullName;
	    private boolean liked;
	    private long commentCount;
	    private List<CommentEntity> listComment;
	    private String avata;
}

