import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSendSms {
	 public static void main(String[] args) {
		 
		 //AC4f6d11a4a5bb6f06e8f3c52e51a547a7 Account Sid
		 //Auth Token   12b6a9fe1d85e27ec161050052509c7e

		 //phne Number (650) 484-5451
		 
		 
/*		 Twilio.init(
				    System.getenv("AC4f6d11a4a5bb6f06e8f3c52e51a547a7"),
				    System.getenv("AC4f6d11a4a5bb6f06e8f3c52e51a547a7"));*/
		 
		 Twilio.init(
				    "AC4f6d11a4a5bb6f06e8f3c52e51a547a7",
				   "12b6a9fe1d85e27ec161050052509c7e");
	 
	 Message.creator(
			    new PhoneNumber("919989824978"),
			    new PhoneNumber("(16504845451"),
			    "Hello from Twilio")
			  .create();
	    }
}
