/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.LibraryInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author alvin
 */
public class StudentLibrary {
    
    private static LibraryInterface connectServer;
    private String tp = null;
    private String psw = null;
    
     public void StudentLibrary() throws RemoteException, NotBoundException, MalformedURLException{
         
         
    connectServer = (LibraryInterface) Naming.lookup("rmi://localhost:3386/APULibraryServer");
    Scanner input = new Scanner(System.in);
    System.out.println("Welcome to APU Library System\n");
    
    System.out.println("Do you have an account?");
    System.out.println("1.Yes");
    System.out.println("2.No");
    int opt = input.nextInt(); 
    if (opt == 1){
    login();
    }
    else if (opt == 2){
    validate();
    } 
    }
    
    public void validate() throws RemoteException{
    Scanner input = new Scanner(System.in);
    System.out.println("Registration\n");
    
    System.out.println("TP number:");
    String tp = input.nextLine(); 
    System.out.println("Password:");
    String psw = input.nextLine(); 
    System.out.println("Name:");
    String name = input.nextLine(); 
    System.out.println("Course:");
    String course = input.nextLine(); 
    System.out.println("Intake:");
    String intake = input.nextLine();
    
     if( connectServer.Validate(tp)){
          
        System.out.println("TP has been registered"); 
        }
        else{      
        register(tp,psw,name,course,intake);
        }
    
    }
     
    public void register(String tp,String psw,String name,String course,String intake) throws RemoteException{
    Scanner input = new Scanner(System.in);
    System.out.println("Registration\n");
    
    if(connectServer.Register(tp, psw, name, course, intake)){
    login();
    }
    else{      
    System.out.println("Exit"); 
    }
    
   
    }
    
    public void login() throws RemoteException{
    Scanner input = new Scanner(System.in);
    System.out.println("Login\n");
    
    System.out.println("TP number:");
    tp = input.nextLine(); 
    System.out.println("Password:");
    psw = input.nextLine(); 
    
    if( connectServer.Credential(tp, psw)){
       System.out.println("Succesfully Login");
       search();
    }
    else{      
    System.out.println("Incorrect Username or Password"); 
    login();
    }
    }
   
    public void search() throws RemoteException{
    
    Scanner input = new Scanner(System.in);
    System.out.println("Search for Book(s)\n");
    
    System.out.println("Which method you want to search for book");
    System.out.println("1.Book Name:");
    System.out.println("2.Book ID:");
    System.out.println("3.Borrow Book:");
    System.out.println("4.Return Book:");
    int opt = input.nextInt();
    if(opt == 1){
    searchByName();
    }
    else if(opt == 2){
    searchByID();
    }else if(opt == 3){
    borrow();
    }else if(opt == 4){
    Return();
    }
    }
    
    public void searchByName() throws RemoteException{
    
    Scanner input = new Scanner(System.in);
    String bookName= null; 
    int bookID = 0;
    String bookCategory = null;
    String bookQty = null;
    System.out.println("Enter Book Name:");
    bookName= input.nextLine(); 
    
        if( connectServer.SearchByName(bookID,bookName,bookCategory,bookQty)){
           displayBook(bookID,bookName,bookCategory,bookQty);
        }
        else{      
        System.out.println("Book not found"); 
        }
    }
 
    public void displayBook(int bookID,String bookName,String bookCategory,String bookQty)throws RemoteException {
        String valid = connectServer.returnBook(bookID, bookName, bookCategory,bookQty);
        
        System.out.println(valid); 
      
    }
   
    public void searchByID() throws RemoteException{
    
    Scanner input = new Scanner(System.in);
    String bookName= null; 
    int bookID = 0;
    String bookCategory = null;
    String bookQty = null;
    System.out.println("Enter Book ID:");
    bookID= input.nextInt(); 
    
        if( connectServer.SearchByID(bookID,bookName,bookCategory,bookQty)){
            
           displayBookByID(bookID,bookName,bookCategory,bookQty);
        }
        else{      
        System.out.println("Book not found"); 
        }
    }
    
    public void displayBookByID(int bookID,String bookName,String bookCategory,String bookQty)throws RemoteException {
        String valid = connectServer.returnBookbyID(bookID, bookName, bookCategory,bookQty);
        
        System.out.println(valid); 
        
    }
    
    public void borrow() throws RemoteException{
    
    Scanner input = new Scanner(System.in);
    String bookName= null; 
    int bookID = 0;
    String bookCategory = null;
    String bookQty = null;
    System.out.println("Enter Book ID:");
    bookID= input.nextInt(); 
    String sID = tp;
        if(connectServer.SearchByID(bookID,bookName,bookCategory,bookQty)){
            
           displayBookByID(bookID,bookName,bookCategory,bookQty);
           
           displayBorrowBook(bookID,bookName,bookCategory,bookQty,sID);
        }
        else{      
        System.out.println("Book not found"); 
        }
    }
    
    public void displayBorrowBook(int bookID,String bookName,String bookCategory,String bookQty,String sID)throws RemoteException {
        String valid = connectServer.borrowBook(bookID, bookName, bookCategory,bookQty,sID);
        
        System.out.println(valid); 
      
    }
    
     public void Return() throws RemoteException{
    
    Scanner input = new Scanner(System.in);
    String bookName= null; 
    int bookID = 0;
    String bookCategory = null;
    String bookQty = null;
    System.out.println("Enter Book ID:");
    bookID= input.nextInt(); 
    String sID = tp;
        if(connectServer.SearchBorrowBook(bookID,bookName,bookCategory,bookQty)){
           
           displayReturnBook(bookID,bookName,bookCategory,bookQty,sID);
        }
        else{      
        System.out.println("Book not found"); 
        }
    }
    
    public void displayReturnBook(int bookID,String bookName,String bookCategory,String bookQty,String sID)throws RemoteException {
        String valid = connectServer.ReturnBorrowBooks(bookID, bookName, bookCategory,bookQty,sID);
        
        System.out.println(valid); 
      
    }
     
}
