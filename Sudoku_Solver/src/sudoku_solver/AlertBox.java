package sudoku_solver;

/*
* This class is used to display an error message to the user in the form of a popup alert window when the user
* has incorrectly inputted values for a Sudoku puzzle. The type of error is handled by the Controller class.
*/

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;

class AlertBox {

    /** Creates an Alert box using MESSAGE and displays it to the user. */
    static void display(String message, int width, int height) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error");
        window.setResizable(false);

        Label label = new Label(message);
        Button button = new Button("OK");
        button.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, width, height);
        window.setScene(scene);
        window.showAndWait();
    }
}
