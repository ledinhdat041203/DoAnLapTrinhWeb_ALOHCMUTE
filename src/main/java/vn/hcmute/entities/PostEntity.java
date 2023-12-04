package vn.hcmute.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Post")
public class PostEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postid", columnDefinition = "BIGINT")
	private long postID;
	
	@ManyToOne
    @JoinColumn(name = "groupid")
    private GroupEntity groupPost;
	
	@ManyToOne
    @JoinColumn(name = "userid")
    private UserInfoEntity user;
	
	@Column(name = "content", columnDefinition = "text")
	private String content;
	@Column(name = "image", columnDefinition = "nvarchar(255)")
	private String image;
	@Column(name = "postdate", columnDefinition = "DATE")
	private Date postDate;
	
	 @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	 private List<LikeEntity> listLikes;
	 
	 @OneToMany(mappedBy = "postCommnent", fetch = FetchType.LAZY)
	 private List<CommentsEntity> listComments;

	public PostEntity(long postID, GroupEntity groupPost, UserInfoEntity user, String content, String image,
			Date postDate, List<LikeEntity> listLikes, List<CommentsEntity> listComments) {
		super();
		this.postID = postID;
		this.groupPost = groupPost;
		this.user = user;
		this.content = content;
		this.image = image;
		this.postDate = postDate;
		this.listLikes = listLikes;
		this.listComments = listComments;
	}

	public PostEntity() {
		super();
	}

	public long getPostID() {
		return postID;
	}

	public void setPostID(long postID) {
		this.postID = postID;
	}

	public GroupEntity getGroupPost() {
		return groupPost;
	}

	public void setGroupPost(GroupEntity groupPost) {
		this.groupPost = groupPost;
	}

	public UserInfoEntity getUser() {
		return user;
	}

	public void setUser(UserInfoEntity user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public List<LikeEntity> getListLikes() {
		return listLikes;
	}

	public void setListLikes(List<LikeEntity> listLikes) {
		this.listLikes = listLikes;
	}

	public List<CommentsEntity> getListComments() {
		return listComments;
	}

	public void setListComments(List<CommentsEntity> listComments) {
		this.listComments = listComments;
	}
	 
	 
	 

	
}

