package Controller;

import Model.Main;
import Model.PageLoader;
import Model.Request.UpdateUserRequest;
import Model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class SettingsController {

    public TextField username_lbl;
    public PasswordField confirm_pf;
    public PasswordField password_pf;
    public ImageView profilePic_iv;
    private User user;
    public Label error_lbl;
    File profilePicFile;

    public void initialize() throws IOException {
        username_lbl.setText(user.getUsername());
        username_lbl.setDisable(true);
        profilePic_iv.setImage(SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(user.getProfilePicture())), null));
    }

    public SettingsController(User u) {
        user = u;
    }

    public void updateUserInfo(ActionEvent actionEvent) throws IOException {
        String validation = validateFields();
        if (!validation.equals("Success"))
            error_lbl.setText(validation);
        else {
            byte[] image = null;
            if (profilePicFile != null) {
                BufferedImage bImage = ImageIO.read(profilePicFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, profilePicFile.getName().substring(profilePicFile.getName().lastIndexOf(".") + 1), bos);
                image = bos.toByteArray();
            }
            Main.connection.request(new UpdateUserRequest(new User(user.getUsername(), password_pf.getText(), user.getGender(),
                    user.getNumber(), image, user.getBirthdate())));
        }
        back(actionEvent);
    }

    private String validateFields() {
        if (!(password_pf.getText().equals(confirm_pf.getText())))
            return "Passwords do not match!";
        if (!password_pf.getText().matches("^[A-Za-z0-9]{8,}$"))
            return "Password is not valid";
        return "Success";
    }

    public void choosePic(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        profilePicFile = fileChooser.showOpenDialog(PageLoader.getPrimaryStage());
        if (profilePicFile != null) {
            fileChooser.setInitialDirectory(new File("C:/Users/Shahriar/IdeaProjects/Client/src/Photos/AppPhotos/profilePics"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg"
                    , "*.png"));
            profilePic_iv.setImage(new Image(profilePicFile.toURI().toString()));
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.mainControllerInstance = new MainPageController(Main.currentUser, true);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/mainPage.fxml", Main.mainControllerInstance));
    }
}
