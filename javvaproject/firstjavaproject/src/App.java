import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application implements EventHandler<ActionEvent> {
    Label label;
    RadioButton[] radioButtons = new RadioButton[4];
    Button btnNext, btnPrev, btnResult;
    ToggleGroup group;
    int count = 0, current = 0;
    String[] selectedOptions;

    private String[][] questions = {
        {"What is the sum of 130+125+191?", "335", "456", "446", "426", "446"},
        {"If we subtract 712 from 1500, how much do we get?", "788", "778", "768", "758", "788"},
        {"50 times of 8 is equal to?", "80", "400", "800", "4000", "4000"},
        {"110 divided by 10 is:", "11", "10", "5", "None of these", "11"},
        {"20+(90÷2) is equal to?", "50", "55", "65", "60", "65"},
        {"The product of 82 and 5 is:", "400", "410", "420", "None of these", "410"},
        {"Find the missing terms in multiples of 3: 3, 6, 9, ___ 15?", "10", "11", "12", "13", "12"},
        {"Solve 24÷8+2.", "5", "6", "8", "18", "5"}
    };

    @Override
    public void start(Stage primaryStage) {
        label = new Label();
        selectedOptions = new String[questions.length];

        radioButtons[0] = new RadioButton();
        radioButtons[1] = new RadioButton();
        radioButtons[2] = new RadioButton();
        radioButtons[3] = new RadioButton();

        group = new ToggleGroup();
        for (RadioButton radioButton : radioButtons) {
            radioButton.setToggleGroup(group);
        }

        btnPrev = new Button("Previous");
        btnNext = new Button("Next");
        btnResult = new Button("Submit");

        btnResult.setVisible(false);
        btnResult.setOnAction(this);
        btnNext.setOnAction(this);
        btnPrev.setOnAction(this);

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(label, radioButtons[0], radioButtons[1], radioButtons[2], radioButtons[3], btnPrev, btnNext, btnResult);

        Scene scene = new Scene(root, 600, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple Quiz App");
        primaryStage.show();

        setData();
    }

    void setData() {
        label.setText("Question: " + (current + 1) + ". " + questions[current][0]);
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i].setText(questions[current][i + 1]);
            radioButtons[i].setSelected(selectedOptions[current] != null && radioButtons[i].getText().equals(selectedOptions[current]));
        }
        if (current == 0) {
            btnPrev.setDisable(true);
        } else {
            btnPrev.setDisable(false);
        }
        if (current == questions.length - 1) {
            btnNext.setDisable(true);
            btnResult.setVisible(true);
        } else {
            btnNext.setDisable(false);
            btnResult.setVisible(false);
        }
    }

    boolean checkAns() {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedOption = selectedRadioButton.getText();
            String correctOption = questions[current][5];
            selectedOptions[current] = selectedOption;
            return selectedOption.equals(correctOption);
        }
        return false;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == btnNext) {
            if (checkAns()) {
                count++;
            }
            current++;
            setData();
        } else if (event.getSource() == btnPrev) {
            if (checkAns()) {
                count--;
            }
            current--;
            setData();
        } else if (event.getSource() == btnResult) {
            if (checkAns()) {
                count++;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Correct Answers: " + count);
            alert.showAndWait();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
