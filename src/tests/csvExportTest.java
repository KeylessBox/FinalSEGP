package tests;

import modules.file_export.csvExport;
import sql.SQL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class csvExportTest
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
	 * Testing Conditon(s): for: (i = 0; i < searchData.size(); i++), if: (i < searchData.size() - 1)
	 */
	@Test
	public void test_method_csvOut_0_branch_0()
	{
		System.out.println("Now Testing Method:csvOut Branch:0");

        File directory = new File(System.getProperty("user.dir"), "./junitExportFilesGenerated/CSV");
        if (!directory.exists()) {
            directory.mkdirs();
        }
		
		//Call Method
        csvExport.csvOut(System.getProperty("user.dir") + "\\junitExportFilesGenerated\\CSV\\csv1.csv", sql.loadCalls(1));
        File csvExported = new File(System.getProperty("user.dir"), "./junitExportFilesGenerated/CSV/csv1.csv");
        assertTrue(csvExported.exists());
		
	}

	/*
	 * Testing Conditon(s): for: (i = 0; i < searchData.size(); i++), else: Not (i < searchData.size() - 1)
	 */
	@Test
	public void test_method_csvOut_0_branch_1()
	{
		System.out.println("Now Testing Method:csvOut Branch:1");

        File directory = new File(System.getProperty("user.dir"), "./junitExportFilesGenerated/CSV");
        if (!directory.exists()) {
            directory.mkdirs();
        }

		//Call Method
        csvExport.csvOut(System.getProperty("user.dir") + "\\junitExportFilesGenerated\\CSV\\csv2.csv", sql.loadCalls(1));
		File csvExported = new File(System.getProperty("user.dir"), "./junitExportFilesGenerated/CSV/csv2.csv");
		assertTrue(csvExported.exists());
	}


}
