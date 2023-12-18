package com.example.projectappchat.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Message_Send_Id", nullable = false)
    private User messageSendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Message_Receiver_Id", nullable = false)
    private User messageReceiverId;

    private String messageBody;

    @Lob
    @Column(name = "Message_Body_File", length = Integer.MAX_VALUE)
    private byte[] messageBodyFile;

    @Column(name = "Message_Date_Creation", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDateCreation;

    @Column(name = "Type_Message")
    private Byte typeMessage;

    public Message() {
    }


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public User getMessageSendId() {
        return messageSendId;
    }

    public void setMessageSendId(User messageSendId) {
        this.messageSendId = messageSendId;
    }

    public User getMessageReceiverId() {
        return messageReceiverId;
    }

    public void setMessageReceiverId(User messageReceiverId) {
        this.messageReceiverId = messageReceiverId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getMessageDateCreation() {
        return messageDateCreation;
    }

    public void setMessageDateCreation(Date messageDateCreation) {
        this.messageDateCreation = messageDateCreation;
    }

    public Byte getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(Byte typeMessage) {
        this.typeMessage = typeMessage;
    }

    public byte[] getMessageBodyFile() {
        return messageBodyFile;
    }

    public void setMessageBodyFile(byte[] messageBodyFile) {
        this.messageBodyFile = messageBodyFile;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageSendId=" + messageSendId +
                ", messageReceiverId=" + messageReceiverId +
                ", messageBody='" + messageBody + '\'' +
                ", messageBodyFile=" + Arrays.toString(messageBodyFile) +
                ", messageDateCreation=" + messageDateCreation +
                ", typeMessage=" + typeMessage +
                '}';
    }
}
