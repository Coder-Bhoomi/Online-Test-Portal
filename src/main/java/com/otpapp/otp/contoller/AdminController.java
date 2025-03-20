package com.otpapp.otp.contoller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otpapp.otp.dto.QbDto;
import com.otpapp.otp.model.AdminInfo;
import com.otpapp.otp.model.Enquiry;
import com.otpapp.otp.model.Qb;
import com.otpapp.otp.model.Response;
import com.otpapp.otp.model.Result;
import com.otpapp.otp.model.StudentInfo;
import com.otpapp.otp.service.AdminInfoRepo;
import com.otpapp.otp.service.EnquiryRepo;
import com.otpapp.otp.service.QbRepo;
import com.otpapp.otp.service.ResponseRepo;
import com.otpapp.otp.service.ResultRepo;
import com.otpapp.otp.service.StudentInfoRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController 
{
	@Autowired
	StudentInfoRepo strepo;
	
	@Autowired
	AdminInfoRepo adminrepo;
	
	@Autowired
	EnquiryRepo erepo;
	
	@Autowired
	ResponseRepo resrepo;
	
	@Autowired
	QbRepo qbrepo;
	
	@Autowired
	ResultRepo rrepo;
	
	@GetMapping("/")
	public String showStudentHome(HttpSession session, HttpServletResponse response, Model model)
	{
		try
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				long stdcount = strepo.count();
				model.addAttribute("stdcount", stdcount);
				
				long eqcount = erepo.count();
				model.addAttribute("eqcount", eqcount);

				long rcount = resrepo.count();
				model.addAttribute("rcount", rcount);

				long qcount = qbrepo.count();
				model.addAttribute("qcount", qcount);

				long recount = rrepo.count();
				model.addAttribute("recount", recount);
				return "admin/adminhome";
			}
			else 
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/adminhome")
	public String showAdminHome(HttpSession session, HttpServletResponse response, Model model)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				long stdcount = strepo.count();
				model.addAttribute("stdcount", stdcount);

				long eqcount = erepo.count();
				model.addAttribute("eqcount", eqcount);

				long rcount = resrepo.count();
				model.addAttribute("rcount", rcount);

				long qcount = qbrepo.count();
				model.addAttribute("qcount", qcount);

				long recount = rrepo.count();
				model.addAttribute("recount", recount);
				return "admin/adminhome";
			}
			else 
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/viewstudent")
	public String showStudent(HttpSession session, HttpServletResponse response, Model model)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				List<StudentInfo> slist = strepo.findAll();
				model.addAttribute("slist", slist);
				return "admin/viewstudent";
			}
			else 
			{
				return "redirect:/adminhome";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminhome";
		}
	}
	
	@GetMapping("/viewstudent/delete")
	public String deleteStudent(@RequestParam String email, HttpSession session, RedirectAttributes redirectattribute)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				StudentInfo st = strepo.findById(email).get();
				strepo.delete(st);
				redirectattribute.addFlashAttribute("msg", email+" is deleted successfully");
				return "redirect:/admin/viewstudent";
			}
			else
			{
				return "redirect:/adminlogin";
			}
			
		} 
		catch (Exception e) 
		{
			redirectattribute.addFlashAttribute("msg", "Something went wrong"+ e.getMessage());
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/viewenquiry")
	public String showEnquiries(HttpSession session, HttpServletResponse response, Model model)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				List<Enquiry> elist = erepo.findAll();
				model.addAttribute("elist", elist);
				return "admin/viewenquiry";
			}
			else 
			{
				return "redirect:/adminhome";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminhome";
		}
	}
	
	@GetMapping("/viewenquiry/delete")
	public String deleteEnquiry(@RequestParam int id, HttpSession session, RedirectAttributes redirectattribute)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				Enquiry e = erepo.findById(id).get();
				erepo.delete(e);
				redirectattribute.addFlashAttribute("msg", "Enquiry deleted successfully");
				return "redirect:/admin/viewenquiry";
			}
			else
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex)
		{
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("/viewfeedback")
	public String showFeedback(HttpSession session, HttpServletResponse response, Model model)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				List<Response> flist = resrepo.FindResponseByResponseType("Feedback");
				model.addAttribute("flist", flist);
				return "admin/viewfeedback";
			}
			else 
			{
				return "redirect:/adminhome";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminhome";
		}
	}
	
	@GetMapping("/viewfeedback/delete")
	public String deleteFeedback(@RequestParam int resid, HttpSession session, RedirectAttributes redirectattribute)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				Response f = resrepo.findById(resid).get();
				resrepo.delete(f);
				redirectattribute.addFlashAttribute("msg", "Enquiry deleted successfully");
				return "redirect:/admin/viewfeedback";
			}
			else
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex)
		{
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("/viewcomplaint")
	public String showComplaints(HttpSession session, HttpServletResponse response, Model model)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				List<Response> clist = resrepo.FindResponseByResponseType("Complain");
				model.addAttribute("clist", clist);
				return "admin/viewcomplaint";
			}
			else 
			{
				return "redirect:/adminhome";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminhome";
		}
	}
	
	@GetMapping("/viewcomplaint/delete")
	public String deleteComplaint(@RequestParam int resid, HttpSession session, RedirectAttributes redirectattribute)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				Response c = resrepo.findById(resid).get();
				resrepo.delete(c);
				redirectattribute.addFlashAttribute("msg", "Enquiry deleted successfully");
				return "redirect:/admin/viewcomplaint";
			}
			else
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex)
		{
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session)
	{
		session.invalidate();
		return "redirect:/adminlogin";
	}
	
	@GetMapping("/changepassword")
	public String showChangePassword(HttpSession session, HttpServletResponse response, Model model)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				return "admin/changepassword";
			}
			else 
			{
				return "redirect:/admin/adminhome";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/admin/adminhome";
		}
	}
	
	@PostMapping("/changepassword")
	public String changePassword(HttpSession session, HttpServletResponse response, HttpServletRequest request, RedirectAttributes attrib)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("studentid")!=null)
			{
				AdminInfo a = adminrepo.getById(session.getAttribute("adminid").toString());
				String oldpassword = request.getParameter("oldpassword");
				String newpassword = request.getParameter("newpassword");
				String confirmpassword = request.getParameter("confirmpassword");
				if(!newpassword.equals(confirmpassword))
				{
					attrib.addFlashAttribute("msg", "New Password & Confirm Password is not match!");
					return "redirect:/admin/changepassword";
				}
				if(!oldpassword.equals(a.getPassword()))
				{
					attrib.addFlashAttribute("msg", "Old Password is not matched");
					return "redirect:/admin/changepassword";
				}
					a.setPassword(newpassword);
					adminrepo.save(a);
					attrib.addFlashAttribute("msg", "Password change !");
					return "redirect:/admin/changepassword";
			}
			else 
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/addqb")
	public String addQb(HttpSession session, HttpServletResponse response, Model model)
	{
		try 
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				QbDto dto = new QbDto();
				model.addAttribute("dto", dto);
				return "admin/addqb";
			}
			else 
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminlogin";
		}
	}
	
	@PostMapping("/addqb")
	public String createQb(HttpSession session, HttpServletResponse response, @ModelAttribute QbDto dto, RedirectAttributes attrib)
	{
		try  
		{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null)
			{
				Qb qb = new Qb();
				qb.setYear(dto.getYear());
				qb.setQuestion(dto.getQuestion());
				qb.setA(dto.getA());
				qb.setB(dto.getB());
				qb.setC(dto.getC());
				qb.setD(dto.getD());
				qb.setCorrect(dto.getCorrect());
				qbrepo.save(qb);
				attrib.addFlashAttribute("msg", "Question is added");
				return "redirect:/admin/addqb";
			}
			else 
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex) 
		{
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/viewqb")
	public String viewQb(HttpSession session, Model model, HttpServletResponse reponse)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				List<Qb> qblist = qbrepo.findAll();
				model.addAttribute("qblist", qblist);
				return "admin/viewqb";
			}
			else
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex)
		{
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("/viewqb/delete")
	public String deleteQb(@RequestParam int id, HttpSession session, RedirectAttributes redirectattribute)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				Qb qb = qbrepo.findById(id).get();
				qbrepo.delete(qb);
				redirectattribute.addFlashAttribute("msg", "Question deleted successfully");
				return "redirect:/admin/viewqb";
			}
			else
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex)
		{
			return "redirect:/adminlogin";
		}
		
	}
	
	@GetMapping("/viewresult")
	public String viewResult(HttpSession session, Model model, HttpServletResponse reponse)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				List<Result> rlist = rrepo.findAll();
				model.addAttribute("rlist", rlist);
				return "admin/viewresult";
			}
			else
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception ex)
		{
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/viewresult/delete")
	public String deleteResult(@RequestParam String emailaddress, HttpSession session, RedirectAttributes redirectattribute)
	{
		try 
		{
			if(session.getAttribute("adminid")!=null)
			{
				Result r = rrepo.findById(emailaddress).get();
				rrepo.delete(r);
				redirectattribute.addFlashAttribute("msg", "Deleted successfully");
				return "redirect:/admin/viewresult";
			}
			else
			{
				return "redirect:/adminlogin";
			}
		}
		catch(Exception e)
		{
			redirectattribute.addFlashAttribute("msg", "Something went wrong"+ e.getMessage());
			return "redirect:/adminlogin";
		}
		
	}
}
