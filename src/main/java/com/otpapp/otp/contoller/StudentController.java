package com.otpapp.otp.contoller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	@GetMapping("/dashboard")
	public String showDashboard(HttpSession session, HttpServletResponse response, Model model) {
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
		return "/student/givetest";
	}

	
	
	/*@GetMapping("/starttest")
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
						return "/student/starttest";
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
				return "/student/starttest";
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
	}*/

	@GetMapping("/student/starttest")
    public String giveTest(Model model, Principal principal) {
        String studentEmail = principal.getName();
        StudentInfo student = stdrepo.findById(studentEmail).orElse(null);
        String course = student.getCourse();

		Pageable pageable = PageRequest.of(0, 10); // Adjust the page size as needed
		List<Qb> allQuestions = qbrepo.findByCourse(course, pageable).getContent();
        Collections.shuffle(allQuestions);
        List<Qb> selectedQuestions = allQuestions.stream().limit(10).toList();

		String jsonData;
		try {
			jsonData = new ObjectMapper().writeValueAsString(selectedQuestions);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting questions to JSON", e);
		}

        model.addAttribute("json", jsonData);
        model.addAttribute("tt", "10 minutes");
        model.addAttribute("tq", 10);
        return "student/starttest"; // your Thymeleaf page
    }

	@GetMapping("/testover")
	public String testOver(HttpSession session, HttpServletResponse response, @RequestParam int s, @RequestParam int t, Model model) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session.getAttribute("studentid") != null) {
				StudentInfo st = stdrepo.getById(session.getAttribute("studentid").toString());
				Result r = new Result();
				r.setEmailaddress(st.getEmailaddress());
				r.setName(st.getName());
				r.setCourse(st.getCourse());
				r.setContactno(st.getContactno());
				r.setTotalmarks(t);
				r.setGetmarks(s);
				r.setStatus("true");
				rrepo.save(r);

				model.addAttribute("t", t);
				model.addAttribute("s", s);
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

	@GetMapping("/viewresult")
	public String showResult(HttpSession session, HttpServletResponse response, Model model) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

			String emailaddress = (String) session.getAttribute("studentid");

			if (emailaddress != null) {
				Optional<Result> resultOptional = rrepo.findById(emailaddress);

				if (resultOptional.isPresent()) {
					model.addAttribute("result", resultOptional.get());
					return "student/viewresult"; 
				} else {
					model.addAttribute("error", "Result not found for the given email.");
					return "student/viewresult"; 
				}
			} 
			else {
				return "redirect:/login"; 
			}
		} catch (Exception ex) {
			return "redirect:/login"; 
		}
	}


}
