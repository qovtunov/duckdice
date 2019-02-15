package Services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Data.ConfigProperties.getProperty;


public class PhoneVerification {

    public static String phoneVerificationCode;
    public StringBuilder content;
    public String pattern1 = " Code: ";
    public String pattern2 = " If you didn't request it, contact support immediately.";
    public BufferedReader bufferedReader;
    public boolean phoneExists = true;
    public boolean newPhone = false;
    public boolean smsReceived = true;
    public boolean noSMS = true;
    public long startTime = System.currentTimeMillis();
    public long waitTime = 60000;
    public long endTime = startTime + waitTime;

    ArrayList<String> oldCodes = new ArrayList<String>();
    ArrayList<String> newCodes = new ArrayList<String>();

    public void readPage() {

        content = new StringBuilder();

        try {
            // create a url object
            String endpoint = "http://receivefreesms.com/" + "+" + getProperty("phone") + ".php";
            URL url = new URL(endpoint);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76");

            // wrap the urlconnection in a bufferedreader
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
                //System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            //check if phone has't expired
            phoneExists = false;
            //case if phone has expired
            System.out.println("Phone has expired! Please contact CQ team.");
            phoneVerificationCode = "0";
            smsReceived = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parsePage() {
        //Do  read page
        readPage();
        if (phoneExists) {
            //case phone still active
            //System.out.printf("Wait for phone verification code...");
            try {
                //check if there is already some SMS on the page to get old code
                Pattern p = Pattern.compile(Pattern.quote(pattern1) + "([0-9]{5})" + Pattern.quote(pattern2));
                Matcher m = p.matcher(content);
                m.find();
                m.group(1);
                String code = m.group(1);
                oldCodes.add(code);
                System.out.println("Old SMS:" + oldCodes);
            } catch (IllegalStateException e) {
                //check if phone is new or there are no SMSs on the page
                System.out.println("The phone is new or SMS out of date");
                newPhone = true;
            }
        }
    }

    public void getPhoneVerificationCode() {

        try {
            if (newPhone) {
                //case if phone is new or there are no SMSs on the page
                System.out.println("Waiting for code...");
                do{
                    try {
                        System.out.printf(".");
                        readPage();
                        Pattern pn = Pattern.compile(Pattern.quote(pattern1) + "([0-9]{5})" + Pattern.quote(pattern2));
                        Matcher mn = pn.matcher(content);
                        mn.find();
                        mn.group(1);
                        String newCode = mn.group(1);
                        System.out.println(newCode);
                        newCodes.add(newCode);
                        noSMS = false;
                    } catch (IllegalStateException e) { }
                } while (noSMS && (System.currentTimeMillis() < endTime));

                if (noSMS) {
                    //SMS code was not found for 30 seconds
                    System.out.println("No SMS received! Something went wrong. Please contact CQ team. ");
                    newCodes.add("0");
                    smsReceived = false;
                }

            } else {
                //case if phone was used already. Using old and new code comparison
                System.out.println("Waiting for new code...");
                do {
                    System.out.printf(".");
                    readPage();
                    Pattern pn = Pattern.compile(Pattern.quote(pattern1) + "([0-9]{5})" + Pattern.quote(pattern2));
                    Matcher mn = pn.matcher(content);
                    mn.find();
                    mn.group(1);
                    String newCode = mn.group(1);
                    newCodes.add(newCode);
                }
                //condition waiting for new code different from old one for 30 seconds
                while ((newCodes.get(newCodes.size() - 1)).contains(oldCodes.get(0)) && (System.currentTimeMillis() < endTime));

                if (newCodes.get(newCodes.size() - 1).contains(oldCodes.get(0))) {
                    //New code was not found for 30 seconds
                    System.out.println("No SMS received! Something went wrong. Please contact CQ team. ");
                    phoneVerificationCode = "0";
                    smsReceived = false;

                }
            }

            if (smsReceived) {
                //New code was received
                System.out.println("SMS code: " + newCodes);
                phoneVerificationCode = newCodes.get(newCodes.size() - 1);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
