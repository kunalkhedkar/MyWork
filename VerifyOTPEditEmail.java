package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
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

public class VerifyOTPEditEmail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        System.out.println("123456 =========================== VerifyOTPEditEmail ====================================");
        //ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
        String params[] = Z.getParameters(request, 3);

        Connection con = null;
        try {

            con = Z.getNewSecuredProfileConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet rs;
            int found = 0, found2 = 0;

            String username = Z.Decrypt(params[0]);

            System.out.println("Username : " + username);

            String newusername = Z.Decrypt(params[1]);

            System.out.println("New Username : " + newusername);

            String data = Z.Decrypt(params[2]);

            System.out.println("Hash : " + data);

            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date dateobj = new Date();
            String format = df.format(dateobj);
            Calendar calobj1 = Calendar.getInstance();

            boolean flagdeletesuccess = false;
            rs = st.executeQuery("select * from editusernameotprequester where username=" + Z.getWhereWithDigest(username));
            if (rs.next()) {
                System.out.println("match found editusernameotprequester");
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
                    if (data.equals(rs.getString(2))) {
                        updateStatus(username, newusername);
                        st2.executeUpdate("delete from editusernameotprequester where username=" + Z.getWhereWithDigest(username));
                        json.put("info", "success");
                        System.out.println("delete success");
                        flagdeletesuccess = true;
                        //delete entry from editusername table specifying transaction from username to new username 
                    } else {
                        json.put("info", "fail");
                    }
                } else {
                    json.put("info", "expired");
                }

            }

            if (flagdeletesuccess == true) {
                System.out.println("flagdeletesuccess " + flagdeletesuccess);
                ResultSet rs1 = st.executeQuery("select username from editusername where username=" + Z.getWhereWithDigest(username));
                if (rs1.next()) {
                    st2.executeUpdate("delete from editusername where username=" + Z.getWhereWithDigest(username));
                    st2.executeUpdate("insert into editusername_all_detail values(" + Z.setDataWithDigest(username) + "," + Z.setDataWithDigest(newusername) + ",'" + df.format(calobj1.getTime()) + "')");
                    System.out.println("all done ********************************** ");
                }

            }

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

    void changeUsernameOldEncryption(String tablename, String columName, String username, String newusername) throws Exception {
        Connection con = Z.getNewSecuredProfileConnection();
        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select " + columName + " from " + tablename);

            while (rs.next()) {
                if (username.equals(Z.Decrypt(rs.getString(1)))) {
                    String encNewUsername = Z.Encrypt(newusername);
                    int row = st.executeUpdate("update " + tablename + " set " + columName + "=" + encNewUsername + " where " + columName + " =" + rs.getString(1));
                    if (row > 0) {
                        System.out.println(tablename + " updated " + row);
                    }
                    break;
                }
            }
        } catch (Exception e) {
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

    void changeUsername_CommaSeparatedOldEncryption(String tablename, String columName, String username, String newusername) throws Exception {
        Connection con = Z.getNewSecuredProfileConnection();
        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select " + columName + " from " + tablename);

            while (rs.next()) {

                String commaSeparatedString = Z.Decrypt(rs.getString(1));
                if (commaSeparatedString.contains(username)) {
                    commaSeparatedString = commaSeparatedString.replace(username, newusername);
                    int row = st.executeUpdate("update " + tablename + " set " + columName + "=" + Z.Encrypt(commaSeparatedString) + " where " + columName + " =" + rs.getString(1));
                    if (row > 0) {
                        System.out.println(tablename + " updated " + row);
                    }
                }
            }
        } catch (Exception e) {
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

    void changeUsername(String tablename, String columName, String username, String newusername) throws Exception {
        Connection con = Z.getNewSecuredProfileConnection();
        try {
            Statement st = con.createStatement();
            System.out.println("checking in " + tablename);
            ResultSet rs = st.executeQuery("select " + columName + " from " + tablename + " where " + columName + "=" + Z.getWhereWithDigest(username));
            if (rs.next()) {
                int row = st.executeUpdate("update " + tablename + " set " + columName + "=" + Z.setDataWithDigest(newusername) + " where " + columName + " =" + Z.getWhereWithDigest(username));
                if (row > 0) {
                    System.out.println(tablename + " updated " + row);
                }

            }

        } catch (Exception e) {
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

    void changeUsername_registered_users_under(String username, String newusername) throws Exception {

        Connection con = Z.getNewSecuredProfileConnection();
        try {
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            
             String ucode = "";
//user table is updated so where cont with new username
            ResultSet rs2 = st.executeQuery("select ucode from user where usernamed=" + Z.getWhereWithDigest(newusername));
            if (rs2.next()) {
                ucode = rs2.getString(1);
            }

            String TABLE_NAME = "zz_" + ucode;
            System.out.println("com.VerifyOTPEditEmail.changeUsername_registered_users_under() TABLE_NAME "+TABLE_NAME );
            int x = 0;
            ResultSet rs = st.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema='securedprofile' AND table_name='" + TABLE_NAME + "'");
            if (rs.next()) {
    
                ResultSet rs1 = st2.executeQuery("select id from " + TABLE_NAME + " where users="+Z.getWhereWithDigest(username));
                if (rs1.next()) {
                   int row = st.executeUpdate("update " + TABLE_NAME + " set users=" + Z.setDataWithDigest(newusername) + " where id="+ rs1.getString(1));
                   if(row>0)                   
                        System.out.println("updated in "+TABLE_NAME+" "+username);
                }
            }
  
            
        } catch (Exception e) {
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

    void updateStatus(String username, String newusername) {
        JSONObject json = new JSONObject();
        int flag1 = 0, flag2 = 0;
        try {

            Connection con = Z.getNewSecuredProfileConnection();
            Statement st = con.createStatement();
            ResultSet rs;
            String sPadding = "ISO10126Padding";
            rs = st.executeQuery("select * from editusername where username=" + Z.getWhereWithDigest(username) + " and newusername=" + Z.getWhereWithDigest(newusername));
            if (rs.next()) {
                int row = st.executeUpdate("update editusername set isactivated='yes' where username=" + Z.getWhereWithDigest(username));
            }

            rs = st.executeQuery("select usernamed from user where usernamed=" + Z.getWhereWithDigest(username));
            if (rs.next()) {
                int row = st.executeUpdate("update user set usernamed=" + Z.setDataWithDigest(newusername) + " where usernamed=" + Z.getWhereWithDigest(username));
            }

            // here
            changeUsername("admininstinfo", "username", username, newusername);
            changeUsername("careerobj", "username", username, newusername);
            changeUsername("certificates", "username", username, newusername);
            changeUsername("company_details", "username", username, newusername);
            changeUsername("contact_details", "username", username, newusername);
            changeUsername("courses", "username", username, newusername);
            changeUsername("currentsession", "username", username, newusername);
            changeUsername("device_ids", "username", username, newusername);
            changeUsername("diploma", "username", username, newusername);
            changeUsername("downloadedresumetemplates", "username", username, newusername);
            changeUsername("errors", "username", username, newusername);
            changeUsername("experiences", "username", username, newusername);
            changeUsername("honors", "username", username, newusername);
            changeUsername("intro", "username", username, newusername);
            changeUsername("knownlang", "username", username, newusername);
            changeUsername("lastloginsession", "username", username, newusername);
            changeUsername("locationpreferences", "username", username, newusername);
            changeUsername("patents", "username", username, newusername);
            changeUsername("personal", "username", username, newusername);
            changeUsername("pgsem", "username", username, newusername);
            changeUsername("pgyear", "username", username, newusername);
            changeUsername("placed_debar_info", "username", username, newusername);
            changeUsername("placedusers", "username", username, newusername);
            changeUsername("projects", "username", username, newusername);
            changeUsername("publications", "username", username, newusername);
            changeUsername("readmessages", "sender", username, newusername);
            changeUsername("readmessages", "reciever", username, newusername);
            changeUsername("registeredusers", "username", username, newusername);
            changeUsername("resume", "username", username, newusername);
            changeUsername("skills", "username", username, newusername);
            changeUsername("strengths", "username", username, newusername);
            changeUsername("tenth", "username", username, newusername);
            changeUsername("twelth", "username", username, newusername);
            changeUsername("ucodes", "users", username, newusername);
            changeUsername("ug", "username", username, newusername);
            changeUsername("user_profile", "username", username, newusername);
            changeUsername("user_profile_thumbnail", "username", username, newusername);
            changeUsername("weaknesses", "username", username, newusername);

            changeUsername_registered_users_under(username, newusername);        // admin tables

            changeUsernameOldEncryption("adminnotification", "uploadedby", username, newusername);
            changeUsernameOldEncryption("adminplacement", "uploadedby", username, newusername);
            changeUsernameOldEncryption("readnotifications", "username", username, newusername);
            changeUsernameOldEncryption("readplacements", "username", username, newusername);

            changeUsername_CommaSeparatedOldEncryption("adminnotification", "forwhom", username, newusername);
            changeUsername_CommaSeparatedOldEncryption("adminplacement", "forwhom", username, newusername);

        } catch (Exception e) {
            json.put("info", e.getMessage());
        }
    }
}
