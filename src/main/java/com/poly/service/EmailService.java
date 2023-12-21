package com.poly.service;

import com.poly.utils.EmailDetail;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetail details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetail details);
}
