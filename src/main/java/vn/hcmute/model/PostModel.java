package vn.hcmute.model;

import java.sql.Date;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PostModel {
		private long postID;
		private long groupID;
		private long userID;
	    private String imageURL;
	    private String content;
	    private Date postDate;
	    private long likeCount;
	    private String userFullName;
		public PostModel(long postID, long groupID, long userID, String imageURL, String content, Date postDate,
				long likeCount, String userFullName) {
			super();
			this.postID = postID;
			this.groupID = groupID;
			this.userID = userID;
			this.imageURL = imageURL;
			this.content = content;
			this.postDate = postDate;
			this.likeCount = likeCount;
			this.userFullName = userFullName;
		}
		public PostModel() {
			super();
		}
		public long getPostID() {
			return postID;
		}
		public void setPostID(long postID) {
			this.postID = postID;
		}
		public long getGroupID() {
			return groupID;
		}
		public void setGroupID(long groupID) {
			this.groupID = groupID;
		}
		public long getUserID() {
			return userID;
		}
		public void setUserID(long userID) {
			this.userID = userID;
		}
		public String getImageURL() {
			return imageURL;
		}
		public void setImageURL(String imageURL) {
			this.imageURL = imageURL;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Date getPostDate() {
			return postDate;
		}
		public void setPostDate(Date postDate) {
			this.postDate = postDate;
		}
		public long getLikeCount() {
			return likeCount;
		}
		public void setLikeCount(long likeCount) {
			this.likeCount = likeCount;
		}
		public String getUserFullName() {
			return userFullName;
		}
		public void setUserFullName(String userFullName) {
			this.userFullName = userFullName;
		}

	    
	    
		
}