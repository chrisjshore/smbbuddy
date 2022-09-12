package smbbuddy.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.cshore.smbbuddy.Destination;

class SMBBuddyTests {
	String path = "localhost\\cshore:password1@example.cshore.io\\Archive";
	Destination test;
	
	@BeforeEach
	void initialize() {
		test = new Destination(path);
	}
	
	@Test
	void testDomain() {
		assertEquals("localhost",test.getDomain());
	}

	@Test
	void testUsrname() {
		assertEquals("cshore",test.getUsername());
	}
	
	@Test
	void testPassword() {
		assertEquals("password1",test.getPassword());
	}
	
	@Test
	void testHostname() {
		assertEquals("example.cshore.io",test.getHostname());
	}
	
	@Test
	void testShare() {
		assertEquals("Archive",test.getShare());
	}
	
	@Test
	void testNullPath() {
		assertNull(test.getPath());
	}
	
	@Test
	void testPath() {
		test.setPath("Test");
		assertEquals("Test",test.getPath());
	}
}
