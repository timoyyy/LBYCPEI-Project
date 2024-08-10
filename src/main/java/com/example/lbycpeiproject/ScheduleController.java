package com.example.lbycpeiproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;

public class ScheduleController {

    @FXML
    private AnchorPane schedulePane;

    @FXML
    private TextField courseDay;

    @FXML
    private TextField courseName;

    @FXML
    private TextField courseTime;

    private List<ScheduledClass> scheduledClasses;

    @FXML
    public void initialize() {
        scheduledClasses = new ArrayList<>();
    }

    private void createSchedule() {
        for (ScheduledClass scheduledClass : scheduledClasses) {
            createClassNode(scheduledClass);
        }
    }

    private void createClassNode(ScheduledClass scheduledClass) {
        for (int day : scheduledClass.getDays()) {
            int x = getXPosition(day);
            double y = getYPosition(scheduledClass.getStartTime());

            StackPane classNode = new StackPane();
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(132);
            rectangle.setHeight(24 * scheduledClass.getDuration());
            rectangle.setFill(Color.web("#87B758"));
            Text label = new Text(scheduledClass.getName() + " " + scheduledClass.getStartTime() + " - " + scheduledClass.getEndTime());
            label.setFont(Font.font("Comic Sans", 12));
            label.setFill(Color.WHITE);
            classNode.getChildren().addAll(rectangle, label);

            classNode.setLayoutX(x);
            classNode.setLayoutY(y);
            schedulePane.getChildren().add(classNode);
        }
    }

    private int getXPosition(int day) {
        switch (day) {
            case 0: // Monday
                return 111;
            case 1: // Tuesday
                return 243;
            case 2: // Wednesday
                return 375;
            case 3: // Thursday
                return 507;
            case 4: // Friday
                return 639;
            case 5: // Saturday
                return 771;
            default:
                return 0;
        }
    }

    private double getYPosition(String startTime) {
        String[] parts = startTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        double totalMinutes = hours * 60 + minutes;
        double startMinutes = ((totalMinutes - 450) / 60.0) * 24;
        return 220 + startMinutes;
    }

    public static class ScheduledClass {
        private String name;
        private int[] days;
        private String startTime;
        private String endTime;
        private double duration;

        public ScheduledClass(String name, String days, String startTime, String endTime, double duration) {
            this.name = name;
            this.days = convertDaysToIndices(days);
            this.startTime = startTime;
            this.endTime = endTime;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int[] getDays() {
            return days;
        }

        public void setDays(int[] days) {
            this.days = days;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }

        private static int[] convertDaysToIndices(String daysString) {
            String[] daysArray = daysString.replaceAll("\\s+", "").split(",");
            List<Integer> dayIndices = new ArrayList<>();
            for (String day : daysArray) {
                int index = convertDayToIndex(day);
                if (index != -1) {
                    dayIndices.add(index);
                }
            }
            return dayIndices.stream().mapToInt(Integer::intValue).toArray();
        }

        private static int convertDayToIndex(String day) {
            switch (day) {
                case "M":
                    return 0;
                case "T":
                    return 1;
                case "W":
                    return 2;
                case "TH":
                    return 3;
                case "F":
                    return 4;
                case "S":
                    return 5;
                default:
                    return -1;
            }
        }
    }

    @FXML
    private void addClasses(ActionEvent event) {
        String name = courseName.getText();
        String dayString = courseDay.getText();
        String timeString = courseTime.getText();
        String[] timeParts = timeString.split("-");
        String startTime = timeParts[0];
        String endTime = timeParts[1];
        ScheduledClass newClass = new ScheduledClass(name, dayString, startTime, endTime, getClassDuration(startTime, endTime));
        scheduledClasses.add(newClass);
        createSchedule();
        clearTextFields();
    }

    @FXML
    void removeClasses(ActionEvent event) {
        String name = courseName.getText();
        for (int i = 0; i < schedulePane.getChildren().size(); i++) {
            Node node = schedulePane.getChildren().get(i);
            if (node instanceof StackPane) {
                StackPane classNode = (StackPane) node;
                Text label = (Text) classNode.getChildren().get(1);
                String className = label.getText().split(" ")[0];
                if (className.equals(name)) {
                    schedulePane.getChildren().remove(i);
                    i--;
                }
            }
        }

        scheduledClasses.removeIf(scheduledClass -> scheduledClass.getName().equals(name));
        clearTextFields();
    }

    @FXML
    private void editClasses(ActionEvent event) {
        String name = courseName.getText();
        String dayString = courseDay.getText();
        String timeString = courseTime.getText();
        String[] timeParts = timeString.split("-");
        String startTime = timeParts[0];
        String endTime = timeParts[1];

        for (int i = 0; i < schedulePane.getChildren().size(); i++) {
            Node node = schedulePane.getChildren().get(i);
            if (node instanceof StackPane) {
                StackPane classNode = (StackPane) node;
                Text label = (Text) classNode.getChildren().get(1);
                String className = label.getText().split(" ")[0];
                if (className.equals(name)) {
                    schedulePane.getChildren().remove(i);
                    i--;
                }
            }
        }

        for (int i = 0; i < scheduledClasses.size(); i++) {
            ScheduledClass scheduledClass = scheduledClasses.get(i);
            if (scheduledClass.getName().equals(name)) {
                scheduledClass.setName(name);
                scheduledClass.setDays(ScheduledClass.convertDaysToIndices(dayString));
                scheduledClass.setStartTime(startTime);
                scheduledClass.setEndTime(endTime);
                scheduledClass.setDuration(getClassDuration(startTime, endTime));
                createSchedule();
                break;
            }
        }
        clearTextFields();
    }

    @FXML
    protected void returnWindow(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-screen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields() {
        courseName.clear();
        courseDay.clear();
        courseTime.clear();
    }

    private double getClassDuration(String startTime, String endTime) {
        String[] startTimeParts = startTime.split(":");
        int startHours = Integer.parseInt(startTimeParts[0]);
        int startMinutes = Integer.parseInt(startTimeParts[1]);

        String[] endTimeParts = endTime.split(":");
        int endHours = Integer.parseInt(endTimeParts[0]);
        int endMinutes = Integer.parseInt(endTimeParts[1]);

        int totalStartMinutes = startHours * 60 + startMinutes;
        int totalEndMinutes = endHours * 60 + endMinutes;
        double totalDurationMinutes = (totalEndMinutes - totalStartMinutes) / 60.0;

        return totalDurationMinutes;
    }
}