package Websocket_adaptivecluster.Websocket_adaptivecluster;

import java.util.logging.Level;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CaptureWSMessages {

	private static WebDriver driver;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException{
		
    	System.setProperty("webdriver.chrome.driver","/Applications/Eclipse/xyz/chromedriver");
        
        // To enable performance log for collecting network events
        LoggingPreferences loggingprefs = new LoggingPreferences();
        loggingprefs.enable(LogType.PERFORMANCE, Level.ALL);
        DesiredCapabilities cap = new DesiredCapabilities().chrome();
        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

        //To launch Google Chrome 
       driver = new ChromeDriver(cap);
       driver.navigate().to("https://web-demo.adaptivecluster.com/");
       Thread.sleep(5000);
       LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);
        
       
        //To close Google Chrome
        driver.close();
        driver.quit();
        logEntries.forEach(entry->{
            JSONObject messageJSON = new JSONObject(entry.getMessage());
            String method = messageJSON.getJSONObject("message").getString("method");
            if(method.equalsIgnoreCase("Network.webSocketFrameSent")){
                System.out.println("Message Sent: " + messageJSON.getJSONObject("message").getJSONObject("params").getJSONObject("response").getString("payloadData"));
            }else if(method.equalsIgnoreCase("Network.webSocketFrameReceived")){
                System.out.println("Message Received: " + messageJSON.getJSONObject("message").getJSONObject("params").getJSONObject("response").getString("payloadData"));
            }
        });
    }
}
