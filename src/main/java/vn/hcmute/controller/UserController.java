package vn.hcmute.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.UserEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.EmailInfo;
import vn.hcmute.model.UserAcountModel;
import vn.hcmute.model.updatePassModel;
import vn.hcmute.service.IMailService;
import vn.hcmute.service.IUserInfoService;
import vn.hcmute.service.IUserService;

@Controller
public class UserController {
	@Autowired
	UserAcountModel u;
	@Autowired
	IUserService user_service;
	@Autowired
	IUserInfoService user_info_service;
	@Autowired
	IMailService imail;

	@RequestMapping("/login")
	public String Showlogin(ModelMap model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("email")) {
					model.addAttribute("emailcookie", c.getValue());
				} else if (c.getName().equals("pass")) {
					model.addAttribute("passcookie", c.getValue());
				}
			}
		}

		return "login";
	}

	@PostMapping("/checklogin")
	public String CheckLoginn(HttpServletResponse response, ModelMap model, @RequestParam("email") String Email,
			@RequestParam("password") String pass, HttpSession session, @ModelAttribute UserAcountModel user_account) {
		if (user_service.checkLogin(Email, pass)) {
			System.out.println(user_account.getEmail());
			Cookie name = new Cookie("email", Email);
			Cookie password = new Cookie("pass", pass);
			name.setMaxAge(60);
			// System.out.println(user_account.isRemember());
			if (user_account.isRemember() == true)
				password.setMaxAge(10);
			else
				password.setMaxAge(0);
			response.addCookie(name);
			response.addCookie(password);
			session.setAttribute("userID", user_service.findByemailContaining(Email).get().getIdAccount());
			session.setAttribute("userInfoID",
					user_service.findByemailContaining(Email).get().getUserInfo().getUserID());
			session.setAttribute("userFullName",
					user_service.findByemailContaining(Email).get().getUserInfo().getFullName());

			return "redirect:/home";

		}

		return "login";
	}

	@GetMapping("/logout")
	public ModelAndView Logout(HttpSession session, ModelMap model) {
		session.removeAttribute("userID");
		session.removeAttribute("userInfoID");
		session.removeAttribute("userFullName");
		return new ModelAndView("redirect:/login", model);
	}

	@GetMapping("/registerOrFail")
	public String Register() {
		return "Register";
	}

	@PostMapping("/registerOrFail")
	public String Show(ModelMap model,@ModelAttribute UserAcountModel user_account, @ModelAttribute UserInfoEntity user_info)
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

	@GetMapping("/forgot")
	public String showFormMail(ModelMap model) {
		model.addAttribute("mail", new EmailInfo());
		return "sendMail";
	}

	@PostMapping("/sendmail")
	public String Send(HttpServletRequest request, ModelMap model, @ModelAttribute("mail") EmailInfo emailInfo) {
		/*
		 * try { Optional<UserEntity> user =
		 * user_service.findByemailContaining(emailInfo.getTo());
		 * imail.send(null,null,user.get()); } catch (Exception e) { return "wrong"; }
		 * return "Correct";
		 */
		Optional<UserEntity> user = user_service.findByemailContaining(emailInfo.getTo());
		if (user.isPresent()) {
			String token = UUID.randomUUID().toString();
			Optional<ResetPasswordEntity> resetPass = user_service.findByUserResetPass(user.get());
			if (!resetPass.isEmpty()) {
				user_service.deleteById(resetPass.get().getId());
				System.out.print("da xoa");
			}
			user_service.createToken(user.get(), token);
			try {
				imail.constructResetTokenEmail(getAppUrl(request), token, user.get());

			} catch (Exception e) {
				return "redirect:forgot?no";
			}
		} else
			return "redirect:forgot?wrong";
		return "redirect:forgot?yes";
	}

	private String getAppUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		return url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
	}

	@GetMapping("/user/changePassword")
	public String showChangePasswordPage(ModelMap model, @RequestParam(name = "token") String token1) {
		String result = user_service.validToken(token1);
		System.out.print(token1);
		if (result != null)
			return "login";
		else {
			model.addAttribute("tokenvalue", token1);
			model.addAttribute("pass", new updatePassModel());
			return "updatePass";
		}
	}

	/*
	 * @GetMapping("updatePass") public String Show(ModelMap model) {
	 * model.addAttribute("pass", new updatePassModel()); return "updatePass"; }
	 */
	@PostMapping("user/updatePassword")
	public String savePass(@ModelAttribute("pass") updatePassModel pass, HttpServletResponse response) {
		String result = user_service.validToken(pass.getToken());
		if (result != null)
			return "redirect:updatePassword?fail";
		Cookie name = new Cookie("token", pass.getToken());
		name.setMaxAge(60);
		response.addCookie(name);
		UserEntity user = user_service.findByToken(pass.getToken()).getUserResetPass();
		if (user != null) {
			System.out.print(user);
			if (pass.getNewPass().equals(pass.getConfirmPass())) {
				user_service.changePass(user, pass.getConfirmPass());
				return "redirect:updatePassword?success";
			} else
				return "redirect:updatePassword?wrong";
		} else
			return "redirect:updatePassword?noexist";
	}

	@GetMapping("user/updatePassword")
	public String show(HttpServletRequest request, ModelMap model) {
		Cookie[] c = request.getCookies();
		if (c != null) {
			for (Cookie cook : c) {
				if (cook.getName().equals("token")) {
					model.addAttribute("tokenvalue", cook.getValue());
				}
			}
		}
		return "updatePass";
	}

}
