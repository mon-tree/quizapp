/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.quizappv2;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Level;
import com.dht.pojo.Question;
import com.dht.pojo.QuestionQueryBuilder;
import com.dht.utils.Configs;
import com.dht.utils.MyAlertSingleton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuestionsController implements Initializable {

    @FXML
    private ComboBox<Category> cbCates;
    @FXML
    private ComboBox<Level> cbLevels;
    @FXML
    private ComboBox<Category> cbSearchCates;
    @FXML
    private ComboBox<Level> cbSearchLevels;
    @FXML
    private TableView<Question> tvQuestions;
    @FXML
    private VBox vChoices;
    @FXML
    private TextArea txtContent;
    @FXML
    private TextField txtKeywords;
    @FXML
    private ToggleGroup group;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadColumns();
        this.loadTableQuestions();
        try {
            this.cbCates.setItems(FXCollections.observableList(Configs.cateService.getCates()));
            this.cbLevels.setItems(FXCollections.observableList(Configs.lvlService.getLevels()));
            this.cbSearchCates.setItems(FXCollections.observableList(Configs.cateService.getCates()));
            this.cbSearchLevels.setItems(FXCollections.observableList(Configs.lvlService.getLevels()));

        } catch (SQLException ex) {

        }

        this.txtKeywords.textProperty().addListener(e -> {
            this.loadTableQuestions();
        });
        this.cbSearchCates.getSelectionModel().selectedItemProperty().addListener(e -> {
            this.loadTableQuestions();
        });
        this.cbSearchLevels.getSelectionModel().selectedItemProperty().addListener(e -> {
            this.loadTableQuestions();
        });
    }

    private void loadColumns() {
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(100);

        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colContent.setPrefWidth(300);

        this.tvQuestions.getColumns().addAll(colId, colContent);
    }

    public void addChoice(ActionEvent e) {
        HBox h = new HBox();
        h.getStyleClass().add("Container");

        RadioButton r = new RadioButton();
        r.setToggleGroup(group);

        TextField t = new TextField();
        t.getStyleClass().add("Input");

        h.getChildren().addAll(r, t);

        this.vChoices.getChildren().add(h);
    }

    public void addQuestion(ActionEvent e) {
        Question q = new Question.Builder()
                .setCategory(this.cbCates.getSelectionModel().getSelectedItem())
                .setContent(this.txtContent.getText())
                .setLevel(this.cbLevels.getSelectionModel().getSelectedItem()).build();

        List<Choice> choices = new ArrayList<>();
        for (var hb : this.vChoices.getChildren()) {
            HBox h = (HBox) hb;

            RadioButton r = (RadioButton) h.getChildren().get(0);
            TextField t = (TextField) h.getChildren().get(1);

            choices.add(new Choice(t.getText(), r.isSelected()));
        }

        try {
            Optional<ButtonType> t = MyAlertSingleton.getInstance().showMsg("Bạn chắc chắn thêm không?", Alert.AlertType.CONFIRMATION);
            if (t.isPresent() && t.get() == ButtonType.OK) {
                Configs.uQuesService.addQuestion(q, choices);

                MyAlertSingleton.getInstance().showMsg("Thêm câu hỏi thành công!");
                this.loadTableQuestions();
            }
        } catch (SQLException ex) {
            MyAlertSingleton.getInstance().showMsg("Thêm câu hỏi thật bại, lý do: " + ex.getMessage());
        }
    }

    private void loadTableQuestions() {
        QuestionQueryBuilder query = new QuestionQueryBuilder().withCategory(this.cbSearchCates.getSelectionModel().getSelectedItem())
                .withKeywords(this.txtKeywords.getText())
                .withLevel(this.cbSearchLevels.getSelectionModel().getSelectedItem());
        Configs.quesService.setQuery(query);

        try {
            this.tvQuestions.setItems(FXCollections.observableList(Configs.quesService.getQuestions()));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
