package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import placeme.octopusites.com.placeme.modal.AdminIntroModal;
import placeme.octopusites.com.placeme.modal.Modelmyprofileintro;

public class Auth extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject json = new JSONObject();
        String params[] = Z.getParameters(request, 2);
        System.out.println("123456 =========================== Auth ==================================== ");
        Connection con = null;
        try {
            System.out.println("Auth start --- ");
            con = Z.getNewSecuredProfileConnection();
            Statement st = con.createStatement();
            ResultSet rs, rs1, rs2, rs3, rs4;
            Statement st1 = con.createStatement();

            String username = Z.Decrypt(params[0]);
            String data = Z.Decrypt(params[1]);

            String hash = Z.md5(data + Z.getSalt());
            String role;
            String adminUsername = null;
            rs = st.executeQuery("select usernamed,password,usertype,isactivated,ucode from user where usernamed=" + Z.getWhereWithDigest(username));
            if (rs.next()) {
                json.put("subadmin", "no");  // initilize
                if (hash.equals(rs.getString(2))) {
                    if (rs.getString(4).equals("yes")) {
                        json.put("info", rs.getString(3));
                    } else {
                        //chek if user has been created through admin 
                        json.put("info", "notactivated");
                        json.put("role", rs.getString(3));
                        role = rs.getString(3);
                        // check if user is created by admin 
                        boolean throughADminFlag = false;
                        rs1 = st1.executeQuery("select " + Z.getDataWithDigest("usernamed") + ",usertype,ucode from user where usertype='admin' AND ucode='" + rs.getString(5) + "'");
                        if (rs1.next()) //Master admin will found first [child admin with same ucode]
                        {
                            String ADMIN_USERNAME = rs1.getString(1);
                            adminUsername = rs1.getString(1);

                            String ucode = rs1.getString(3);
                            String TABLE_NAME = "zz_" + ucode;

                            rs2 = st.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema='securedprofile' AND table_name='" + TABLE_NAME + "'");
                            if (rs2.next()) {
                                json.put("table", "exist");

                                rs3 = st.executeQuery("select users from " + TABLE_NAME + " where users=" + Z.getWhereWithDigest(username));
                                if (rs3.next()) {
                                    json.put("throughAdmin", "yes");
                                    throughADminFlag = true;

                                }

                            }
                            if (throughADminFlag == true) {

                                System.out.println("Admin username " + ADMIN_USERNAME);

                                rs4 = st.executeQuery("select " + Z.getDataWithDigest("data") + " from intro where username=" + Z.getWhereWithDigest(adminUsername));
                                if (rs4.next()) {
                                    System.out.println("admin intro data found");
                                    AdminIntroModal objIntro = (AdminIntroModal) Z.fromString(rs4.getString(1));
                                    json.put("adminInst", Z.Encrypt(objIntro.getInstitute()));
                                    json.put("adminfname", Z.Encrypt(objIntro.getFname()));
                                    json.put("adminlname", Z.Encrypt(objIntro.getLname()));
                                    System.out.println("admin fname " + objIntro.getFname());
                                    System.out.println("admin lname " + objIntro.getLname());
                                }
                            }
                        }
                        if (throughADminFlag == false) {

                            if (role != null && role.equals("admin")) {

                                rs4 = st.executeQuery("select " + Z.getDataWithDigest("data") + " from intro where username=" + Z.getWhereWithDigest(adminUsername));
                                if (rs4.next()) {
                                    System.out.println("admin intro data found");
                                    AdminIntroModal objIntro = (AdminIntroModal) Z.fromString(rs4.getString(1));
                                    json.put("adminInst", Z.Encrypt(objIntro.getInstitute()));
                                    json.put("adminfname", Z.Encrypt(objIntro.getFname()));
                                    json.put("adminlname", Z.Encrypt(objIntro.getLname()));
                                    System.out.println("admin fname " + objIntro.getFname());
                                    System.out.println("admin lname " + objIntro.getLname());
                                    json.put("subadmin", "yes");
                                }

                            }
                            rs = st.executeQuery("select " + Z.getDataWithDigest("data") + " from intro where username=" + Z.getWhereWithDigest(username));
                            if (rs.next()) {
                                Modelmyprofileintro obj = (Modelmyprofileintro) Z.fromString(rs.getString(1));
                                sendMail(username, obj.firstname, obj.lastname);
                            }
                            json.put("throughAdmin", "no");
                        }
                    }
                } else {
                    json.put("info", "fail");
                }

            } else {
                json.put("info", "notpresent");
            }
            System.out.println("123456 =========================== Auth ==================================== end");
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

    void sendMail(String username, String fname, String lname) throws Exception {

        Z.sendMail(username, "Confirm Your Place Me Account", "<link href=\"http://octopusites.com/app_mail_res/vendor/simple-line-icons/css/simple-line-icons.css\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                + "<link href=\"http://octopusites.com/app_mail_res/vendor/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                + "<link href=\"http://octopusites.com/app_mail_res/css/layout.css\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                + "<link rel=\"shortcut icon\" href=\"favicon.ico\"/>\n"
                + "<link href=\"https://fonts.googleapis.com/css?family=Hammersmith+One\" rel=\"stylesheet\">\n"
                + "<link href=\"https://fonts.googleapis.com/css?family=Nunito\" rel=\"stylesheet\">\n"
                + "<link href=\"https://fonts.googleapis.com/css?family=Righteous\" rel=\"stylesheet\">\n"
                + "<div class=\"container\">\n"
                + "<img src=\"http://octopusites.com/app_mail_res/img/Place-Me-Logo.png\" width=\"100\" height=\"100\" class=\"img-responsive\">\n"
                + "<div class=\"row text-center\" style=\"margin: auto;\">\n"
                + "<h2 class=\"sendmail\">Congratulations !</h2>\n"
                + "<p class=\"text-left emailtext\">Hello " + fname + " " + lname + ",</p>\n"
                + "<p class=\"text-left emailtext\">Thank you for creating an account on Place Me. You have chosen to create your account under <strong>\"Student\"</strong> role.</p>\n"
                + "<center><img src=\"http://octopusites.com/app_mail_res/img/Student-Role-Image.png\" height=\"100\" width=\"100\" class=\"img-responsive\"></center><br>\n"
                + "<p class=\"emailtext\" style=\"font-size: 20px;\">Your One Time Password (OTP) is <strong>" + Z.generateOTP(username, 6) + "</strong>.</p><br> \n"
                + "<p class=\"text-left emailtext\">This OTP is valid only for 30 minutes. We request you to not share this with anyone.</p>\n"
                + "<p class=\"text-left emailtext\">If you have not requested this message then please ignore this mail.</p>\n"
                + "</div>\n"
                + "</div>\n"
                + "<script src=\"http://octopusites.com/app_mail_res/vendor/jquery.min.js\" type=\"text/javascript\"></script>\n"
                + "<script src=\"http://octopusites.com/app_mail_res/vendor/jquery-migrate.min.js\" type=\"text/javascript\"></script>\n"
                + "<script src=\"http://octopusites.com/app_mail_res/vendor/bootstrap/js/bootstrap.min.js\" type=\"text/javascript\"></script>\n"
                + "<script src=\"http://octopusites.com/app_mail_res/js/layout.min.js\" type=\"text/javascript\"></script>\n"
                + "\n"
                + "");

    }

}
