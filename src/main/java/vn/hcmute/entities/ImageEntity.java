package vn.hcmute.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Image")
public class ImageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "imageID", columnDefinition = "BIGINT")
	private long imageID;
	@Column(name = "pathimage", columnDefinition = "nvarchar(MAX)", nullable = false)
	private String pathimage;
	public ImageEntity(String pathimage) {
		super();
		this.pathimage = pathimage;
	}
	
	

	
}
