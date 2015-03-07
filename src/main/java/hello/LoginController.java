package hello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	private MongoDAOImpl mongoDaoImpl = MongoDAOImpl.getInstance();

	@RequestMapping(value="/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) {       
        String username = request.getParameter("username");
        String password = request.getParameter("password");  
        if(username!=null && password!=null){
        	if(mongoDaoImpl.login(username, password)){
        	//if(username.equals("idis@pccoe.com") && password.equals("pccoe")){
    			System.out.println("Authentication success :"+username);
                response.addHeader("IS_VALID", "yes");
    		}
    		else{
    			System.out.println("Authentication failed:"+username);
                response.addHeader("IS_VALID", "no");
    		}
        }  
        else{
        	System.out.println("Authentication failed:"+username);
            response.addHeader("IS_VALID", "no");
        }
    }	
}
