package vn.hcmute.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Notification")
public class NotificationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notifyid", columnDefinition = "BIGINT")
	private long notifyID;
	
	@Column(name = "content", columnDefinition =  "text")
	private String content;
	
	@Column(name = "date", columnDefinition = "Date")
	private Date dateNotify;
	
	@ManyToOne
    @JoinColumn(name = "userid")
    private UserInfoEntity user;
	
	@Column(name = "status", columnDefinition = "bit")
	private boolean status;
	
	@Column(name = "link", columnDefinition = "text")
	private String link;
	
}