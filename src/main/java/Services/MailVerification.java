package Services;

import Driver.MainMethods;

import javax.mail.*;
import javax.mail.search.SubjectTerm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MailVerification extends MainMethods {

        public static String emailQuerry;
        public static String recoveryUrl;
        public boolean isMailFound = false;


    public void getEmailQuerry(String mailName, String patternOne, String patternTwo, String emailQuote, int group) throws Exception {

        //Setup props and connecting to mail inbox
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", getProperty("gmailEmail"), getProperty("gmailPassword"));

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        System.out.println("Total messages:" + folder.getMessageCount());
        System.out.println("Unread messages:" + folder.getUnreadMessageCount());

        Message[] messages = null;
        Message mailFromOrderbook= null;

        //Search for mail from Orderbook
        System.out.println("Wait for inbound mail...");
        for (int i = 0; i<180; i++) {
            messages = folder.search(new SubjectTerm(mailName), folder.getMessages());
            if (messages.length == 0) {
                Thread.sleep(500);
                System.out.printf(".");
            }
        }

        //Search for unread mail from Orderbook
        //This is to avoid using the mail for which registration is already done
        for (Message mail : messages) {
            if (!mail.isSet(Flags.Flag.SEEN)) {
                mailFromOrderbook = mail;
                //System.out.println("\n" + "Message count is: " + mailFromOrderbook.getMessageNumber());
                isMailFound = true;
            }
        }

        if (!isMailFound) {
            throw new Exception("No new email from Orderbook");
        } else {
            String line;
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromOrderbook.getInputStream()));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println("\n" + "Email: " + buffer);

            String pattern1 = patternOne;
            String pattern2 = patternTwo;
            String code = emailQuote;
            Pattern p = Pattern.compile(Pattern.quote(pattern1) + code + Pattern.quote(pattern2));
            Matcher m = p.matcher(buffer);
            while (m.find()) {
                emailQuerry = m.group(group);
                System.out.println("\n" + "Email code: " + emailQuerry);
            }
        }
    }

    public void editRecoveryUrl(String env) {
        if (env.equals("prod")){
            recoveryUrl = ((MailVerification.emailQuerry.replace("=0D=0A=0D=0AIf", "")).replace("?recovery=3D", "")).replace("=", "");

        } else {
            recoveryUrl = ((MailVerification.emailQuerry.replace("=0D=0A=0D=0AIf", "")).replace("?recovery=3D", "")).replace("=", "");
        }
        System.out.println(recoveryUrl);

    }

    public void deleteAllMessages() throws MessagingException, IOException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", "bugbasters@gmail.com", "Azerty13");

        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_WRITE);

        // retrieve the messages from the folder in an array and print it
        Message[] messages = emailFolder.getMessages();
        //System.out.println("messages.length---" + messages.length);
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            //String ans = reader.readLine();
            message.setFlag(Flags.Flag.DELETED, true);
        }
        // expunges the folder to remove messages which are marked deleted
        emailFolder.expunge();
        emailFolder.close(false);
    }
}