package com.project.pro.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "pro_send_email")
public class SendEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email_to")
    private String to;

    @Column(name = "email_from")
    private String from;

    @Column(name = "reply_to")
    private String replyTo;

    @Column(name = "cc")
    private String cc;

    @Column(name = "bcc")
    private String bcc;

    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

}
