package Controller;

import Model.*;
import Model.Request.GetUserInformationRequest;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class MainPageController {
    private boolean showingInbox;
    private User currentUser;
    @FXML
    public ImageView profile_iv;
    @FXML
    public Label username_lbl;
    @FXML
    public ListView<Conversation> convo_lv;
    @FXML
    public ImageView inOrOut_iv;
    private final File INBOX_PIC = new File("C:/Users/Shahriar/IdeaProjects/Client/src/Photos/AppPhotos/inbox.png");
    private final File OUTBOX_PIC = new File("C:/Users/Shahriar/IdeaProjects/Client/src/Photos/AppPhotos/outbox.png");

    public void initialize() throws IOException {
        System.out.println("asking for info of " + currentUser.getUsername());
        Main.connection.request(new GetUserInformationRequest(currentUser));
        currentUser = (User) Main.connection.getResponse();
        Main.currentUser = currentUser;
        System.out.println("the info is " + Main.currentUser.getUsername());
        username_lbl.setText(currentUser.getUsername());
        profile_iv.setImage(SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(currentUser.getProfilePicture())), null));
        if (showingInbox) {
            inOrOut_iv.setImage(new Image(INBOX_PIC.toURI().toString()));
            if (currentUser.getInbox() != null && currentUser.getInbox().size() > 0) {
                convo_lv.setItems(FXCollections.observableArrayList(currentUser.getInbox()));
                convo_lv.setCellFactory(convo_lv -> new ConversationCell());
            }
        } else {
            inOrOut_iv.setImage(new Image(OUTBOX_PIC.toURI().toString()));
            if (currentUser.getOutbox() != null && currentUser.getOutbox().size() > 0) {
                convo_lv.setItems(FXCollections.observableArrayList(currentUser.getOutbox()));
                convo_lv.setCellFactory(convo_lv -> new ConversationCell());
            }
        }
    }

    public MainPageController(User currentUser, boolean showingInbox) {
        this.showingInbox = showingInbox;
        this.currentUser = currentUser;
    }


    public void refresh(ActionEvent actionEvent) throws IOException {
        Main.mainControllerInstance = new MainPageController(Main.currentUser, showingInbox);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/mainPage.fxml", Main.mainControllerInstance));
    }

    public void toggleInboxOutbox() throws IOException {
        showingInbox = !showingInbox;
        System.out.println("showingInbox = " + showingInbox);
        if (showingInbox)
            inOrOut_iv.setImage(new Image(INBOX_PIC.toURI().toString()));
        else
            inOrOut_iv.setImage(new Image(OUTBOX_PIC.toURI().toString()));
        Main.mainControllerInstance = new MainPageController(Main.currentUser, showingInbox);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/mainPage.fxml", Main.mainControllerInstance));
    }

    public void goToSettings(ActionEvent actionEvent) throws IOException {
        SettingsController controller = new SettingsController(Main.currentUser);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/Settings.fxml", controller));
    }

    public void compose() {
        new PageLoader().load("/View/compose.fxml");
    }
}


