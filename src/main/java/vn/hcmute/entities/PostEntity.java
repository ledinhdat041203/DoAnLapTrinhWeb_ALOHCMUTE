package vn.hcmute.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Post")
public class PostEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postid", columnDefinition = "BIGINT")
	private long postID;

	@ManyToOne
    @JoinColumn(name = "userid")
    private UserInfoEntity user;

	@ManyToOne
    @JoinColumn(name = "groupid")
    private GroupEntity groupPost;
	
	@JsonProperty("content")
	@Column(name = "content", columnDefinition = "NTEXT")

	private String content;
	@Column(name = "image", columnDefinition = "nvarchar(255)")

	@JsonProperty("image")
	private String image;
	@Column(name = "postdate", columnDefinition = "DATETIME")
	private Timestamp postDate;
	
	 @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	 private List<LikeEntity> listLikes;


	 @OneToMany(mappedBy = "postCommnent", fetch = FetchType.LAZY)
	 private List<CommentEntity> listComments;
	 
	 
	
}

