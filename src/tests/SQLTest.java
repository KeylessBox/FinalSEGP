package tests;

import javafx.collections.ObservableList;
import modules.record_structures.CallRecord;
import modules.record_structures.CaseRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sql.SQL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by AndreiM on 2/27/2017.
 */
class SQLTest {
    SQL sql;
    CaseRecord caseExample;
    @BeforeEach
    void setUp() {
        sql = new SQL();
        ObservableList<CaseRecord> listOfCasesExample = sql.loadCases();
        caseExample = listOfCasesExample.get(1);
    }

    @AfterEach
    void tearDown() {
        sql = null;
    }

    @Test
    void loadCalls() {
        int caseId = 1;
        ObservableList<CallRecord> observableList = sql.loadCalls(caseId);
        for (CallRecord callRecord: observableList ) {
            assertEquals(String.valueOf(caseId),callRecord.getCaseID());
        }
    }

    @Test
    void addCase() {
        sql.addCase(caseExample);
        ObservableList<CaseRecord> observableList = sql.loadCases();
        CaseRecord result = null;
        for (CaseRecord caseTest: observableList) {
            if (caseExample.getName().equals(caseTest.getName())) {
                result = caseTest;
            }
        }
        assertEquals (caseExample.getName(), result.getName());
    }

}