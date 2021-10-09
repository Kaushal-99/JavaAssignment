package rest.controllers;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rest.dao.SocietyPaymentRecordsDao;
@RestController
@CrossOrigin
public class SocietyPaymentRecordsController {
    SocietyPaymentRecordsDao societyPaymentRecordsDao=new SocietyPaymentRecordsDao() ;
	
	@RequestMapping(value = "/society",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String loginUser(HttpServletRequest request,@RequestParam String type) {
            
		String result=societyPaymentRecordsDao.getRecords(type);
		return result;
	}
}
