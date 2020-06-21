package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Message implements java.io.Serializable {
    private static final long serialVersionUID = 11L;
    private String sender, receiver;
    private String text;
    private byte[] file;
    private LocalDate date;
    private LocalTime time;
    private boolean read;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Message(String sender, String receiver, String text, byte[] file, LocalDate date, LocalTime time) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.file = file;
        this.date = date;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Message getSameMessage() {
        return new Message(sender, receiver, text, file, date, time);
    }
}
