/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import com.github.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mapvsnp
 */
public class Hotel extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXRouter.bind(this, stage, "MAHOTEL");
        FXRouter.setAnimationType("fade");
        setRoutes();
        FXRouter.goTo("login");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private static void setRoutes() {
        FXRouter.when("login", "ui/login/login.fxml", 600.0, 500.0);
        FXRouter.when("register", "ui/register/register.fxml", 600.0, 650.0);
        FXRouter.when("addroom", "ui/rooms/addroom/addroom.fxml", 600.0, 500.0);
        FXRouter.when("roomsmanage", "ui/rooms/roomsmanage/roomsmanage.fxml", 811.0, 600.0);
    }
    
}
