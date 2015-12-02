package icecreamshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {
			
		private static Connection conn;
		
		public DBConnect(String dbURL, String user, String password) throws SQLException{
		
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/icecream", "root", "");
		
		}
		
		public void shutdown() throws SQLException{
		
			if(conn != null){
				conn.close();
			}
			
			
		}
		
		public void insert(String fName, String lName, String fave) throws SQLException {
			Statement insert = conn.createStatement();
			insert.executeUpdate("INSERT INTO customer (firstName, lastName, faveFlavor) " + "VALUES ('" + fName + "' , '" + lName + "', '" + fave  + "')");
		}
		
		public List<Customer> getCustomers() throws SQLException{
		
			try(
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from customer");
		
			){
		
				List<Customer> customerList = new ArrayList<>();
				while (rs.next()){
		
					String fName = rs.getString("firstName");
					String lName = rs.getString("lastName");
					String faveFlavor = rs.getString("faveFlavor");
		
					Customer customer = new Customer(fName, lName, faveFlavor);
					customerList.add(customer);
				}
				return customerList;
			}
		}
			
			public List<Order> getOrders() throws SQLException{
				
				try(
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from orders");
			
				){
			
					List<Order> orderList = new ArrayList<>();
					while (rs.next()){
			
						int orderID = rs.getInt("order_num");
						String type = rs.getString("cone_type");
						int custID = rs.getInt("customerID");
						String flavor = rs.getString("cone_flavor");
						String scoops = rs.getString("numScoops");
						String status = rs.getString("status");
			
						Order order = new Order(orderID, type, custID, flavor, scoops, status);
						orderList.add(order);
					}
					return orderList;
				}
				
		}
}
			