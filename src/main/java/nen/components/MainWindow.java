package nen.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nen.nen2.Nen2;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Nen2 nen2;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image nen2Image = new Image(this.getClass().getResourceAsStream("/images/Bot.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setNen2(Nen2 n) {
        nen2 = n;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = nen2.getResponse(input);
        assert !response.isEmpty() : "Response from Nen2 shouldn't be empty";
        String commandType = nen2.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNen2Dialog(response, nen2Image, commandType)
        );
        userInput.clear();
    }
}
