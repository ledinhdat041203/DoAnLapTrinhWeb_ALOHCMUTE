package vn.hcmute.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;


@Controller
@RequestMapping("/info")
public class UserInfoController {
    @Autowired
    IUserInfoService userInfoService;

    @Autowired
    IPostService postService;

    @GetMapping("/list")
    public String list(ModelMap model, ModelMap postModelMap) {
        List<UserInfoEntity> userList = userInfoService.findAll();
        model.addAttribute("info", userList);

        Long userId = 3L; // Thay đổi userId theo logic của bạn
        List<PostModel> userPosts = postService.findByUserUserID(userId);
        postModelMap.addAttribute("listPosts", userPosts);

        return "index";
    }

    @GetMapping("/update/{userid}")
    public String update(@PathVariable Long userid, Model model) {
        UserInfoEntity userInfo = userInfoService.findById(userid);
        if (userInfo != null) {
            model.addAttribute("userInfo", userInfo);
            return "update";
        } else {
            // Xử lý trường hợp userInfo không tồn tại
            return "redirect:/info/list";
        }
    }

    @PostMapping("/saveUpdate")
    public String saveUserInfoUpdate(@ModelAttribute("userInfo") UserInfoEntity userInfo) {
        if (userInfo != null) {
            userInfoService.save(userInfo);
            return "redirect:/info/list";
        } else {
            // Xử lý trường hợp userInfo là null
            return "redirect:/info/list";
        }
    }
}
