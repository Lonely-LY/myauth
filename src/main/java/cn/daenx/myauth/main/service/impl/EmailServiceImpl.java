package cn.daenx.myauth.main.service.impl;

import cn.daenx.myauth.main.service.IEmailService;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 发送邮件
 * Created by Panyoujie on 2019-06-19 04:07
 */
@Service
public class EmailServiceImpl implements IEmailService {
    @Value("${spring.mail.nickname}")
    private String nickname;
    @Value("${spring.mail.username}")
    private String formEmail;  // 发件人
    @Resource
    private JavaMailSender mailSender;

    @Override
    public void sendTextEmail(String title, String content, String[] toEmails) throws UnsupportedEncodingException, AddressException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(new InternetAddress(MimeUtility.encodeText(nickname)+"<"+formEmail+">").toString());
        message.setTo(toEmails);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendFullTextEmail(String title, String html, String[] toEmails) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(new InternetAddress(MimeUtility.encodeText(nickname)+"<"+formEmail+">").toString());
        helper.setTo(toEmails);
        helper.setSubject(title);
        // 发送邮件
        helper.setText(html, true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendHtmlEmail(String title, String htmlPath, Map<String, Object> map, String[] toEmails) throws MessagingException, IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("templates/");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate(htmlPath);  // 加载html模板
        t.binding(map);  // 填充数据
        String html = t.render();  // 获得渲染后的html
        sendFullTextEmail(title, html, toEmails);  // 发送邮件
    }

}