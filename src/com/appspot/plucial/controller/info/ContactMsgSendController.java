package com.appspot.plucial.controller.info;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slim3.controller.Navigation;

import com.appspot.plucial.model.UserModel;

public class ContactMsgSendController extends BaseController {

    private static String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";

    @Override
    protected Navigation execute(UserModel loginUserModel, boolean isLogin,
            boolean isSmartPhone) throws Exception {
        String name = asString("name");
        String email = asString("email");
        String subject = asString("subject");
        String message = asString("message");

        // 必須チェック
        if(name != null && !name.trim().isEmpty()
                && email != null && !email.trim().isEmpty()
                && subject != null && !subject.trim().isEmpty()
                && message != null && !message.trim().isEmpty()
                && email.matches(mailFormat)) {
            // 正常の場合

            try{
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);
                MimeMessage msg = new MimeMessage(session);

                //発信元情報：メールアドレス、名前
                msg.setFrom(new InternetAddress("info@plucial.com", "PluCial", "ISO-2022-JP"));

                //送信先情報
                msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("info@plucial.com", "Takahara"));

                msg.setSubject("【PluCial問い合わせ】:" + subject, "ISO-2022-JP");
                msg.setText(message + "\n\n" + name + "\n" + email);

                Transport.send(msg);

            }catch(Exception e) {
                e.printStackTrace();
            }

            return redirect("/info/contactEnd");

        }else {
            // エラーの場合
            requestScope("name", name);
            requestScope("email", email);
            requestScope("subject", subject);
            requestScope("message", message);

            requestScope("error", "入力内容は正しくありません。");

            return forward("/responsive/info/contact.jsp");
        }
    }

    @Override
    protected String setPageTitle() {
        return "お問い合わせ";
    }

    @Override
    protected String setPageDescription() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
}
