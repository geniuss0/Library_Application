package library_app;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;

public class Finder 
{
	public static void find_borrowing(Connection con) throws SQLException
	{
		Scanner scan = new Scanner(System.in);
		Statement stt = con.createStatement();
		ResultSet res = null;
		
		stt.execute("USE library");
		
		System.out.print("Enter the customer's name and surname: ");
		String name = scan.nextLine();
		String tn[] = name.split(" ");
		
		res = stt.executeQuery("SELECT borrowings.borrowing_id, borrowings.idb, books.title, " +
				"borrowings.idc, customers.fname, customers.lname " +
				"FROM borrowings INNER JOIN customers ON borrowings.idc = customers.idc " +
				"INNER JOIN books ON borrowings.idb = books.idb " +
				"WHERE customers.fname LIKE '" + tn[0] + "%' " +
				"AND customers.lname LIKE '" + tn[1] + "%'");	
		
		while(res.next())
		{
			System.out.println("Borrowing ID: " + res.getString("borrowing_id") +
					"\nName:        " + res.getString("fname") +
					"\nSurname:     " + res.getString("lname") +
					"\nCustomer ID: " + res.getString("idc") +
					"\nBook title:  " + res.getString("title") +
					"\nBook ID:     " + res.getString("idb") + "\n");
		}

		stt.close();
		res.close();
	}
	
	public static void find_customer(Connection con) throws SQLException
	{
		Scanner scan = new Scanner(System.in);
		Statement stt = con.createStatement();
		ResultSet res = null;
		
		stt.execute("USE library");
		
		System.out.print("Enter the customer's name and surname: ");
		String name = scan.nextLine();
		String tn[] = name.split(" ");
		
		res = stt.executeQuery("SELECT * FROM customers " +
				"WHERE customers.fname LIKE '" + tn[0] + "%'" +
				"AND customers.lname LIKE '" + tn[1] + "%'");
		
		while(res.next())
		{
			System.out.println(res.getString("idc") + " | " + res.getString("fname") +
					" | " + res.getString("lname") + " | " + res.getString("adress"));
		}
		
		stt.close();
		res.close();
	}
	
	public static void find_book(Connection con) throws SQLException
	{
		Scanner scan = new Scanner(System.in);
		Statement stt = con.createStatement();
		ResultSet res = null;
		
		stt.execute("USE library");
		
		System.out.println("At least one designation must be entered.");
		System.out.print("Enter the title: ");
		String title = scan.nextLine();
		System.out.print("Enter the author: ");
		String author = scan.nextLine();
		System.out.print("Enter the publishing house: ");
		String publishing_house = scan.nextLine();
		System.out.println("");
		
		res = stt.executeQuery("SELECT * FROM books " +
				"WHERE books.title LIKE '" + title + "%' " +
				"OR books.author LIKE '" + author + "%' " +
				"OR books.publishing_house LIKE '" + publishing_house + "%'");
		
		while(res.next())
		{
			System.out.println(res.getString("idb") + " | " + res.getString("title") +
					" | " + res.getString("author") + " | " + res.getString("publishing_house") +
					" | " + res.getString("quantity"));
		}
		
		stt.close();
		res.close();
	}
	
	public static void find_your_own(Connection con) throws SQLException
	{				
		Scanner scan = new Scanner(System.in);
		Statement stt = con.createStatement();
		ResultSet res = null;
		
		boolean b;
		String ans;
		
		do
		{
			System.out.println("Enter the SQL query:");
			String query = scan.nextLine();
			b = false;
			try
			{
				res = stt.executeQuery(query);
				ResultSetMetaData rsmd = res.getMetaData();
				
				while(res.next())
				{
					System.out.print("| ");
					for(int i = 1; i <= rsmd.getColumnCount(); i++ )
					{
						System.out.println(res.getString(i) + " | ");
					}
				}
				res.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error! Do you wont to try again? YES | NO");
				ans = scan.nextLine();
				ans.toUpperCase();
				if(ans.equals("YES") || ans.equals("Y")) { b = true; }
			}
		}
		while(b == true);
		
		stt.close();
	}
}
