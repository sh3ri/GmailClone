package Controller;

import Model.Main;
import Model.Message;
import Model.PageLoader;
import Model.Request.ProfilePictureRequest;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.io.*;

public class SingleMessageController {

    public ImageView profile_iv;
    public Label username_lbl;
    public Label dateTime_lbl;
    public Label text_lbl;
    public AnchorPane root;
    private Message message;

    public SingleMessageController(Message item) throws IOException {
        message = item;
        new PageLoader().load("/View/singleMessage.fxml", this);
    }
    public Parent init() throws IOException {
        Main.connection.request(new ProfilePictureRequest(message.getSender()));
        byte[] senderImage = (byte[])Main.connection.getResponse();
        if(senderImage != null)
            profile_iv.setImage(SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(senderImage)), null));
        username_lbl.setText(message.getSender());
        String time = message.getTime().toString();
        if(time.contains("."))
            time = time.substring(0, time.indexOf("."));
        dateTime_lbl.setText("at " + time + " " + message.getDate().toString());
        text_lbl.setText(message.getText());
        return root;
    }

    public void openAttachment(ActionEvent actionEvent) throws IOException {
        System.out.println("opening attachment");
        try (FileOutputStream fos = new FileOutputStream("C:/Users/Shahriar/IdeaProjects/Client/src/Files/curFile")) {
            fos.write(message.getFile());
        }
        Main.mainInstance.getHostServices().showDocument(new File("C:/Users/Shahriar/IdeaProjects/Client/src/Files/curFile").toURI().toString());
    }
}
