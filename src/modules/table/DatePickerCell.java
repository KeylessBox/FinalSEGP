//package Modules.Table;
//
///**
// * Created by Aleksandr on 05-Feb-17.
// */
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.Calendar;
//import java.util.Date;
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.event.Event;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.control.ContentDisplay;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TableCell;
//
//public class DatePickerCell<S, T> extends TableCell<BirthdayEvent, Date> {
//
//    private DatePicker datePicker;
//    private ObservableList<BirthdayEvent> birthdayData;
//
//    public DatePickerCell(ObservableList<BirthdayEvent> listBirthdays) {
//
//        super();
//
//        this.birthdayData = listBirthdays;
//
//        if (datePicker == null) {
//            createDatePicker();
//        }
//        setGraphic(datePicker);
//        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                datePicker.requestFocus();
//            }
//        });
//    }
//
//    @Override
//    public void updateItem(Date item, boolean empty) {
//
//        super.updateItem(item, empty);
//
//        SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
//
//        if (null == this.datePicker) {
//            System.out.println("datePicker is NULL");
//        }
//
//        if (empty) {
//            setText(null);
//            setGraphic(null);
//        } else {
//
//            if (isEditing()) {
//                setContentDisplay(ContentDisplay.TEXT_ONLY);
//
//            } else {
//                setDatepikerDate(smp.format(item));
//                setText(smp.format(item));
//                setGraphic(this.datePicker);
//                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//            }
//        }
//    }
//
//    private void setDatepikerDate(String dateAsStr) {
//
//        LocalDate ld = null;
//        int jour, mois, annee;
//
//        jour = mois = annee = 0;
//        try {
//            jour = Integer.parseInt(dateAsStr.substring(0, 2));
//            mois = Integer.parseInt(dateAsStr.substring(3, 5));
//            annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
//        } catch (NumberFormatException e) {
//            System.out.println("setDatepikerDate / unexpected error " + e);
//        }
//
//        ld = LocalDate.of(annee, mois, jour);
//        datePicker.setValue(ld);
//    }
//
//    private void createDatePicker() {
//        this.datePicker = new DatePicker();
//        datePicker.setPromptText("jj/mm/aaaa");
//        datePicker.setEditable(true);
//
//        datePicker.setOnAction(new EventHandler() {
//            public void handle(Event t) {
//                LocalDate date = datePicker.getValue();
//                int index = getIndex();
//
//                SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
//                Calendar cal = Calendar.getInstance();
//                cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
//                cal.set(Calendar.MONTH, date.getMonthValue() - 1);
//                cal.set(Calendar.YEAR, date.getYear());
//
//                setText(smp.format(cal.getTime()));
//                commitEdit(cal.getTime());
//
//                if (null != getBirthdayData()) {
//                    getBirthdayData().get(index).setDate(cal.getTime());
//                }
//            }
//        });
//
//        setAlignment(Pos.CENTER);
//    }
//
//    @Override
//    public void startEdit() {
//        super.startEdit();
//    }
//
//    @Override
//    public void cancelEdit() {
//        super.cancelEdit();
//        setContentDisplay(ContentDisplay.TEXT_ONLY);
//    }
//
//    public ObservableList<BirthdayEvent> getBirthdayData() {
//        return birthdayData;
//    }
//
//    public void setBirthdayData(ObservableList<BirthdayEvent> birthdayData) {
//        this.birthdayData = birthdayData;
//    }
//
//    public DatePicker getDatePicker() {
//        return datePicker;
//    }
//
//    public void setDatePicker(DatePicker datePicker) {
//        this.datePicker = datePicker;
//    }
//
//}
