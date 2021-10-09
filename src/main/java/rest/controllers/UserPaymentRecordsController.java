package rest.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rest.dao.UserPaymentRecordsDao;

@RestController
public class UserPaymentRecordsController {
     UserPaymentRecordsDao userPaymentRecordsDao=new UserPaymentRecordsDao() ;
	
	@RequestMapping(value = "/records",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String loginUser(HttpServletRequest request,@RequestParam String flatNumber,@RequestParam String type) {
           // System.out.println(flatNumber+" "+type);
		String result=userPaymentRecordsDao.getRecords(flatNumber,type);
		return result;
	}
}
