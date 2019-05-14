/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author alvin
 */
public interface LibraryInterface extends Remote {
    public Boolean Validate(String tp)throws RemoteException;
    public boolean Register(String tp, String psw, String name, String course, String intake) throws RemoteException;
    public boolean Credential(String tp, String psw)throws RemoteException;
    public boolean SearchByName(int bookID,String bookName,String bookCategory,String bookQty)throws RemoteException;
    public boolean SearchByID(int bookID,String bookName,String bookCategory,String bookQty)throws RemoteException;
    public boolean SearchBorrowBook(int bookID,String bookName,String bookCategory,String bookQty)throws RemoteException;
    public String returnBook(int bookID,String bookName,String bookCategory,String bookQty)throws RemoteException;
    public String returnBookbyID(int bookID,String bookName,String bookCategory,String bookQty)throws RemoteException;
    public String borrowBook(int bookID,String bookName,String bookCategory,String bookQty,String sID)throws RemoteException; 
    public String ReturnBorrowBooks(int bookID,String bookName,String bookCategory,String bookQty,String sID)throws RemoteException;
}
