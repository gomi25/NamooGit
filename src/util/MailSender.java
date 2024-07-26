package util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

class SMTPAuthenticator extends javax.mail.Authenticator {
    // SMTP 인증 아이디
    private String userid = "koreait7namoo@naver.com";
    // SMTP 인증 비밀번호
    private String pwd = "koreait7.namoo";

    public SMTPAuthenticator() { }
    public SMTPAuthenticator(String id, String pwd){
        this.userid = id;
        this.pwd = pwd;
    }
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.userid, this.pwd); // id, pwd
    }
}

public class MailSender {
//	public static void main(String[] args) {
//		MailSender sender = new MailSender();
//		sender.sendMail("0716@ruru.be", "강하늘", "제목 테스트 메일 발송 JavaMail API", "<p>내용 ㅋ<b style='color:red;'>ㅋ</b></p>");
//	}
	
	public void sendMail(String emailTo, String nameTo, String subject, String htmlMsg) {
	    // SMTP 인증방식을 이용하는 경우 
	    // String smtpUserid = "SMTP 인증 계정";
	    // String smtpPwd = "SMTP 인증 비밀번호";

	    Properties prop = new Properties();

	    // 메일서버 주소
	    prop.put("mail.smtp.host", "smtp.naver.com");

	    // 포트(기본 25)
	    prop.put("mail.smtp.port", 465);

	    // SMTP 인증 기능 사용시
	    prop.put("mail.smtp.auth", "true");

	    // ssl 사용시(이때는 보통 port 465)
	    prop.put("mail.smtp.ssl.enable", "true");
	    // tls 사용시(ssl 을 사용할 경우에는 주석)
	    //prop.put("mail.smtp.starttls.enable", "true");

	    prop.put("mail.smtp.ssl.checkserveridentity", "false");
	    prop.put("mail.smtp.ssl.trust", "*");
	    prop.put("mail.protocol.ssl.trust", "*");

	    try {
	        Authenticator auth = new SMTPAuthenticator();
	        Session session = Session.getInstance(prop, auth);
	        /* 1) SMTP 인증방식을 이용하는 경우: 보내는 사람 메일 아이디, 비밀번호
	        session.setDebug(true);  // 디버그 용도로만 사용(성능저하)
	        //*/

	        //* 2) SMTP 릴레이 방식으로 보내는 경우
	        //Session session = Session.getInstance(prop);
	        // session.setDebug(true);  // 디버그 용도로만 사용(성능저하)
	        //*/

	        // 메일 언어셋
	        String charset = "UTF-8";

	        MimeMessage msg = new MimeMessage(session);

	        // 메일제목
	        // 파라미터로. String subject = "발신 메일 제목";
	        msg.setSubject(MimeUtility.encodeText(subject, charset, "B"));

	        // 메일 발신 날짜
	        msg.setSentDate(new Date());

	        // 보내는 사람의 메일주소(위에서 인증시 사용한 메일아이디와 동일한 메일주소 권장)
	        String senderEmail = "koreait7namoo@naver.com";
	        Address fromAddr = new InternetAddress(senderEmail, "나무", charset);    // 이메일, 이름, charset
	        msg.setFrom(fromAddr);
	       
	        // 수신자
	        Address[] arrAddresses = new Address[1];
	        arrAddresses[0] = new InternetAddress(emailTo, nameTo, charset);
//	        arrAddresses[1] = new InternetAddress("수신자2 주소","수신자2이름", charset);

	        // 하나의 동일한 메일을 수신자 수만큼 지정하여 발송(동보메일)
	        msg.setRecipients(Message.RecipientType.TO, arrAddresses);

	        MimeMultipart multipart = new MimeMultipart();
	        MimeBodyPart msgPart = new MimeBodyPart();

	        // 메일 본문 html
	        // 파라미터로. String htmlMsg = "<p>메일 본문 <span style='color:red;'>HTML</span></p>";
	        msgPart.setContent(htmlMsg, "text/html;charset=" + charset);
	        multipart.addBodyPart(msgPart);

	        // 첨부파일
	        /*
	        MimeBodyPart attachPart2 = new MimeBodyPart();
	        DataSource fds = new FileDataSource("C:\\Temp\\테스트.pptx");    // 첨부파일 경로
	        attachPart2.setDataHandler(new DataHandler(fds));
	        String filename = "테스트.pptx";    // 첨부파일 이름 지정
	        attachPart2.setFileName(MimeUtility.encodeText(filename, charset, "B"));

	        multipart.addBodyPart(attachPart2);
	        //*/
	        msg.setContent(multipart);

	        // 메일 전송
	        Transport.send(msg);
	        System.out.println("메일발송 성공");
	    } catch (MessagingException ex) {
	        System.out.println("메일전송 오류 - " + ex.toString());
	    } catch(UnsupportedEncodingException ex) {
	        System.out.println("인코딩 오류 - " + ex.toString());
	    } catch (Exception ex) {
	        System.out.println("기타 오류 - " + ex.toString());
	    }
	}
}
