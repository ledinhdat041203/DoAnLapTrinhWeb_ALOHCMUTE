package vn.hcmute.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGoogle {
    public String given_name;
    public String name;
    public String email;
    public String picture;
    
}
