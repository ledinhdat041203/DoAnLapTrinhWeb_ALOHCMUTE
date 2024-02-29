package vn.hcmute.controller;

import java.io.IOException;

import java.util.Map;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import jakarta.mail.internet.MimeMessage;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.StatusAccountEntity;
import vn.hcmute.entities.UserEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.EmailInfo;
import vn.hcmute.model.InfoGoogleModel;
import vn.hcmute.model.UserAcountModel;

import vn.hcmute.model.UserGoogle;
import vn.hcmute.model.UserInfoModel;

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
	// lưu trữ cookie cho đăng nhập đợt sau

	@GetMapping("/login")
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

	// kiểm tra email pass và status kích hoạt
	@PostMapping("/checklogin")
	public String CheckLoginn(HttpServletResponse response, ModelMap model, @RequestParam("email") String Email,
			@RequestParam("password") String pass, HttpSession session, @ModelAttribute UserAcountModel user_account) {
		Optional<UserEntity> user = user_service.findByemailContaining(Email);
		Optional<StatusAccountEntity> status = user_service.findByuserCode(user.get());
		if (user_service.checkLogin(Email, pass) && status.get().getStatus() == true) {
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
			session.setAttribute("username", user_service.findByemailContaining(Email).get().getIdAccount());
			session.setAttribute("email", user_service.findByemailContaining(Email));

			session.setAttribute("userID", user_service.findByemailContaining(Email).get().getIdAccount());
			session.setAttribute("userInfoID",
					user_service.findByemailContaining(Email).get().getUserInfo().getUserID());
			session.setAttribute("userFullName",
					user_service.findByemailContaining(Email).get().getUserInfo().getFullName());

			return "redirect:/listpost";
		} else if (user_service.checkLogin(Email, pass) && status.get().getStatus() == false) {
			session.setAttribute("email", Email);
			return "redirect:login?false";
		}
		return "redirect:login?wrong";
	}

	@GetMapping("/logout")
	public ModelAndView Logout(HttpSession session, ModelMap model) {
		session.removeAttribute("username");
		return new ModelAndView("redirect:/login", model);
	}

	@GetMapping("/registerOrFail")
	public String Register() {

		return "Register";
	}

	@PostMapping("/registerOrFail")
	public String Show(ModelMap model, @ModelAttribute UserAcountModel user_account,
			@ModelAttribute UserInfoEntity user_info, HttpSession session) {
		// user_account.setUserInfo(user_info);
		UserEntity user_entity = new UserEntity();
		StatusAccountEntity status = new StatusAccountEntity();
		Optional<UserEntity> ua = user_service.findByemailContaining(user_account.getEmail());
		Optional<UserInfoEntity> ui = user_info_service.findByphoneNumberContaining(user_info.getPhoneNumber());
		if (ua.isPresent())
			return "redirect:/registerOrFail?emailexist";
		else if (ui.isPresent())
			// user_service.save(user_account);
			return "redirect:/registerOrFail?phone";
		else if (!user_account.getPass().equals(user_account.getCheckPass()))
			return "redirect:/registerOrFail?pass";
		else {
			BeanUtils.copyProperties(user_account, user_entity);
			user_info_service.save(user_info);
			user_entity.setUserInfo(user_info);
			user_service.save(user_entity);
			status.setStatus(false);
			status.setUserCode(user_entity);// khởi tạo status dưới sql
			user_service.save(status);
			session.setAttribute("email", user_account.getEmail()); // lưu session email để tiến hành gửi code về email
			return "redirect:/verify";
		}

	}

	@GetMapping("/verify")
	public String show(HttpSession session) // nhận emal và gửi code
	{
		String email = (String) session.getAttribute("email");
		System.out.println(
				"Email: -----------------------------------------------------------------------------" + email);
		Optional<UserEntity> user = user_service.findByemailContaining(email);
		System.out.print(user.get().getEmail());
		Optional<StatusAccountEntity> account_status = user_service.findByuserCode(user.get());
		if (user.isPresent()) { // && account_status.get().getStatus() == false) {
			System.out.print(user.get().getIdAccount());
			System.out.print(account_status.get().getId());
			System.out.print(user.get().getEmail());
			int code = Integer.parseInt(RandomStringUtils.randomNumeric(6));
			user_service.createCode(account_status.get(), code);// tạo code cập nhật code cũ
			try {
				imail.constructCreateCode(code, user.get());
				System.out.print("da in");

			} catch (Exception e) {
				return "redirect:forgot?no";
			}
		} else
			return "redirect:forgot?wrong";
		return "verify";
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
				System.out.print(user.get().getEmail());
				System.out.print(token);
				System.out.print(getAppUrl(request));

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

	@GetMapping("/LoginGoogle")
	public String processRequest(@RequestParam("code") String code,HttpSession session) throws ClientProtocolException, IOException {
		String accessToken = getToken(code);
		UserGoogle user = getUserInfo(accessToken);
		Optional<UserEntity> user_check = user_service.findByemailContaining(user.getEmail());
		if(user_check.isEmpty())
		{
			UserEntity user_entity = new UserEntity();
			user_entity.setPass("");
			user_entity.setEmail(user.getEmail());
			UserInfoEntity user_info= new UserInfoEntity();
			StatusAccountEntity status = new StatusAccountEntity();
			user_info.setFullName(user.getName());
			user_info.setAvata(user.getPicture());
			
			user_entity.setUserName(user.getGiven_name());
			user_info_service.save(user_info);
			user_entity.setUserInfo(user_info);
			user_service.save(user_entity);
			status.setUserCode(user_entity);
			status.setStatus(true);
			user_service.save(status);
		}
			session.setAttribute("email", user_service.findByemailContaining(user.getEmail()));
	
			session.setAttribute("userID", user_service.findByemailContaining(user.getEmail()).get().getIdAccount());
			session.setAttribute("userInfoID",
					user_service.findByemailContaining(user.getEmail()).get().getUserInfo().getUserID());
			session.setAttribute("userFullName",
					user_service.findByemailContaining(user.getEmail()).get().getUserInfo().getFullName());
			
			System.out.println("Emai-----------"+user.getEmail());
			return "home";
	}

	public static String getToken(String code) throws ClientProtocolException, IOException {
		// call api to get token
		try {
			String response = Request.Post(InfoGoogleModel.GOOGLE_LINK_GET_TOKEN)
					.bodyForm(Form.form().add("client_id", InfoGoogleModel.GOOGLE_CLIENT_ID)
							.add("client_secret", InfoGoogleModel.GOOGLE_CLIENT_SECRET)
							.add("redirect_uri", InfoGoogleModel.GOOGLE_REDIRECT_URI).add("code", code)
							.add("grant_type", InfoGoogleModel.GOOGLE_GRANT_TYPE).build())
					.execute().returnContent().asString();

			// Xử lý response, ví dụ:
			//System.out.println("Server response: " + response);

			// Đọc và xử lý access token từ response (đối với mục đích của bạn)
			JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
			String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
			return accessToken;
		} catch (Exception e) {
			// Xử lý lỗi, ví dụ:
			e.printStackTrace();
			System.out.println("Loi---------------------------------------------" + e.getMessage());
			System.out.println("Request body: " + Form.form()
			.add("link token", InfoGoogleModel.GOOGLE_LINK_GET_TOKEN)
	        .add("client_id", InfoGoogleModel.GOOGLE_CLIENT_ID)
	        .add("client_secret", InfoGoogleModel.GOOGLE_CLIENT_SECRET)
	        .add("redirect_uri", InfoGoogleModel.GOOGLE_REDIRECT_URI)
	        .add("code", code)
	        .add("grant_type", InfoGoogleModel.GOOGLE_GRANT_TYPE)
	        .build().toString());

		}
		
		return "home";
	}

	public static UserGoogle getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
		String link = InfoGoogleModel.GOOGLE_LINK_GET_USER_INFO + accessToken;
		String response = Request.Get(link).execute().returnContent().asString();
		System.out.print(response);
		UserGoogle googlePojo = new Gson().fromJson(response, UserGoogle.class);

		return googlePojo;
	}

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

	@PostMapping("/verifyCode")
	public String verify(HttpServletRequest request, ModelMap model,
			@ModelAttribute("status") StatusAccountEntity status) {
		/*
		 * try { Optional<UserEntity> user =
		 * user_service.findByemailContaining(emailInfo.getTo());
		 * imail.send(null,null,user.get()); } catch (Exception e) { return "wrong"; }
		 * return "Correct";
		 */
		Optional<StatusAccountEntity> status_check = user_service.findBycode(status.getCode());
		if (status_check.isPresent()) {
			status_check.get().setStatus(true);
			user_service.save(status_check.get());
			System.out.print(status_check.get().getStatus());
			return "redirect:/verifyCode?success";
		} else
			return "redirect:/verifyCode?fail";
	}

	@GetMapping("/verifyCode")
	public String show() {
		return "verify";
	}

	@GetMapping("/again")
	public String showAgain() {
		return "verify";
	}
}