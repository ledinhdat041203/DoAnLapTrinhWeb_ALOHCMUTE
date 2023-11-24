package vn.iostar.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iostar.entities.UserEntity;
import vn.iostar.entities.UserInfoEntity;
import vn.iostar.model.UserAcountModel;
import vn.iostar.model.UserInfoModel;
import vn.iostar.service.IUserInfoService;
import vn.iostar.service.IUserService;
import vn.iostar.service.UserServiceImple;


@Controller
public class UserController {
	@Autowired
	UserAcountModel u;
	@Autowired
	IUserService user_service;
	@Autowired
	IUserInfoService user_info_service;
	@RequestMapping("/login")
	public String Showlogin(ModelMap model,HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
		for (Cookie c : cookies)
		{
			if(c.getName().equals("email"))
			{
				model.addAttribute("emailcookie", c.getValue());
			}
			else if(c.getName().equals("pass"))
			{
				model.addAttribute("passcookie", c.getValue());
			}
		}
		}
		
		return "login";
	}
	@PostMapping("/checklogin")
	public String CheckLoginn(HttpServletResponse response,ModelMap model,@RequestParam("email")String Email,@RequestParam("password")String pass,HttpSession session,@ModelAttribute UserAcountModel user_account)
	{
		if (user_service.checkLogin(Email, pass))
		{
			System.out.println(user_account.getEmail());
			Cookie name = new Cookie("email", Email);
			Cookie password = new Cookie("pass", pass);
			name.setMaxAge(60);
			//System.out.println(user_account.isRemember());
			if(user_account.isRemember()==true)
				password.setMaxAge(10);
			else
				password.setMaxAge(0);
			response.addCookie(name);
			response.addCookie(password);
			session.setAttribute("username",user_service.findByemailContaining(Email).get().getIdAccount());
			return "user";
		}

		return "login";
	}
	@GetMapping("/logout")
	public ModelAndView Logout(HttpSession session,ModelMap model)
	{
		session.removeAttribute("username");
		return new ModelAndView("redirect:/login",model);
	}
	@GetMapping("/registerOrFail")
	public String Register()
	{
		
		return "Register";
	}
	@PostMapping("/registerOrFail")
	public String Show(ModelMap model,@ModelAttribute UserAcountModel user_account, @ModelAttribute("user_info") UserInfoEntity user_info)
	{
		//user_account.setUserInfo(user_info);
		UserEntity user_entity = new UserEntity();
		Optional<UserEntity> ua= user_service.findByemailContaining(user_account.getEmail());
		Optional<UserInfoEntity> ui = user_info_service.findByphoneNumberContaining(user_info.getPhoneNumber());
		if(ua.isPresent())
			return "redirect:/registerOrFail?emailexist";
		else if(ui.isPresent())
			//user_service.save(user_account);
			return "redirect:/registerOrFail?phone";
		else if(!user_account.getPass().equals(user_account.getCheckPass()))
			return "redirect:/registerOrFail?pass"; 
		else
		{
			BeanUtils.copyProperties(user_account, user_entity);
			user_info_service.save(user_info);
			user_entity.setUserInfo(user_info);
			user_service.save(user_entity);
			return "redirect:/registerOrFail?success";
		}



	}
	
	
}
