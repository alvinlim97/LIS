/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Client.StudentLibrary;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author alvin
 */
public class Server implements LibraryInterface {
     private static final int portNo =3386;
     private static Server defaultServer = null;
     private String serverName = null;
     private static StudentLibrary student = null;
     
     public Server(String name)
    {
        this.setServerName(name);
    }
    
    public String getServerName()
    {
        return serverName;
    }
    
    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }
    
     public static Connection condb(){   
        Connection con = null;
        try{
            Class.forName("org.sqlite.JDBC");
            
            con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alvin\\Documents\\NetBeansProjects\\LIS\\LIS_db.db");
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
           return con;
    }
    
     public static void main(String[] args) throws RemoteException, AlreadyBoundException {
       
        defaultServer = new Server("APULibraryServer");
        
        Registry registry = LocateRegistry.createRegistry(portNo);
        Remote obj = UnicastRemoteObject.exportObject(defaultServer, portNo);
        registry.bind(defaultServer.getServerName(), obj);
        System.out.println(defaultServer.getServerName() + " Is Running!");
      
        condb();  
    }
     @Override
    public Boolean Validate(String tp) throws RemoteException {
    ResultSet rs = null;
    PreparedStatement std = null;   
    Connection con = Server.condb();
    Boolean StudentData = false;
    
    if (con != null){
                try {
            String sql = "Select tp from LIS_std_db";
         
            std = con.prepareStatement(sql);
            rs = std.executeQuery();
            
            Object[] columnData = new Object[2];
             while(rs.next() && StudentData == false){
                 columnData[0] = rs.getString("tp");
                 
             if(tp.equals(columnData[0])){
             StudentData = true;
               std.close();
               rs.close();
               con.close();
             }else {
                 StudentData = false;
             }
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
         return StudentData;
       
    } 
     
     
    @Override
    public boolean Register(String tp, String psw, String name, String course, String intake) throws RemoteException {
    PreparedStatement std = null;
    Connection con = Server.condb();   
    Boolean Register = false;

        try {
            String sql = "insert into LIS_std_db (tp, psw, name, course, intake) Values(?,?,?,?,?)";
         
            std = con.prepareStatement(sql);
          
            std.setString(1, tp);
            std.setString(2, psw);
            std.setString(3, name);
            std.setString(4, course);
            std.setString(5, intake);
            
            std.execute();
            
            
            System.out.println("Successfuly Registered");
            Register = true;
            std.close();
            con.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
         return Register;
    }

    @Override
    public boolean Credential(String tp, String psw) throws RemoteException {
    ResultSet rs = null;
    PreparedStatement std = null;   
    Connection con = Server.condb();
    Boolean StudentData = false;
    
    if (con != null){
                try {
            String sql = "Select tp,psw from LIS_std_db";
         
            std = con.prepareStatement(sql);
            rs = std.executeQuery();
            
            Object[] columnData = new Object[2];
             while(rs.next() && StudentData == false){
                 columnData[0] = rs.getString("tp");
                 columnData[1] = rs.getString("psw");
                 
             if(tp.equals(columnData[0]) && psw.equals(columnData[1])){
             StudentData = true;
               std.close();
               rs.close();
               con.close();
             }else {
                 StudentData = false;
             }
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
         return StudentData;
       
    }

    @Override
    public boolean SearchByName(int bookID,String bookName,String bookCategory,String bookQty) throws RemoteException {
    ResultSet rs = null;
    PreparedStatement bk = null;   
    Connection con = Server.condb();
    Boolean BookData = false;
    int bID;
    String bName;
    String bCate;
    String bQty;
    String checkStatus = "Available";
    if (con != null){
                try {
            String sql = "Select bookID, bookName,bookCategory,bookQty from LIS_bk_db";
         
            bk = con.prepareStatement(sql);
            rs = bk.executeQuery();
            
            Object[] columnData = new Object[4];
             while(rs.next() && BookData == false){
                 columnData[0] = rs.getString("bookID");
                 columnData[1] = rs.getString("bookName");
                 columnData[2] = rs.getString("bookCategory");
                 columnData[3] = rs.getString("bookQty");
             if(bookName.equals(columnData[1]) && columnData[3].equals(checkStatus) ){

            bID = Integer.parseInt(columnData[0].toString());
            bName = columnData[1].toString();
            bCate =  columnData[2].toString();
            bQty = columnData[3].toString();
            
            System.out.println(bID);
            System.out.println(bName);
            System.out.println(bCate);
            System.out.println(bQty);

             BookData = true;
             
             
               bk.close();
               rs.close();
               con.close();
             }else {
                 BookData = false;
             }
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
 
    }
         return BookData;
       
    } 
   
    @Override
    public String returnBook(int bookID, String bookName, String bookCategory, String bookQty) throws RemoteException {
    ResultSet rs = null;
    PreparedStatement bk = null;   
    Connection con = Server.condb();
    Boolean BookData = false;
    int bID;
    String bName;
    String bCate;
    String bQty;
    String valid= null;
    
    if (con != null){
                try {
            String sql = "Select bookID, bookName,bookCategory,bookQty from LIS_bk_db";
         
            bk = con.prepareStatement(sql);
            rs = bk.executeQuery();
            
            Object[] columnData = new Object[4];
             while(rs.next() && BookData == false){
                 columnData[0] = rs.getString("bookID");
                 columnData[1] = rs.getString("bookName");
                 columnData[2] = rs.getString("bookCategory");
                 columnData[3] = rs.getString("bookQty");
             if(bookName != null && bookName.equals(columnData[1])){

            bID = Integer.parseInt(columnData[0].toString());
            bName = columnData[1].toString();
            bCate =  columnData[2].toString();
            bQty = columnData[3].toString();
            
            System.out.println(bID);
            System.out.println(bName);
            System.out.println(bCate);
            System.out.println(bQty);
              BookData = true;
            valid = "||Book ID:"+ bID +"||Book Name:"+ bName +"||Book Category:"+ bCate +"||Book Status:"+ bQty+"||";
            
               bk.close();
               rs.close();
               con.close();
             }else {
                 return valid = null;
             }
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
 
    }
         return valid;
       
    }

    @Override
    public boolean SearchByID(int bookID,String bookName,String bookCategory,String bookQty) throws RemoteException {
    ResultSet rs = null;
    PreparedStatement bk = null;   
    Connection con = Server.condb();
    Boolean BookData = false;
    int bID;
    String bName;
    String bCate;
    String bQty;
    String checkStatus = "Available";
    if (con != null){
                try {
            String sql = "Select bookID, bookName,bookCategory,bookQty from LIS_bk_db";
         
            bk = con.prepareStatement(sql);
            rs = bk.executeQuery();
            
            Object[] columnData = new Object[4];
             while(rs.next() && BookData == false){
                 columnData[0] = rs.getString("bookID");
                 columnData[1] = rs.getString("bookName");
                 columnData[2] = rs.getString("bookCategory");
                 columnData[3] = rs.getString("bookQty");
             if( columnData[0].equals(Integer.toString(bookID)) && columnData[3].equals(checkStatus) ){

            bID = Integer.parseInt(columnData[0].toString());
            bName = columnData[1].toString();
            bCate =  columnData[2].toString();
            bQty = columnData[3].toString();
            
            System.out.println(bID);
            System.out.println(bName);
            System.out.println(bCate);
            System.out.println(bQty);

             BookData = true;
             
               bk.close();
               rs.close();
               con.close();
             }else {
                 BookData = false;
             }
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
 
    }
         return BookData;
       
    }
    
   @Override
    public String returnBookbyID(int bookID, String bookName, String bookCategory, String bookQty) throws RemoteException {
    ResultSet rs = null;
    PreparedStatement bk = null;   
    Connection con = Server.condb();
    Boolean BookData = false;
    int bID;
    String bName;
    String bCate;
    String bQty;
    String valid= null;
    
    if (con != null){
                try {
            String sql = "Select bookID, bookName,bookCategory,bookQty from LIS_bk_db";
         
            bk = con.prepareStatement(sql);
            rs = bk.executeQuery();
            
            Object[] columnData = new Object[4];
             while(rs.next() && BookData == false){
                 columnData[0] = rs.getString("bookID");
                 columnData[1] = rs.getString("bookName");
                 columnData[2] = rs.getString("bookCategory");
                 columnData[3] = rs.getString("bookQty");
             if( columnData[0].equals(Integer.toString(bookID))){

            bID = Integer.parseInt(columnData[0].toString());
            bName = columnData[1].toString();
            bCate =  columnData[2].toString();
            bQty = columnData[3].toString();
            
            System.out.println(bID);
            System.out.println(bName);
            System.out.println(bCate);
            System.out.println(bQty);  
            BookData = true;
            valid = "||Book ID:"+ bID +"||Book Name:"+ bName +"||Book Category:"+ bCate +"||Book Status:"+ bQty+"||";
            
               bk.close();
               rs.close();
               con.close();
             }else {
                 return valid = null;
             }
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
 
    }
         return valid;
       
    }

    
    @Override
    public String borrowBook(int bookID, String bookName, String bookCategory, String bookQty,String sID) throws RemoteException {
        
        String sql = "UPDATE LIS_bk_db SET bookQty = ? , "
                + "studentID = ? "
                + "WHERE bookID = ?";
        String valid = null;
        try (Connection con = Server.condb();
                PreparedStatement bk = con.prepareStatement(sql)) {
            
            System.out.println("INININ");
            bk.setString(1, "Borrowed");
            bk.setString(2, sID);
            bk.setInt(3, bookID);
            bk.executeUpdate();
            valid ="Book Successfuly Borrowed";
            
            
               bk.close();
               con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return valid;
    }

    @Override
    public boolean SearchBorrowBook(int bookID,String bookName,String bookCategory,String bookQty) throws RemoteException {
    ResultSet rs = null;
    PreparedStatement bk = null;   
    Connection con = Server.condb();
    Boolean BookData = false;
    int bID;
    String bName;
    String bCate;
    String bQty;
    String checkStatus = "Borrowed";
    if (con != null){
                try {
            String sql = "Select bookID, bookName,bookCategory,bookQty from LIS_bk_db";
         
            bk = con.prepareStatement(sql);
            rs = bk.executeQuery();
            
            Object[] columnData = new Object[4];
             while(rs.next() && BookData == false){
                 columnData[0] = rs.getString("bookID");
                 columnData[1] = rs.getString("bookName");
                 columnData[2] = rs.getString("bookCategory");
                 columnData[3] = rs.getString("bookQty");
             if( columnData[0].equals(Integer.toString(bookID)) && columnData[3].equals(checkStatus) ){

            bID = Integer.parseInt(columnData[0].toString());
            bName = columnData[1].toString();
            bCate =  columnData[2].toString();
            bQty = columnData[3].toString();
            
            System.out.println(bID);
            System.out.println(bName);
            System.out.println(bCate);
            System.out.println(bQty);

             BookData = true;
             
               bk.close();
               rs.close();
               con.close();
             }else {
                 BookData = false;
             }
             }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
 
    }
         return BookData;
       
    }
    
    @Override
    public String ReturnBorrowBooks(int bookID, String bookName, String bookCategory, String bookQty, String sID) throws RemoteException {
       String sql = "UPDATE LIS_bk_db SET bookQty = ? , "
                + "studentID = ? "
                + "WHERE bookID = ?";
        String valid = null;
        try (Connection con = Server.condb();
                PreparedStatement bk = con.prepareStatement(sql)) {
            
            System.out.println("INININ");
            bk.setString(1, "Available");
            bk.setString(2, "NULL");
            bk.setInt(3, bookID);
            bk.executeUpdate();
            valid ="Book Successfuly Returned";
            
            
               bk.close();
               con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return valid;
    }
    
    
    
}
