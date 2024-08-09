package com.example.lbycpeiproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class ScheduleController {

    @FXML
    private StackPane schedulePane;

    private List<ScheduledClass> scheduledClasses;

    @FXML
    public void initialize() {
        // Assuming the scheduled classes are loaded from another FXML file
        List<ScheduledClass> classes = new ArrayList<>();
        scheduledClasses = classes;
        createSchedule();
    }

    private void createSchedule() {
        for (ScheduledClass scheduledClass : scheduledClasses) {
            createClassNode(scheduledClass);
        }
    }

    private void createClassNode(ScheduledClass scheduledClass) {
        StackPane classNode = new StackPane();
        Rectangle rectangle = new Rectangle(132, 24 * scheduledClass.getDuration());
        rectangle.setFill(Color.web("#87B758"));
        Text label = new Text(scheduledClass.getName() + " " + scheduledClass.getStartTime() + " - " + scheduledClass.getEndTime());
        label.setFont(Font.font("Comic Sans", 12));
        classNode.getChildren().addAll(rectangle, label);

        int x = getXPosition(scheduledClass.getDay());
        int y = getYPosition(scheduledClass.getStartTime());
        classNode.setLayoutX(x);
        classNode.setLayoutY(y);

        schedulePane.getChildren().add(classNode);
    }

    private int getXPosition(int day) {
        // Use the provided table to calculate the x position based on the day
        switch (day) {
            case 0: // Monday
                return 180;
            case 1: // Tuesday
                return 312;
            case 2: // Wednesday
                return 444;
            case 3: // Thursday
                return 576;
            case 4: // Friday
                return 708;
            case 5: // Saturday
                return 840;
            default:
                return 0;
        }
    }

    private int getYPosition(String startTime) {
        String[] parts = startTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        int totalMinutes = hours * 60 + minutes;
        return 220 + ((totalMinutes - 450) / 30) * 24;
    }

    private static class ScheduledClass {
        private final String name;
        private final int day;
        private final String startTime;
        private final String endTime;
        private final double duration;

        public ScheduledClass(String name, String day, String startTime, String endTime, double duration) {
            this.name = name;
            this.day = convertDayToIndex(day);
            this.startTime = startTime;
            this.endTime = endTime;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public int getDay() {
            return day;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public double getDuration() {
            return duration;
        }

        private int convertDayToIndex(String day) {
            switch (day) {
                case "Monday":
                    return 0;
                case "Tuesday":
                    return 1;
                case "Wednesday":
                    return 2;
                case "Thursday":
                    return 3;
                case "Friday":
                    return 4;
                case "Saturday":
                    return 5;
                default:
                    return -1;
            }
        }
    }

    @FXML
    void returnWindow(ActionEvent event) {
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
}