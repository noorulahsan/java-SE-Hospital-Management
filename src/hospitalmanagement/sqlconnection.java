/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagement;
import java.sql.*;

/**
 *
 * @author noorulahsan
 */
public class sqlconnection {
    static Connection connect;
    public Connection con()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return connect;
    }
    
}
