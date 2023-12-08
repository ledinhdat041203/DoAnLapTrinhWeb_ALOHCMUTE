package vn.hcmute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.FriendsEntity;
import vn.hcmute.entities.LikeEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.model.UserInfoModel;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IPostService postService;

    @GetMapping("")
    public String list(ModelMap model, HttpSession session, ModelMap post, Model listpost) {
        Long userID = (Long) session.getAttribute("userInfoID");
        
        if (userID == null) {
            // Xử lý trường hợp userID là null
            return "redirect:/login"; // hoặc chuyển hướng đến trang đăng nhập khác
        }
        List<PostModel> posts = postService.findByUserUserID(userID);
        Optional<UserInfoEntity> userOptional = userInfoService.findById(userID);
        if (userOptional.isEmpty()) {
            // Xử lý trường hợp userInfo không tồn tại
            return "redirect:/info/list";
        }
        UserInfoEntity user = userOptional.get();
        Long following = (long) user.getListFriend1().size();
        Long follower = (long) user.getListFriend2().size();
        UserInfoModel userModel = userInfoService.convertToUserInfoModel(user);
        userModel.setCountFollower(follower);
        userModel.setCountFollowing(following);
        userModel.setCountPost((long)posts.size());
        model.addAttribute("info", userModel);
        post.addAttribute("post", new PostEntity());

        
        listpost.addAttribute("list", posts);
        
        return "profile";
    }

    @GetMapping("/update/{userid}")
    public String update(@PathVariable Long userid, Model model) {
        Optional<UserInfoEntity> userInfoOptional = userInfoService.findById(userid);
        if (userInfoOptional.isEmpty()) {
            // Xử lý trường hợp userInfo không tồn tại
            return "redirect:/info/list";
        }

        UserInfoEntity userInfo = userInfoOptional.get();
        model.addAttribute("userInfo", userInfo);
        return "update";
    }

    @PostMapping("/saveUpdate")
    public String saveUserInfoUpdate(@ModelAttribute("userInfo") UserInfoEntity userInfo) {
        if (userInfo == null || userInfo.getUserID() == null) {
            // Xử lý trường hợp userInfo hoặc userID là null
            return "redirect:/profile";
        }

        Optional<UserInfoEntity> existingUser = userInfoService.findById(userInfo.getUserID());
        if (existingUser.isPresent()) {
            // Update thông tin của người dùng
            userInfoService.save(userInfo);
        } else {
            // Xử lý trường hợp người dùng không tồn tại
            return "redirect:/info/list";
        }

        return "redirect:/profile";
    }
}
