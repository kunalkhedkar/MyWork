package com;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class EditEmail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        System.out.println("123456 =========================== EditEmail ==================================== ");
        //ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
        String params[] = Z.getParameters(request, 3);

        Connection con = null;
        try {

            con = Z.getNewSecuredProfileConnection();
            Statement st = con.createStatement();
            ResultSet rs;

            int found = 0, found2 = 0, foundnewusername = 0;

            String username = Z.Decrypt(params[0]);

            System.out.println("Username : " + username);
            String newusername = Z.Decrypt(params[1]);
            System.out.println("New Username : " + newusername);

            String data = Z.Decrypt(params[2]);
            String hash = Z.md5(data + Z.getSalt());

            System.out.println("Hash : " + hash);

            rs = st.executeQuery("select password from user where usernamed=" + Z.getWhereWithDigest(username));
            if (rs.next()) {
                if (rs.getString(1).equals(hash)) {
                    found = 1;
                    System.out.println("username and hash matched ");
                } else {
                    json.put("info", "wrong");
                }
            } else {
                json.put("info", "not found");
            }

            if (found == 1) {

                System.out.println("searching for new username in user table");
                rs = st.executeQuery("select id from user where usernamed=" + Z.getWhereWithDigest(newusername));
                if (rs.next()) {
                    System.out.println("new username found in user table");
                    foundnewusername = 1;
                }
                if (foundnewusername == 1) {
                    json.put("info", "already");
                } else {
                    System.out.println("new username not found in user table");
                    rs = st.executeQuery("select isactivated," + Z.getDataWithDigest("newusername") + " from editusername where username=" + Z.getWhereWithDigest(username));
                    if (rs.next()) {

                        if (rs.getString(2).equals(newusername)) {
                            if (rs.getString(1).equals("no")) {

                                sendMail(newusername, username);

                                json.put("info", "success");
                            }

                        } else {
                            st.executeUpdate("update editusername set newusername=" + Z.setDataWithDigest(newusername) + ",isactivated='no' where username=" + Z.getWhereWithDigest(username));

                            sendMail(newusername, username);

                            json.put("info", "success");
                        }

                    }
                    if (found2 == 0) {
                        st.executeUpdate("insert into editusername values(" + Z.getWhereWithDigest(username) + "," + Z.getWhereWithDigest(newusername) + ",'no')");

                        sendMail(newusername, username);

                        json.put("info", "success");

                    }

                }
            }
            System.out.println("123456 =========================== EditEmail ==================================== end");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("info", e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    void sendMail(String newusername, String username) throws Exception {

        Z.sendMail(newusername, "Confirm Your New Email Address", "<link href=\"http://octopusites.com/app_mail_res/vendor/simple-line-icons/css/simple-line-icons.css\" rel=\"stylesheet\" type=\"text/css\"/> \n"
                + "<link href=\"http://octopusites.com/app_mail_res/vendor/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\"/> \n"
                + "<link href=\"http://octopusites.com/app_mail_res/css/layout.css\" rel=\"stylesheet\" type=\"text/css\"/> \n"
                + "<link rel=\"shortcut icon\" href=\"favicon.ico\"/> \n"
                + "<link href=\"https://fonts.googleapis.com/css?family=HammersmithOne\" rel=\"stylesheet\"> \n"
                + "<link href=\"https://fonts.googleapis.com/css?family=Nunito\" rel=\"stylesheet\"> \n"
                + "<link href=\"https://fonts.googleapis.com/css?family=Righteous\" rel=\"stylesheet\"> \n"
                + "<div class=\"container\"> \n"
                + "<img src=\"http://octopusites.com/app_mail_res/img/Place-Me-Logo.png\" width=\"100\" height=\"100\" class=\"img-responsive\"> \n"
                + "<div class=\"row text-center\" style=\"margin: auto;\"> \n"
                + "<h2 class=\"sendmail\">Congratulations !</h2> \n"
                + "<p class=\"text-left emailtext\">For changing your account's username from<strong>'" + username + "'</strong> to <strong>'" + newusername + "'</strong></p> \n"
                + "<center><img src=\"http://octopusites.com/app_mail_res/img/Student-Role-Image.png\" height=\"100\" width=\"100\" class=\"img-responsive\"></center><br> \n"
                + "<p class=\"emailtext\" style=\"font-size: 20px;\">Your One Time Password (OTP) is <strong>" + generateOTP(username) + "</strong>.</p><br> \n"
                + "<p class=\"text-left emailtext\">This OTP is valid only for 30 minutes. We request you to not share this with anyone.</p>\n"
                + "<p class=\"text-left emailtext\">If you have not requested this message then please ignore this mail.</p>\n"
                + "</div>\n"
                + "</div> \n"
                + "<script src=\"http://octopusites.com/app_mail_res/vendor/jquery.min.js\" type=\"text/javascript\"></script> \n"
                + "<script src=\"http://octopusites.com/app_mail_res/vendor/jquery-migrate.min.js\" type=\"text/javascript\"></script> \n"
                + "<script src=\"http://octopusites.com/app_mail_res/vendor/bootstrap/js/bootstrap.min.js\" type=\"text/javascript\"></script> \n"
                + "<script src=\"http://octopusites.com/app_mail_res/js/layout.min.js\" type=\"text/javascript\"></script> \n");

    }

    String generateOTP(String username) {
        StringBuilder generatedToken = new StringBuilder();
        Connection con = null;
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < 6; i++) {
                generatedToken.append(number.nextInt(9));
            }

            con = Z.getNewSecuredProfileConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet rs;

            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));
            Calendar calobj = Calendar.getInstance();

            rs = st.executeQuery("select username from editusernameotprequester where username=" + Z.getWhereWithDigest(username));
            if (rs.next()) {
                st2.executeUpdate("update editusernameotprequester set OTP='" + generatedToken.toString() + "',time='" + df.format(calobj.getTime()) + "' where username=" + Z.getWhereWithDigest(username));
            } else {
                st.executeUpdate("insert into editusernameotprequester values(" + Z.setDataWithDigest(username) + ",'" + generatedToken.toString() + "','" + df.format(calobj.getTime()) + "')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return generatedToken.toString();
    }

}
