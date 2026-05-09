package project.oop.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.oop.Gender;
import project.oop.Guest;
import project.oop.HotelDatabase;
import project.oop.util.SceneNavigator;

import java.time.LocalDate;

public class RegisterController {

    @FXML private TextField        usernameField;
    @FXML private ComboBox<String> genderCombo;
    @FXML private PasswordField    passwordField;
    @FXML private TextField        yearField;
    @FXML private TextField        monthField;
    @FXML private TextField        dayField;
    @FXML private TextField        addressField;
    @FXML private TextField        balanceField;
    @FXML private TextField        prefField;
    @FXML private Label            errorLabel;

    @FXML
    public void initialize() {
        genderCombo.getItems().addAll("MALE", "FEMALE");
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String genderStr = genderCombo.getValue();
        String year    = yearField.getText().trim();
        String month   = monthField.getText().trim();
        String day     = dayField.getText().trim();
        String address = addressField.getText().trim();
        String balStr  = balanceField.getText().trim();
        String pref    = prefField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || genderStr == null
                || year.isEmpty() || month.isEmpty() || day.isEmpty() || address.isEmpty()) {
            showError("Please fill in all required fields.");
            return;
        }

        // Check duplicate username
        if (HotelDatabase.findGuest(username) != null) {
            showError("Username already taken.");
            return;
        }

        LocalDate dob;
        try {
            dob = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        } catch (Exception e) {
            showError("Invalid date of birth.");
            return;
        }

        double balance = 0;
        if (!balStr.isEmpty()) {
            try { balance = Double.parseDouble(balStr); }
            catch (NumberFormatException e) { showError("Invalid balance."); return; }
        }

        Gender gender;
        try { gender = Gender.valueOf(genderStr); }
        catch (IllegalArgumentException e) { showError("Invalid gender."); return; }

        try {
            Guest.register(username, password, dob, balance, address, gender, pref);
            SceneNavigator.navigateTo("Login.fxml");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        SceneNavigator.navigateTo("Login.fxml");
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}
