package Controller;

import Model.Main;
import Model.PageLoader;
import Model.Request.UpdateUserRequest;
import Model.User;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class AdditionalInformationController {

    public ImageView photo_iv;
    public TextField phone_tf;
    public ComboBox gender_cb;
    public Label result_lbl;
    public File profilePicFile = new
            File("C:/Users/Shahriar/IdeaProjects/Client/src/Photos/AppPhotos/profilePics/man.png");

    public AdditionalInformationController() throws URISyntaxException {
    }

    public void initialize(){
        photo_iv.setImage(new Image("file:" + profilePicFile.getAbsolutePath()));
    }

    public void chooseImage() {
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory(new File("C:/Users/Shahriar/IdeaProjects/Client/src/Photos/AppPhotos/profilePics"));
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg"
        , "*.png"));
        profilePicFile = filechooser.showOpenDialog(PageLoader.getPrimaryStage());
        if(profilePicFile == null)
            profilePicFile = new File("C:/Users/Shahriar/IdeaProjects/Client/src/Photos/AppPhotos/profilePics/man.png");
        photo_iv.setImage(new Image(profilePicFile.toURI().toString()));
    }

    public void signup(){
        if(!phone_tf.getText().matches("^[0-9]*$")){
            result_lbl.setText("Phone number is not valid");
            return ;
        }
        String extension = profilePicFile.getName().substring(profilePicFile.getName().lastIndexOf('.') + 1);
        ByteOutputStream bos = new ByteOutputStream();
        try {
            ImageIO.write(ImageIO.read(profilePicFile), extension, bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.currentUser.setAdditionalInfo(phone_tf.getText(), (String)gender_cb.getSelectionModel().getSelectedItem(),
                bos.getBytes());
//        System.out.println("updating " + Main.currentUser);
        User newUser = new User(Main.currentUser.getUsername(), Main.currentUser.getPassword(), Main.currentUser.getGender(),
                Main.currentUser.getNumber(), Main.currentUser.getProfilePicture(), Main.currentUser.getBirthdate());
        UpdateUserRequest uur = new UpdateUserRequest(newUser);
//        System.out.println("updating " + uur.getUser());
        Main.connection.request(uur);
        new PageLoader().load("/View/SignIn.fxml");
    }

    public void goHome(ActionEvent actionEvent) {
        new PageLoader().load("/View/SignIn.fxml");
    }
}
