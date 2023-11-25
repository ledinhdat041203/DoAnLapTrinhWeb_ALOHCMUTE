package vn.iostar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {
	
	    private String imageURL;
	    private String content;
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
	    
}
