package sqldatabase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;


public class sqldatabase {
	public static void main(String[] args) {
		// building a GUI
		JFrame frame = new JFrame("Georgia Tech Library Database"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		
		JLabel ssnLabel = new JLabel("SSN"); 
		JTextField ssnField = new JTextField(40); 
		JLabel firstNameLabel = new JLabel("First Name"); 
		JTextField firstNameField = new JTextField(40); 
		JLabel lastNameLabel = new JLabel("Last Name"); 
		JTextField lastNameField = new JTextField(40); 
		JLabel dobLabel = new JLabel("Date of Birth"); 
		JTextField dobField = new JTextField(40); 
		
		JTextArea output = new JTextArea(10,40);
		output.setEditable(false);
		
		JButton createButton = new JButton("Create User");
		JButton readButton = new JButton("Read User");
		JButton updateButton = new JButton("Update User");
		JButton deleteButton = new JButton("Delete User"); 
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(10,1));
		panel.add(ssnLabel); 
		panel.add(ssnField); 
		panel.add(firstNameLabel);
		panel.add(firstNameField);
		panel.add(lastNameLabel);
		panel.add(lastNameField);
		panel.add(dobLabel);
		panel.add(dobField); 
		panel.add(createButton);
		panel.add(readButton);
		panel.add(updateButton);
		panel.add(deleteButton);
		panel.add(output); 
		
		frame.add(panel);
		frame.setVisible(true);
		
		
		
		Connection con = null; 
		try {
			String url = "jdbc:mysql://localhost:3306/Georgia_Tech_Library_DB";
			String username = "root";
			String password = "mysqldatabase"; 
			con = DriverManager.getConnection(url, username, password);
			
			if (con != null) {
				System.out.println("Connected to the database"); 
			}
			
			//CREATE users
			String createPerson = "Insert INTO person (ssn, first_name, last_name, dob) values (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(createPerson); 
			// have to use long for ssn bc thats how its described in the person table
			stmt.setLong(1, 12345678);
			stmt.setString(2, "Jane");
			stmt.setString(3, "Doe");
			// have to use sql set data bc its set as type:date in person table on sql
			stmt.setDate(4, java.sql.Date.valueOf("2000-01-01")); //yyyy-mm-dd jan 1 2000
			int personInserted = stmt.executeUpdate();
			System.out.println (personInserted + " user inserted successfully"); 
			
			createPerson = "Insert INTO person (ssn, first_name, last_name, dob) values (?,?,?,?)"; 
			stmt = con.prepareStatement(createPerson);
			stmt.setLong(1,  87654321);
			stmt.setString(2, "John");
			stmt.setString(3,  "Doe");
			stmt.setDate(4, java.sql.Date.valueOf("2002-04-23"));
			
			personInserted = stmt.executeUpdate();
			System.out.println (personInserted + " user inserted successfully"); 
			
			createPerson = "Insert INTO person (ssn, first_name, last_name, dob) values (?,?,?,?)"; 
			stmt = con.prepareStatement(createPerson);
			stmt.setLong(1,  24681357); 
			stmt.setString(2, "James");
			stmt.setString(3,  "Hill");
			stmt.setDate(4, java.sql.Date.valueOf("2003-10-09"));
			
			personInserted = stmt.executeUpdate();
			System.out.println (personInserted + " user inserted successfully"); 


			
			//READ users
			String readPerson = "SELECT * FROM person WHERE ssn = ?";
			stmt = con.prepareStatement(readPerson);
			stmt.setLong(1, 12345678);
			ResultSet result = stmt.executeQuery(); 
			while (result.next()) {
				System.out.println("Reading User: "); 
				System.out.println("SSN: " + result.getLong("ssn"));
				System.out.println("First Name: " + result.getString("first_name"));
				System.out.println("Last Name: " + result.getString("last_name"));
				System.out.println("DOB: " + result.getDate("dob"));
				
			}
			
		
			
			//UPDATE last name of user
			String updateUser = "UPDATE person SET last_name = ? WHERE ssn = ?";
			stmt = con.prepareStatement(updateUser); 
			stmt.setString(1,  "Smith");
			stmt.setLong(2, 87654321);
			int userUpdated = stmt.executeUpdate(); 
				if (userUpdated > 0) {
					System.out.println("User updated successfully"); 
				}
				else {
					System.out.println("No user found with given SSN"); 
				}
			

			
			//DELETE user
			String deleteUser = "DELETE FROM person WHERE ssn = ?";
			stmt = con.prepareStatement(deleteUser); 
			stmt.setLong(1,  24681357);
			int personDeleted = stmt.executeUpdate(); 
			System.out.println (personDeleted + " user deleted successfully"); 
			
			
			
			//CREATE users w/ GUI
			createButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				try { 
					String createPerson = "Insert INTO person (ssn, first_name, last_name, dob) values (?,?,?,?)";
					Connection con = DriverManager.getConnection(url, username, password);
					PreparedStatement stmt = con.prepareStatement(createPerson); 
					stmt.setLong(1, (Long.parseLong(ssnField.getText())));
					stmt.setString(2, firstNameField.getText());
					stmt.setString(3, lastNameField.getText());
					stmt.setDate(4, java.sql.Date.valueOf(dobField.getText())); 
					int personInserted = stmt.executeUpdate();
					System.out.println (personInserted + " user inserted successfully"); 
					stmt.close(); 
					}
					
					catch (SQLException ex) {
						output.setText("Error: " + ex.getMessage()); 
					}

					
				
			}
			}); 
		
			
			//READ users w/ GUI
			readButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				try { 
					String readPerson = "SELECT * FROM person WHERE ssn = ?";
					Connection con = DriverManager.getConnection(url, username, password);
					PreparedStatement stmt = con.prepareStatement(readPerson);
					stmt.setLong(1, Long.parseLong(ssnField.getText()));
					ResultSet result = stmt.executeQuery(); 
					while (result.next()) {
						System.out.println("Reading User: "); 
						System.out.println("SSN: " + result.getLong("ssn"));
						System.out.println("First Name: " + result.getString("first_name"));
						System.out.println("Last Name: " + result.getString("last_name"));
						System.out.println("DOB: " + result.getDate("dob"));
						
					}
					stmt.close(); 
					}
					
					catch (SQLException ex) {
						output.setText("Error: " + ex.getMessage()); 
					}

					
				
			}
			});
			
			//UPDATE last name of user with GUI 
			updateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				try { 
					String updatePerson = "UPDATE person SET last_name = ? WHERE ssn = ?";
					Connection con = DriverManager.getConnection(url, username, password);
					PreparedStatement stmt = con.prepareStatement(updatePerson); 
					stmt = con.prepareStatement(updateUser); 
					stmt.setString(1, lastNameField.getText());
					stmt.setLong(2, Long.parseLong(ssnField.getText()));
					int userUpdated = stmt.executeUpdate(); 
						if (userUpdated > 0) {
							System.out.println("User updated successfully"); 
						}
						else {
							System.out.println("No user found with given SSN"); 
						}
					stmt.close(); 
					}
					
					catch (SQLException ex) {
						output.setText("Error: " + ex.getMessage()); 
					}

					
				
			}
			});
			
			//DELETE user w/ GUI
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				try { 
					String deletePerson = "DELETE FROM person WHERE ssn = ?";
					Connection con = DriverManager.getConnection(url, username, password);
					PreparedStatement stmt = con.prepareStatement(deletePerson); 
					stmt = con.prepareStatement(deleteUser); 
					stmt.setLong(1, (Long.parseLong(ssnField.getText())));
					int personDeleted = stmt.executeUpdate(); 
					System.out.println (personDeleted + " user deleted successfully"); 
					stmt.close(); 
					}
					
					catch (SQLException ex) {
						output.setText("Error: " + ex.getMessage()); 
					}

				
			}
			});
			
			stmt.close();
			con.close(); 
			
			
		} catch (SQLException e) {
			 System.out.println("Error connecting to the database");
			 e.printStackTrace();
	
		} 
	
	
		
	}
}