package myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class TestLogger {
    
   
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

	// deuxième implémentation de ILogger
	
	@Autowired
    @Qualifier("fileLoggerWithConstructor") // pour choisir l'implantation
    ILogger fileLoggerWithConstructor;
	
	@Value("${logfile}")
	String logfile =  "";
    @Test
    public void testFileLoggerWithConstructor() {

		
        fileLoggerWithConstructor.log("This message should be in a file");
		File tmpfile = new File(logfile);

		assertTrue(tmpfile.exists());
		

	}

	@Autowired
	@Qualifier("beanFileLogger")
	BeanFileLogger beanFileLogger;

	@Test
	public void testBeanFileLoggerDefaultValue(){
		beanFileLogger.log(" : " + beanFileLogger.getFileName());
		assertTrue(beanFileLogger.getFileName() != null);

	}

}
