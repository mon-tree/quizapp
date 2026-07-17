/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.quizappv1;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.exams.ExamStrategy;
import com.dht.services.exams.ExamTypes;
import com.dht.services.exams.FixedExam;
import com.dht.services.exams.SpecificExam;
import com.dht.utils.MyAlertSingleton;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ExamController implements Initializable {

    @FXML
    ComboBox<ExamTypes> cbExamTypes;
    @FXML
    TextField txtNum;
    @FXML
    ListView<Question> lvQuestions;

    private List<Question> questions;
    private Map<Integer, Choice> answers = new HashMap<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbExamTypes.setItems(FXCollections.observableArrayList(ExamTypes.values()));

        this.txtNum.setVisible(false);
        this.cbExamTypes.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (this.cbExamTypes.getSelectionModel().getSelectedItem() == ExamTypes.SPECIFIC) {
                this.txtNum.setVisible(true);
            } else {
                this.txtNum.setVisible(false);
            }
        });

        this.lvQuestions.setCellFactory(callback -> new ListCell<Question>() {
            @Override
            protected void updateItem(Question q, boolean empty) {
                super.updateItem(q, empty); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody

                if (q == null || empty == true) {
                    setGraphic(null);
                } else {
                    VBox v = new VBox(5);

                    v.setStyle("-fx-border-width:2; -fx-border-color: blue; -fx-padding: 10");

                    Text t = new Text(q.getContent());
                    v.getChildren().add(t);
                    
                    

                    ToggleGroup g = new ToggleGroup();
                    for (var c : q.getChoices()) {
                        RadioButton r = new RadioButton(c.getContent());
                        
                        // xử lý giao diện
                        if (answers.get(q.getId()) == c)
                            r.setSelected(true);
                        
                        r.setOnAction(e -> {
                            if (r.isSelected())
                                answers.put(q.getId(), c);
                        });
                        
                        r.setToggleGroup(g);
                        v.getChildren().add(r);
                    }

                    setGraphic(v);
                }
            }

        });
    }

    public void start(ActionEvent e) {
        answers = new HashMap<>();
        ExamStrategy s;
        switch (this.cbExamTypes.getSelectionModel().getSelectedItem()) {
            case SPECIFIC:
                s = new SpecificExam(this.txtNum.getText());
                break;
            default:
                s = new FixedExam();
        }

        try {
            this.questions = s.getQuestions();
            this.lvQuestions.setItems(FXCollections.observableList(this.questions));
        } catch (SQLException ex) {
            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void grading(ActionEvent e) {
        int count = 0;
        
        for (var c: answers.values())
            if (c.isCorrect() == true)
                count++;
        
        MyAlertSingleton.getInstance().showMsg(String.format("Bạn đã làm đúng %d/%d!", count, questions.size()), 
                Alert.AlertType.INFORMATION);
    }

}
