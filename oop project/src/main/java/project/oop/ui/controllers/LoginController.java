package project.oop.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.oop.*;
import project.oop.util.SceneNavigator;
import project.oop.util.Session;
import project.oop.util.SessionStaff;

public class LoginController {

    @FXML private ToggleButton  guestTab;
    @FXML private ToggleButton  staffTab;
    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label         errorLabel;

    private boolean isGuestMode = true;

    @FXML
    public void initialize() {
        guestTab.setOnAction(e -> setMode(true));
        staffTab.setOnAction(e -> setMode(false));
    }

    private void setMode(boolean guest) {
        isGuestMode = guest;
        guestTab.setSelected(guest);
        staffTab.setSelected(!guest);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        if (isGuestMode) {
            try {
                Guest guest = Guest.login(username, password);
                Session.setCurrentGuest(guest);
                SceneNavigator.navigateTo("GuestDashboard.fxml");
            } catch (IllegalArgumentException e) {
                showError("Incorrect username or password.");
            }
        } else {
            for (Staff member : HotelDatabase.staffList) {
                if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
                    SessionStaff.setCurrentStaff(member);
                    if (member.getRole() == Role.ADMIN) {
                        SceneNavigator.navigateTo("AdminDashboard.fxml");
                    } else {
                        SceneNavigator.navigateTo("StaffDashboard.fxml");
                    }
                    return;
                }
            }
            showError("Invalid staff credentials.");
        }
    }

    @FXML
    private void goToRegister() {
        SceneNavigator.navigateTo("Register.fxml");
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}
