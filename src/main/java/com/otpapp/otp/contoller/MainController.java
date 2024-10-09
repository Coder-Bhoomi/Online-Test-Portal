package com.otpapp.otp.contoller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otpapp.otp.api.SmsSender;
import com.otpapp.otp.dto.AdminInfoDto;
import com.otpapp.otp.dto.EnquiryDto;
import com.otpapp.otp.dto.StudentInfoDto;
import com.otpapp.otp.model.AdminInfo;
import com.otpapp.otp.model.Enquiry;
import com.otpapp.otp.model.StudentInfo;
import com.otpapp.otp.service.AdminInfoRepo;
import com.otpapp.otp.service.EnquiryRepo;
import com.otpapp.otp.service.StudentInfoRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	EnquiryRepo erepo;
	
	@Autowired
	StudentInfoRepo srepo;
	
	@Autowired
	AdminInfoRepo adminrepo;

	@GetMapping("/home")
	public String showIndex()
	{
		return "index";
	}
	
	@GetMapping("/about")
	public String showabout()
	{
		return "aboutus";
	}
	
	@GetMapping("/register")
	public String showregister(Model model)
	{
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		return "Registration";
	}
	
	@PostMapping("/register")
	public String submitEnquiry(@ModelAttribute StudentInfoDto studentInfoDto, BindingResult result, RedirectAttributes redirectAttribute)
	{ 
		try 
		{
			StudentInfo stu = new StudentInfo();
			stu.setRollno(studentInfoDto.getRollno());
			stu.setEnrollmentno(studentInfoDto.getEnrollmentno());
			stu.setName(studentInfoDto.getName());
			stu.setContactno(studentInfoDto.getContactno());
			stu.setWhatsappno(studentInfoDto.getWhatsappno());
			stu.setEmailaddress(studentInfoDto.getEmailaddress());
			stu.setPassword(studentInfoDto.getPassword());
			stu.setCollegename(studentInfoDto.getCollegename());
			stu.setCourse(studentInfoDto.getCourse());
			stu.setBranch(studentInfoDto.getBranch());
			stu.setYear(studentInfoDto.getYear());
			stu.setHighschool(studentInfoDto.getHighschool());
			stu.setInterschool(studentInfoDto.getInterschool());
			stu.setAggregate(studentInfoDto.getAggregate());
			stu.setTrainingmode(studentInfoDto.getTrainingmode());
			stu.setTraininglocation(studentInfoDto.getTraininglocation());
			stu.setRegdate(new Date()+"");
			srepo.save(stu);
			redirectAttribute.addFlashAttribute("message", "Registration Successfully");
			return "redirect:/register";
		}
		catch(Exception e)
		{
			redirectAttribute.addFlashAttribute("message", "Something went wrong"+ e.getMessage());
			return "redirect:/register";
		}
		
	}
	
	@GetMapping("/contact")
	public String showcontact(Model model)
	{
		EnquiryDto  dto = new EnquiryDto();
		model.addAttribute("dto", dto);
		return "contact";
	}
	
	@PostMapping("/contact")
	public String submitEnquiry(@ModelAttribute EnquiryDto enqiryDto, BindingResult result, RedirectAttributes redirectAttribute)
	{ 
		try 
		{
			Enquiry eq = new Enquiry();
			eq.setName(enqiryDto.getName());
			eq.setGender(enqiryDto.getGender());
			eq.setContactno(enqiryDto.getContactno());
			eq.setEmailaddress(enqiryDto.getEmailaddress());
			eq.setEnquirytext(enqiryDto.getEnquirytext());
			eq.setPosteddate(new Date()+"");
			erepo.save(eq);
			SmsSender ss = new SmsSender();
			ss.sendSms(enqiryDto.getContactno());
			redirectAttribute.addFlashAttribute("message", "Form Submitted Successfully");
			return "redirect:/contact";
		}
		catch(Exception e)
		{
			redirectAttribute.addFlashAttribute("message", "Something went wrong");
			return "redirect:/contact";
		}
		
	}
	
	@GetMapping("/studentlogin")
	public String showStudentLogin(Model model)
	{
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		return "studentlogin";
	}
	
	@PostMapping("/studentlogin")
	public String validateStudent(@ModelAttribute StudentInfoDto dto, HttpSession session, RedirectAttributes attrib)
	{
		try 
		{
			StudentInfo s= srepo.getById(dto.getEmailaddress());
			if(dto.getPassword().equals(s.getPassword()))
			{
				//attrib.addFlashAttribute("message", "Valid User");
				session.setAttribute("studentid", s.getEmailaddress());
				return "redirect:/student/shome";
			}
			
			else
			{
				attrib.addFlashAttribute("message", "Invalid User");
			}
			return "redirect:/studentlogin";
		}
		catch(EntityNotFoundException ex)
		{
			attrib.addFlashAttribute("message", "Student Does not exist");
			return "redirect:/studentlogin";
		}
		  
	}
	
	@GetMapping("/adminlogin")
	public String showAdminLogin(Model model)
	{
		AdminInfoDto dto = new AdminInfoDto();
		model.addAttribute("dto", dto);
		return "adminlogin";
	}
	
	@PostMapping("/adminlogin")
	public String validateAdmin(@ModelAttribute AdminInfoDto dto, HttpSession session, RedirectAttributes attrib)
	{
		try 
		{
			AdminInfo a= adminrepo.getById(dto.getUserid());
			if(a.getPassword().equals(dto.getPassword()))
			{
				//attrib.addFlashAttribute("message", "Valid User");
				session.setAttribute("adminid", a.getUserid());
				return "redirect:/admin/";
			}
			
			else
			{
				attrib.addFlashAttribute("message", "Invalid User");
			}
			return "redirect:/adminlogin";
		}
		catch(EntityNotFoundException ex)
		{
			attrib.addFlashAttribute("message", "Admin Does not exist");
			return "redirect:/adminlogin";
		}
		  
	}
	

}
