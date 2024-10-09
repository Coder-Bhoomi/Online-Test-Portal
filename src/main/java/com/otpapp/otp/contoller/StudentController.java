package com.otpapp.otp.contoller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.otpapp.otp.dto.ResponseDto;
import com.otpapp.otp.dto.StudentInfoDto;
import com.otpapp.otp.model.Qb;
import com.otpapp.otp.model.Response;
import com.otpapp.otp.model.Result;
import com.otpapp.otp.model.StudentInfo;
import com.otpapp.otp.service.QbRepo;
import com.otpapp.otp.service.ResponseRepo;
import com.otpapp.otp.service.ResultRepo;
import com.otpapp.otp.service.StudentInfoRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentInfoRepo stdrepo;

	@Autowired
	ResponseRepo resrepo;

	@Autowired
	ResultRepo rrepo;

	@Autowired
	QbRepo qbrepo;

	@GetMapping("/shome")
	public String showStudentHome(HttpSession session, HttpServletResponse response, Model model) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session.getAttribute("studentid") != null) {
				StudentInfo sinfo = stdrepo.findById(session.getAttribute("studentid").toString()).get();
				model.addAttribute("sinfo", sinfo);
				StudentInfoDto dto = new StudentInfoDto();
				model.addAttribute("dto", dto);
				return "student/dashboard";
			} else {
				return "redirect:/studentlogin";
			}
		} catch (Exception ex) {
			return "redirect:/studentlogin";
		}
	}

	@PostMapping("/shome")
	public String uploadPic(HttpSession session, RedirectAttributes attrib, @ModelAttribute StudentInfoDto dto) {
		try {
			if (session.getAttribute("studentid") != null) {
				StudentInfo stu = stdrepo.getById((String) session.getAttribute("studentid"));
				MultipartFile filedata = dto.getProfilepic();
				String storageFileName = new Date().getTime() + "_" + filedata.getOriginalFilename();
				String uploadDir = "public/user/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try (InputStream inputstream = filedata.getInputStream()) {
					Files.copy(inputstream, Paths.get(uploadDir + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}
				stu.setProfilepic(storageFileName);
				stdrepo.save(stu);
				attrib.addAttribute("message", "Pic uploaded successfully");
				return "redirect:/student/shome";
			} else {
				return "redirect:/studentlogin";
			}
		} catch (Exception e) {
			attrib.addFlashAttribute("message", "something went wrong" + e.getMessage());
			return "redirect:/student/shome";
		}
	}

	@GetMapping("/changepassword")
	public String showChangePassword(HttpSession session, HttpServletResponse response) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session.getAttribute("studentid") != null) {
				return "student/changepassword";
			} else {
				return "redirect:/studentlogin";
			}
		} catch (Exception ex) {
			return "redirect:/studentlogin";
		}
	}

	@PostMapping("/changepassword")
	public String changePassword(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			RedirectAttributes attrib) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session.getAttribute("studentid") != null) {
				StudentInfo s = stdrepo.getById(session.getAttribute("studentid").toString());
				String oldpassword = request.getParameter("oldpassword");
				String newpassword = request.getParameter("newpassword");
				String confirmpassword = request.getParameter("confirmpassword");
				if (!newpassword.equals(confirmpassword)) {
					attrib.addFlashAttribute("msg", "NewPassword & ConfirmPassword is not match!");
					return "redirect:/student/changepassword";
				}
				if (!oldpassword.equals(s.getPassword())) {
					attrib.addFlashAttribute("msg", "Old Password is not matched");
					return "redirect:/student/changepassword";
				}
				s.setPassword(newpassword);
				stdrepo.save(s);
				attrib.addFlashAttribute("msg", "Password change !");
				return "redirect:/student/changepassword";
			} else {
				return "redirect:/studentlogin";
			}
		} catch (Exception ex) {
			return "redirect:/studentlogin";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/studentlogin";
	}

	@GetMapping("/response")
	public String ShowResponse(HttpSession session, HttpServletResponse response, Model model) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-validate");
		try {
			if (session.getAttribute("studentid") != null) {
				ResponseDto dto = new ResponseDto();
				model.addAttribute("dto", dto);
				return "student/response";
			} else {
				return "redirect:/studentlogin";
			}
		} catch (Exception e) {
			return "redirect:/studentlogin";
		}
	}

	@PostMapping("/response")
	public String SubmitResponse(HttpSession session, HttpServletResponse response,
			@ModelAttribute ResponseDto responseDto, RedirectAttributes redirect) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session.getAttribute("studentid") != null) {
				Response res = new Response();
				StudentInfo std = stdrepo.getById((String) session.getAttribute("studentid"));

				res.setName(std.getName());
				res.setContactno(std.getContactno());
				res.setEmailaddress(std.getEmailaddress());
				res.setResponsetype(responseDto.getResponsetype());
				res.setResponsesubject(responseDto.getResponsesubject());
				res.setResponsetext(responseDto.getResponsetext());
				res.setResponsedate(new Date() + "");
				resrepo.save(res);
				redirect.addFlashAttribute("message", "Response Submitted Successfully");
				return "redirect:/student/response";
			} else {
				return "redirect:/student/response";
			}

		}

		catch (Exception e) {
			redirect.addFlashAttribute("message", "Something went wrong" + e.getMessage());
			return "redirect:/student/response";

		}

	}

	@GetMapping("/givetest")
	public String showGiveTest() 
	{
		return "student/givetest";
	}
	
	@GetMapping("/starttest")
	public String showStartTest(HttpSession session, HttpServletResponse response, Model model,RedirectAttributes attrib)
	{
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("studentid")!=null)
			{
				StudentInfo sinfo = stdrepo.findById(session.getAttribute("studentid").toString()).get();
				model.addAttribute("sinfo", sinfo);
				String  status = rrepo.getStatus(sinfo.getEmailaddress());
				try {
					if(status.equals("true"))
					{
						attrib.addFlashAttribute("message", "You have already given the test.");
						return "redirect:/student/givetest";
					}
					else
					{
						String year = sinfo.getYear();
						List<Qb> qlist = qbrepo.findQbByYear(year);
						Gson gson = new Gson();
						String json=gson.toJson(qlist);
						model.addAttribute("json", json);
						model.addAttribute("tt", qlist.size()/2);
						model.addAttribute("tq", qlist.size());
						return "student/starttest";
					}
				}
		 catch (Exception e) {
			 String year = sinfo.getYear();
				List<Qb> qlist = qbrepo.findQbByYear(year);
				Gson gson = new Gson();
				String json=gson.toJson(qlist);
				model.addAttribute("json", json);
				model.addAttribute("tt", qlist.size()/2);
				model.addAttribute("tq", qlist.size());
				return "student/starttest";
				}
			}
			else
			{
				return "redirect:/studentlogin";
			}
		} 
		catch (Exception ex) {
			
			return "redirect:/studentlogin";
		}
	}

	@GetMapping("/testover")
	public String testOver(HttpSession session, HttpServletResponse response, @RequestParam int s,
			@RequestParam int t) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session.getAttribute("studentid") != null) {
				StudentInfo st = stdrepo.getById(session.getAttribute("studentid").toString());
				Result r = new Result();
				r.setEmailaddress(st.getEmailaddress());
				r.setName(st.getName());
				r.setCollegename(st.getCollegename());
				r.setCourse(st.getCourse());
				r.setBranch(st.getBranch());
				r.setYear(st.getYear());
				r.setContactno(st.getContactno());
				r.setTotalmarks(t);
				r.setGetmarks(s);
				r.setStatus("true");
				rrepo.save(r);
				return "student/testover";
			} else {
				return "redirect:/student";
			}
		} catch (Exception e) {
			return "redirect:/student";
		}
	}
	
	@GetMapping("/trainingvideo")
	public String showVideo() 
	{
		return "student/trainingvideo";
	}

}
