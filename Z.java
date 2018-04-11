package com;

import static com.AES4all.demo1decrypt;
import static com.AES4all.demo1encrypt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import placeme.octopusites.com.placeme.modal.AdminContactDetailsModal;

public class Z {

    static boolean isInit = false;
    static Connection con = null;
    static Statement st = null;

    public static final String COMPRESSION_PATH = "/home/PlacemeWork/uploadProfile/";
    public static final String SERVER_FILES_PATH = "/home/PlacemeWork/";
    public static final String GEOLITE_DB = "/home/PlacemeWork/GeoLite2-City.mmdb";

    public static final String BLANK_ENCRYPTED_STRING = "61y2H/6jeZPxiTwjsMAPpg==";
    public static final String DATABASE_NAME = "securedprofile";

    static void init() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/securedprofile?autoReconnect=true", "root", "root123");
            st = con.createStatement();
            isInit = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDigest1() {
        String digest1 = null;
        if (!isInit) {
            init();
        }
        try {
            ResultSet rs = st.executeQuery("select digest1 from digest");
            if (rs.next()) {
                digest1 = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return digest1;
    }

    public static String getWhereWithDigest(String username) {
        return "AES_ENCRYPT('" + username + "', '" + Z.getSqlDigest() + "')";
    }

    public static String getDataWithDigest(String coulumnname) {
        return "AES_DECRYPT(" + coulumnname + ", '" + Z.getSqlDigest() + "')";
    }

    public static String setDataWithDigest(String data) {
        return "AES_ENCRYPT('" + data + "', '" + Z.getSqlDigest() + "')";
    }

    public static String getDigest2() {
        String digest2 = null;
        if (!isInit) {
            init();
        }
        try {
            ResultSet rs = st.executeQuery("select digest2 from digest");
            if (rs.next()) {
                digest2 = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return digest2;
    }

    public static String getSqlDigest() {
        String sqldigest = null;
        if (!isInit) {
            init();
        }
        try {
            ResultSet rs = st.executeQuery("select digest3 from digest");
            if (rs.next()) {
                sqldigest = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(SimpleBase64Encoder.decode(sqldigest));
    }

    public static String getSalt() {
        String salt = null;
        if (!isInit) {
            init();
        }
        try {
            ResultSet rs = st.executeQuery("select * from salt");
            if (rs.next()) {
                salt = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return salt;
    }

    public static String generateOTP(String username, int size) throws Exception {
        if (!isInit) {
            init();
        }
        StringBuilder generatedToken = new StringBuilder();
        SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
        for (int i = 0; i < size; i++) {
            generatedToken.append(number.nextInt(9));
        }

        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj));
        Calendar calobj = Calendar.getInstance();
        ResultSet rs = st.executeQuery("select * from otprequester where username=" + getWhereWithDigest(username));
        if (rs.next()) {
            st.executeUpdate("update otprequester set OTP='" + generatedToken.toString() + "',time='" + df.format(calobj.getTime()) + "' where username=" + getWhereWithDigest(username));
        } else {
            st.executeUpdate("insert into otprequester values(" + setDataWithDigest(username) + ",'" + generatedToken.toString() + "','" + df.format(calobj.getTime()) + "')");
        }
        return generatedToken.toString();
    }

    public static boolean verifyOTP(String username, String otp) throws Exception {
        if (!isInit) {
            init();
        }
        boolean isValid = false;
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Calendar calobj1 = Calendar.getInstance();

        ResultSet rs = st.executeQuery("select * from otprequester where username=" + getWhereWithDigest(username));
        if (rs.next()) {
            String stop = df.format(calobj1.getTime());
            String start = rs.getString(3);

            Date d1 = null;
            Date d2 = null;

            d1 = df.parse(start);
            d2 = df.parse(stop);

            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            if (diffMinutes <= 30) {
                if (otp.equals(rs.getString(2))) {
                    st.executeUpdate("update user set isactivated='yes' where usernamed=" + getWhereWithDigest(username));
                    st.executeUpdate("delete from otprequester where username=" + getWhereWithDigest(username));
                    isValid = true;
                }

            }

        }
        return isValid;
    }

    public static Connection getNewSecuredProfileConnection() throws Exception {
        if (!isInit) {
            init();
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/securedprofile?autoReconnect=true", "root", "root123");
    }

    public static String[] getParameters(HttpServletRequest request, int size) {
        Enumeration paramNames = request.getParameterNames();
        String params[] = new String[size];
        int i = 0;
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
            i++;

        }
        return params;
    }

    public static void sendMailold(String recipient, String subject, String messageToSend) {
        ExecutorService emailExecutor = Executors.newCachedThreadPool();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    String host = "smtp.gmail.com";
                    String Password = "+Octopus12+";
                    String from = "contact@placeme.co.in";
                    String toAddress = recipient;

                    Properties props = System.getProperties();
                    props.put("mail.smtp.host", host);
                    props.put("mail.smtps.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    Session session = Session.getInstance(props, null);

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, toAddress);
                    message.setSubject(subject);
                    message.setContent(messageToSend, "text/html; charset=utf-8");
                    Transport tr = session.getTransport("smtps");
                    tr.connect(host, from, Password);
                    tr.sendMessage(message, message.getAllRecipients());
                    System.out.println("Mail Sent Successfully to " + recipient + " with subject " + subject);
                    tr.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sendMail(String to, String subject, String messageToSend) {

        ExecutorService emailExecutor = Executors.newCachedThreadPool();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String from = "contact@placeme.co.in";

                String host = "localhost";
//        String host = "placeme.co.in";

                Properties properties = System.getProperties();
                // Setup mail server
                properties.setProperty("mail.smtp.host", host);
                // Get the default Session object.
                Session session = Session.getDefaultInstance(properties);

                try {
                    // Create a default MimeMessage object.
                    MimeMessage message = new MimeMessage(session);
                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(from));
                    // Set To: header field of the header.
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    // Set Subject: header field
                    message.setSubject(subject);
                    // Now set the actual message
                    message.setContent(messageToSend, "text/html; charset=utf-8");
                    // Send message
                    Transport.send(message);
                    System.out.println("Mail Sent Successfully to " + to + " with subject " + subject);
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                    System.out.println("send mail exp :" + to + "  " + mex.getMessage());
                }
            }
        });
    }

    public static String Encrypt(String string) throws Exception {
        byte[] demoKeyBytes = SimpleBase64Encoder.decode(getDigest1());
        byte[] demoIVBytes = SimpleBase64Encoder.decode(getDigest2());
        String sPadding = "ISO10126Padding";

        byte[] objBytes = string.getBytes("UTF-8");
        byte[] objEncryptedBytes = demo1encrypt(demoKeyBytes, demoIVBytes, sPadding, objBytes);

        return new String(SimpleBase64Encoder.encode(objEncryptedBytes));
    }

    public static String Decrypt(String string) throws Exception {
        byte[] demoKeyBytes = SimpleBase64Encoder.decode(getDigest1());
        byte[] demoIVBytes = SimpleBase64Encoder.decode(getDigest2());
        String sPadding = "ISO10126Padding";

        byte[] EncryptedBytes = SimpleBase64Encoder.decode(string);
        byte[] DecryptedBytes = demo1decrypt(demoKeyBytes, demoIVBytes, sPadding, EncryptedBytes);
        return new String(DecryptedBytes);
    }

    public static String OtoString(Serializable o) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();

        String objectString = new String(SimpleBase64Encoder.encode(baos.toByteArray()));

        byte[] demoKeyBytes = SimpleBase64Encoder.decode(getDigest1());
        byte[] demoIVBytes = SimpleBase64Encoder.decode(getDigest2());
        String sPadding = "ISO10126Padding";

        byte[] objBytes = objectString.getBytes("UTF-8");
        byte[] objEncryptedBytes = demo1encrypt(demoKeyBytes, demoIVBytes, sPadding, objBytes);
        String encobj = new String(SimpleBase64Encoder.encode(objEncryptedBytes));

        return encobj;

    }

    static Object fromString(String s) throws Exception {

        byte[] demoKeyBytes = SimpleBase64Encoder.decode(getDigest1());
        byte[] demoIVBytes = SimpleBase64Encoder.decode(getDigest2());
        String sPadding = "ISO10126Padding";

        byte[] EncryptedBytes = SimpleBase64Encoder.decode(s);
        byte[] DecryptedBytes = demo1decrypt(demoKeyBytes, demoIVBytes, sPadding, EncryptedBytes);
        String objectString = new String(DecryptedBytes);

        byte[] data = SimpleBase64Encoder.decode(objectString);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public static String md5(String input) {

        String md5 = null;

        if (null == input) {
            return null;
        }

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex) 
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }

    public static String[] getFnameLname(String username) {
        if (!isInit) {
            init();
        }
        String[] fnameLname = new String[2];
        Connection con = null;
        try {

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/securedprofile", "root", "root123");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select " + Z.getDataWithDigest("data") + " from contact_details where username=" + Z.getWhereWithDigest(username));
            if (rs.next()) {

                AdminContactDetailsModal obj1 = (AdminContactDetailsModal) Z.fromString(rs.getString(1));

                fnameLname[0] = obj1.getFname();
                fnameLname[1] = obj1.getLname();
            }

        } catch (Exception e) {
            return null;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return fnameLname;

    }

    public static boolean createWelcomeNotification(String fname, final String forwhom) {
        if (!isInit) {
            init();
        }
        Connection con = null;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/securedprofile", "root", "root123");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();

            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date dateobj = new Date();
            Calendar calobj = Calendar.getInstance();
            String currenttime = df.format(calobj.getTime());
            String currenttimeenc = Z.Encrypt(currenttime);

            String title = "Welcome to Place Me !";
            String uploadedby = "Team PlaceMe";
            String notification = "Hello " + fname + ",\n"
                    + "Congratulations ! Your account has been successfully created on Place Me. \n"
                    + "We have unique and distinct features just for you. Place Me will provide placement opportunities either from your TPO (Training and Placement Officer) or directly from Recruiters/HR managers. Make sure you complete your profile before applying. All the best !";

            PreparedStatement statement = con.prepareStatement("Insert into adminnotification(title,notification,uploadtime,lastmodified,uploadedby,forwhom) values ('" + Encrypt(title) + "','" + Encrypt(notification) + "','" + currenttimeenc + "','" + currenttimeenc + "','" + Encrypt(uploadedby) + "','" + Encrypt(forwhom) + "')");
            int row2;
            row2 = statement.executeUpdate();

            String wid = null;
            if (row2 != 0) {
                System.out.println("Notification sent " + uploadedby + " forwhome " + forwhom);

                ResultSet rs = st.executeQuery("select id,title,uploadtime,forwhom from adminnotification order by id desc");
                while (rs.next()) {
                    System.out.println("title " + Z.Decrypt(rs.getString(2)) + " uploadtime" + Z.Decrypt(rs.getString(3)) + " forwhom " + Z.Decrypt(rs.getString(4)));
                    System.out.println("title " + title + " uploadtime " + currenttime + " forwhom " + forwhom);
                    if (Z.Decrypt(rs.getString(2)).equals(title) && Z.Decrypt(rs.getString(3)).equals(currenttime) && Z.Decrypt(rs.getString(4)).equals(forwhom)) {
                        wid = rs.getString(1);
                        int row = st2.executeUpdate("insert into welcomeuser(username,wid,time) values(" + Z.setDataWithDigest(forwhom) + ",'" + rs.getString(1) + "','" + currenttime + "')");
                        break;
                    }
                }

                return true;
            } else {
                System.out.println("Notification NOT sent " + uploadedby + " forwhome " + forwhom);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Z.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(Z.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public static boolean createNotification(String title, String notification, String uploadedby, String forwhom) {
        if (!isInit) {
            init();
        }
        try {
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date dateobj = new Date();
            Calendar calobj = Calendar.getInstance();
            String currenttime = df.format(calobj.getTime());
            String currenttimeenc = Z.Encrypt(currenttime);

            PreparedStatement statement = con.prepareStatement("Insert into adminnotification(title,notification,uploadtime,lastmodified,uploadedby,forwhom) values ('" + Encrypt(title) + "','" + Encrypt(notification) + "','" + currenttimeenc + "','" + currenttimeenc + "','" + Encrypt(uploadedby) + "','" + Encrypt(forwhom) + "')");
            int row2;
            row2 = statement.executeUpdate();
            if (row2 != 0) {
                System.out.println("Notification sent " + uploadedby + " forwhome " + forwhom);
                return true;
            } else {
                System.out.println("Notification NOT sent " + uploadedby + " forwhome " + forwhom);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Z.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(Z.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

}
