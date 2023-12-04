package vn.hcmute.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Messages")
public class MessagesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "messageid", columnDefinition = "BIGINT")
	private long messageID;
	

	@ManyToOne
    @JoinColumn(name = "senderid")
    private UserInfoEntity sender;
	
	@ManyToOne
    @JoinColumn(name = "receiverid")
    private UserInfoEntity receiver;
	
	@Column(name = "content", columnDefinition = "text")
	private String content;

}
