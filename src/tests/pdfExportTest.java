package tests;

import modules.file_export.pdfExport;
import sql.SQL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
public class pdfExportTest
{
	private SQL sql = new SQL();

	@Before
	public void setUp()
	{
		
	}

	@After
	public void tearDown()
	{
		
	}

	/*
	 * Testing Conditon(s): for: (int i = 0; i < searchData.size(); i++)
	 */
	@Test
	public void test_method_pdfOut_0_branch_0()
	{
		System.out.println("Now Testing Method:pdfOut Branch:0");

		File directory = new File(System.getProperty("user.dir"), "./junitExportFilesGenerated/PDF");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		//Call Method
		pdfExport.pdfOut(System.getProperty("user.dir") + "\\junitExportFilesGenerated\\PDF\\nameTest1.pdf", "nameTest1", sql.loadCalls(1));

		File pdfExported = new File(System.getProperty("user.dir"), "./junitExportFilesGenerated/PDF/nameTest1.pdf");
		assertTrue(pdfExported.exists());
		
	}

	/*
	 * Testing Conditon(s): for: Not (int i = 0; i < searchData.size(); i++)
	 */
	@Test
	public void test_method_pdfOut_0_branch_1()
	{
		System.out.println("Now Testing Method:pdfOut Branch:1");
		
		//Call Method
		pdfExport.pdfOut(System.getProperty("user.dir") + "\\junitExportFilesGenerated\\PDF\\nameTest2.pdf", "nameTest2", sql.loadCalls(1));
		File pdfExported = new File(System.getProperty("user.dir"), "./junitExportFilesGenerated/PDF/nameTest2.pdf");
		assertTrue(pdfExported.exists());
		
	}

}
