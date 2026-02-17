package chad;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Chad chad;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUsr.jpg"));
    private final Image chadImage = new Image(this.getClass().getResourceAsStream("/images/DaChad.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Chad instance and displays the welcome message.
     *
     * @param chad Chad instance to use for responses.
     */
    public void setChad(Chad chad) {
        this.chad = chad;
        dialogContainer.getChildren().add(DialogBox.getChadDialog(chad.getGreeting(), chadImage, "chad"));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Chad's reply,
     * and appends them to the dialog container. Clears the user input after
     * processing.
     * Closes the application if the user enters {@code bye}.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = chad.getResponse(input);
        String commandType = chad.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getChadDialog(response, chadImage, commandType));

        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}
