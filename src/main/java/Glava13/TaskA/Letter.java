package Glava13.TaskA;


import java.sql.Date;

public class Letter {
    private int id;
    private int senderId;
    private int receiverId;
    private String subject;
    private String body;
    private Date sentDate;

    public Letter(int id, int senderId, int receiverId, String subject, String body, Date sentDate)
    {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.subject = subject;
        this.body = body;
        this.sentDate = sentDate;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public int getSenderId()
    {
        return senderId;
    }

    public int getReceiverId()
    {
        return receiverId;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getBody() {

        return body;
    }

    public Date getSentDate()
    {
        return sentDate;
    }

    @Override
    public String toString()
    {
        return "Letter {" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", sentDate=" + sentDate +
                '}';
    }
}