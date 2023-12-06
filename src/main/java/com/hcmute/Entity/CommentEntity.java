package com.hcmute.Entity;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;
import org.springframework.data.repository.CrudRepository;


@Entity
@Table(name = "comments")

public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentid", columnDefinition = "BIGINT")
    private long commentidID;
    
    @JsonProperty("content")
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "postid")
    @JsonBackReference
    private PostEntity postCommnent;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonBackReference
    private UserInfoEntity userComment;

    @JsonProperty("commentDate")
    @Column(name = "commentdate", columnDefinition = "DATE")
    private Date commentDate;
    
    
    // Constructors
    public CommentEntity() {}

    public CommentEntity(long commentidID, String content, PostEntity postCommnent, UserInfoEntity userComment, Date commentDate) {
        this.commentidID = commentidID;
        this.content = content;
        this.postCommnent = postCommnent;
        this.userComment = userComment;
        this.commentDate = commentDate;
    }
    
    public CommentEntity(long commentidID, String content, long postid, long userid, Date commentDate) {
        this.commentidID = commentidID;
        this.content = content;

        // Thiết lập trực tiếp postCommnent và userComment từ postid và userid
        this.postCommnent = new PostEntity();
        this.postCommnent.setPostID(postid);

        this.userComment = new UserInfoEntity();
        this.userComment.setUserID(userid);

        this.commentDate = commentDate;
    }

    // Getters and setters

    // toString() excluding postCommnent and userComment to avoid circular references
    @Override
    public String toString() {
        String userName = (userComment != null) ? userComment.getFullName() : "Unknown User";
        return "CommentEntity{" +
                "commentidID=" + commentidID +
                ", userName=" + userName +
                ", content='" + content + '\'' +
                ", commentDate=" + commentDate +
                '}';
    }
    public String getUserName() {
        return (userComment != null) ? userComment.getFullName() : "Unknown User";
    }

    public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

        // Phương thức save sẽ tự động thực hiện INSERT hoặc UPDATE tùy thuộc vào commentidID
        CommentEntity save(CommentEntity commentEntity);
    }

	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	public PostEntity getPostCommnent() {
		// TODO Auto-generated method stub
		return postCommnent;
	}

	public Date getCommentDate() {
		// TODO Auto-generated method stub
		return commentDate;
	}

	public UserInfoEntity getUserComment() {
		// TODO Auto-generated method stub
		return userComment;
	}

	public long getCommentId() {
		// TODO Auto-generated method stub
		return commentidID;
	}
	public void setContent(String content) {
        this.content = content;
    }


	


    
}
