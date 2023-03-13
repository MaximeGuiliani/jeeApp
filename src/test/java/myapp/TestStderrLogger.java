package myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class TestStderrLogger {
    
   
	@Autowired
	ApplicationContext context;

	@Autowired
	ILogger loggerByService;

	@Resource(name = "stderrLogger")
	ILogger loggerByName;

	@Autowired
	StderrLogger loggerByType;
	

	@Test
	public void testLoggerService() {
		assertTrue(loggerByService instanceof StderrLogger);
		loggerByService.log("Salutation !");
	}

	@Test
	public void testLoggerByName() {
		assertEquals(loggerByService, loggerByName);
	}



	@Test
	public void testLoggerByType() {
		assertEquals(loggerByService, loggerByType);
	}

	@Test
	public void testHelloByContext() {
		assertEquals(loggerByName, context.getBean(ILogger.class));
		assertEquals(loggerByName, context.getBean("stderrLogger"));
	}


	@Test
	public void testLoggerByteArrayOutputStream() {
		assertTrue(loggerByService instanceof ILogger);
		
		
		ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		System.setErr(printStream);
		
		loggerByService.log("testLoggerByteArrayOutputStream");
		
		assertTrue( byteArrayOutputStream.toString().contains("testLoggerByteArrayOutputStream"));
		
		
	}
	

}
