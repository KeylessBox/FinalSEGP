package modules.table;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by AndreiM on 2/5/2017.
 */

/**
 * Not used on anything now. I'll keep it just in case someone would anything that is here
 */
public class CaseTab {
    public static VBox createCaseTab(ObservableList<CasesRecords> data) {
        VBox cases = new VBox();
        int i =0;
        for (CasesRecords tabPane : data){
            cases.getChildren().add(i,new Label(tabPane.getCaseName()));
            i++;
        }
        return cases;
    }
}
