package net.test.tools;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import java.util.*;

import javax.activation.*;

public class MailUtils {

	private String host = ""; // smtp服务器
	private String user = ""; // 用户名
	private String pwd = ""; // 密码
	private String fromUser = ""; // 发件人地址
	private String toUser = ""; // 收件人地址
	private String ccUser = ""; // 抄送人地址
	private String bccUser = ""; // 密送人地址
	private String affix = ""; // 附件地址
	private String affixName = ""; // 附件名称
	private String subject = ""; // 邮件标题
	private String mailBody = ""; // 邮件内容

	// 发件人通常必须与授权用户同一邮件地址
	public void setHost(String host, String user, String pwd) {
		this.host = host;
		this.user = user;
		this.fromUser = user;
		this.pwd = pwd;
	}

	public void setAddress(String toUser, String ccUser, String bccUser) {
		this.toUser = toUser;
		this.ccUser = ccUser;
		this.bccUser = bccUser;
	}

	public void setContent(String subject, String mailBody) {
		this.subject = subject;
		this.mailBody = mailBody;
	}

	public void setAffix(String affix, String affixName) {
		this.affix = affix;
		this.affixName = affixName;
	}

	public String send() {
		String sResult = "邮件发送成功";
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");

		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);

		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);

		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(fromUser));
			// 加载收件人地址
			Address[] tos = null;
			String[] receivers = toUser.split(",");
			if (receivers != null) {
				// 为每个邮件接收者创建一个地址
				tos = new InternetAddress[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					tos[i] = new InternetAddress(receivers[i]);
				}
				message.addRecipients(Message.RecipientType.TO, tos);
			} else {
				sResult = "邮件发送失败，收件人地址不正确。";
				return sResult;
			}
			String[] ccu = ccUser.split(",");
			if (ccu != null && !"".equals(ccUser)) {
				Address[] ccs = null;
				ccs = new InternetAddress[ccu.length];
				for (int i = 0; i < ccu.length; i++) {
					ccs[i] = new InternetAddress(ccu[i]);
				}
				// 抄送
				message.addRecipients(Message.RecipientType.CC, ccs);
			}
			String[] bccu = bccUser.split(",");
			if (bccu != null && !"".equals(bccUser)) {
				Address[] bccs = null;
				bccs = new InternetAddress[bccu.length];
				for (int i = 0; i < bccu.length; i++) {
					bccs[i] = new InternetAddress(bccu[i]);
				}
				// 密送
				message.addRecipients(Message.RecipientType.BCC, bccs);
			}

			// 加载标题
			message.setSubject(subject);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(mailBody);
			multipart.addBodyPart(contentPart);
			// 添加附件
			if (!"".equals(affix)) {
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(affix);
				// 添加附件的内容
				messageBodyPart.setDataHandler(new DataHandler(source));
				// 添加附件的标题
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				messageBodyPart.setFileName("=?GBK?B?"
						+ enc.encode(affixName.getBytes()) + "?=");
				multipart.addBodyPart(messageBodyPart);
			}
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return sResult;
		} catch (Exception e) {
			e.printStackTrace();
			return "邮件发送失败" + e.getMessage();
		}
	}

	public static void main(String[] args) {
		MailUtils cn = new MailUtils();
		StringBuffer sb = new StringBuffer();
		sb.append("请简明地描述问题，小易会即时为您解答，记得给小易服务做评价哈");
		// 设置smtp服务器以及邮箱的帐号和密码
		cn.setHost("smtp.163.com", "send@163.com", "psd");
		// 设置发件人地址、收件人地址和邮件标题
		cn.setAddress("rec@qq.com", "", "");
		// 设置邮件标题和内容
		cn.setContent("我是小易", sb.toString());
		// 设置要发送附件的位置和标题
		cn.setAffix("f:/Download/how_to_use.txt", "how_to_use.txt");

		System.out.println(cn.send());

	}
}