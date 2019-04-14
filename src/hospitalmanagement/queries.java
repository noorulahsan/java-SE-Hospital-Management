/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagement;

import com.sun.org.apache.xml.internal.resolver.Catalog;
import static hospitalmanagement.mainwindow.logdept;
import static hospitalmanagement.mainwindow.logid;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author noorulahsan
 */
public class queries {
    Statement st;
    ResultSet rs;
    String str;
    public queries()
    {
        try
        {           
           sqlconnection conne=new sqlconnection();
           Connection con=conne.con();
           st=con.createStatement();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
    
    public void insertpatient(String name,int age,String gender,int phone,String address,String place,String department,String date)
    {
            try
            {
        str="insert into patients(Name,Age,Gender,Phone,Address,Place,Department,RegDate) values('"+name+"','"+age+"','"+gender+"','"+phone+"','"+address+"','"+place+"','"+department+"','"+date+"')";
        st.executeUpdate(str);
    }
    catch(Exception e)
    {
        System.out.print(e);
    }
}
         public void insertlogin(String name,int age,String gender,String department,String qualification,int experience,String address,String place,int phone,String email,String username,String password,String type) 
    {
        try
        {
        str="insert into login(Username,Password,Type) values('"+username+"','"+password+"','"+type+"')";
        st.executeUpdate(str);
        String str1="select * from `login` order by ID desc limit 1";
        rs=st.executeQuery(str1);
        String lid="";
        if(rs.next())
        {
            lid=rs.getString(1);
        }
        if(type.equals("Doctor"))
        {
         str="insert into doctor(Name,Age,Gender,Department,Qualification,Experience,Address,Place,Phone,Email,LID) values('"+name+"','"+age+"','"+gender+"','"+department+"','"+qualification+"','"+experience+"','"+address+"','"+place+"','"+phone+"','"+email+"','"+lid+"')";
        st.executeUpdate(str);
        }
        else
        {
         str="insert into staff(Name,Age,Gender,Department,Qualification,Experience,Address,Place,Phone,Email,LID) values('"+name+"','"+age+"','"+gender+"','"+department+"','"+qualification+"','"+experience+"','"+address+"','"+place+"','"+phone+"','"+email+"','"+lid+"')";
        st.executeUpdate(str);   
        }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void appointmentinsert(int pid,int did)
    {
        try
        {
            str="insert into appointment(PID,DID)values('"+pid+"','"+did+"')";
            st.executeUpdate(str);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
    public void updatedescription(int pid,String description)
    {
        try
        {
            str="update patients set Description='"+description+"' where ID='"+pid+"'";
            st.executeUpdate(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void updatepatientdetails(String disease,int doctor,int id)
    {
        try
        {
            str="update patients set Doctor='"+doctor+"',Disease='"+disease+"'where ID='"+id+"'";
            st.executeUpdate(str);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
//    public void insertstaff(String name,int age,String gender,String department,String qualification,int experience,String address,String place,int phone,String email,String username,String password)
//    {
//        try
//        {
//            str="insert into staff(Name,Age,Gender,Department,Qualification,Experience,Address,Place,Phone,Email,Username,Password) values('"+name+"','"+age+"','"+gender+"','"+department+"','"+qualification+"','"+experience+"','"+address+"','"+place+"','"+phone+"','"+email+"','"+username+"','"+password+"')";
//            st.executeUpdate(str);
//        }
//        catch(Exception e)
//        {
//            System.out.print(e);
//        }
//    }
    public ResultSet usernamedoc(String user)
    {
        
        try
        {
            str="select username from login where Username='"+user+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return rs;
    }
    public ResultSet adminlogin(String username,String password)
    {
        try
        {
            str="select Username,Password from login where Username='"+username+"' and password='"+password+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet staffpatientdetails(String department)
    {
        try
        {
            str="select ID,Name,Age,Gender,Disease from patients where Department='"+department+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return rs;
    }
    public ResultSet staffallpatientdetails()
    {
        try
        {
            str="select ID,Name,Age,Gender,Disease from patients";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return rs;
    }
    public ResultSet doctorappointment()
    {
                try
        {
            str="select appointment.ID,patients.Name,patients.Age,patients.Gender,patients.Disease from appointment,patients where appointment.DID='"+logid+"'and appointment.PID=patients.ID";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return rs;
    }
    public ResultSet getpidfromtoken(int token)
    {
        try
        {
            str="select PID from appointment where ID='"+token+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet getconsult(int pid)
    {
        try
        {
            str="select Name,Age,Gender,Disease,Description from patients where ID='"+pid+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
//    public ResultSet usernamestf(String user)
//    {
//        try
//        {
//            str="select username from staff where Username='"+user+"'";
//            rs=st.executeQuery(str);
//        }
//        catch(Exception e)
//        {
//            System.out.print(e);
//        }
//        return rs;
//    }
    public ResultSet logindoctor(String username,String password)
    {
        int id=0;
        try
        {
            str="select login.*,doctor.ID,doctor.Name,doctor.Department from login,doctor where login.Username='"+username+"' and login.Password='"+password+"' and login.ID=doctor.LID";
            rs=st.executeQuery(str);
                    }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet loginstaff(String username,String password)
    {
                try
        {
            str="select login.*,staff.ID,staff.Name,staff.Department from login,staff where login.Username='"+username+"' and login.Password='"+password+"' and login.ID=staff.LID";
            rs=st.executeQuery(str);
                    }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet gettype(String username,String password)
    {
        try
        {
            str="select type from login where Username='"+username+"' and password='"+password+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet patientdetailsid(int id,String dept)
    {
        try
        {
            str="select Name, Age,Gender from patients where ID='"+id+"'and Department='"+dept+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return rs;
    }
    public ResultSet getdoctornamesandid()
    {
        String t="Doctor";
        try
        {
            str="select doctor.ID,doctor.Name from doctor,login where login.Type='"+t+"' and doctor.Department='"+logdept+"'and login.ID=doctor.LID";
            rs=st.executeQuery(str);
            
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return rs;
    }
    public void deleteappoint(int pid)
    {
        try
        {
            str="delete from appointment where PID='"+pid+"'";
            st.executeUpdate(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public ResultSet viewdeptpatient(String dept)
    {
        try
        {
            str="select patients.ID,patients.Name,patients.Age,patients.Gender,patients.Phone,patients.Address,patients.Place,patients.RegDate,doctor.Name,patients.Disease,patients.Description from patients,doctor where patients.Doctor=doctor.ID and patients.Department='"+dept+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet viewpatient()
    {
        try
        {
            str="select patients.ID,patients.Name,patients.Age,patients.Gender,patients.Phone,patients.Address,patients.Place,patients.RegDate,doctor.Name,patients.Disease,patients.Description from doctor,patients where patients.Doctor=doctor.ID";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet viewstaff()
    {
                try
        {
            str="select ID,Name,Age,Gender,Qualification,Experience,Address,Place,Phone,Email from staff";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet viewdeptstaff(String dept)
    {
                try
        {
            str="select ID,Name,Age,Gender,Qualification,Experience,Address,Place,Phone,Email from staff where Department='"+dept+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet viewdoctors()
    {
                try
        {
            str="select ID,Name,Age,Gender,Qualification,Experience,Address,Place,Phone,Email from doctor";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet viewdeptdoctors(String dept)
    {
        String type="Doctor";
        try
        {
            str="select ID,Name,Age,Gender,Qualification,Experience,Address,Place,Phone,Email from doctor where Department='"+dept+"'";
            rs=st.executeQuery(str);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
    }
    
}
