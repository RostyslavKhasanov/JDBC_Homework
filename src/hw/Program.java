package hw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
	
public class Program {

	static Connection conn;
	
	public static void main(String[] args) throws SQLException, IOException {
		String dbUrl = "jdbc:mysql://localhost:3306/people?useSSL=false";
		String username = "root";
		String password = "nejtrino24";
		boolean exit = true;
		
		conn  = DriverManager.getConnection(dbUrl, username, password);
		
		alterTablePersonAndCity();
		alterTableCityAndCountry();
		
		System.out.println(
				"Menu \n"
				+ "1 - Додати країну до БД \n"
				+ "2 - Додати місто до БД \n"
				+ "3 - Додати людину до БД \n"
				+ "4 - Показати список людей \n"
				+ "5 - Показати список міст \n"
				+ "6 - Показати список країн \n"
				+ "7 - Показати інформацію про одну людину по id \n"
				+ "8 - Показати інформацію про одне місто по id \n"
				+ "9 -  Показати інформацію про одну країну по id \n"
				+ "10 - Показати інформацію про людей з одного міста \n"
				+ "11 - Показати список міст однієї країни \n"
				+ "12 - Показати інформацію про людину, з якого вона міста і країни \n"
				+ "13 - Знайти людину за іменем в відповідності з заданим шаблоном \n"
				+ "14 - Заповнити рандомними даними \n");
		
		while (exit) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			
			String menu = bf.readLine();
			switch (menu) {
				case "1" : {
					System.out.println("Введіть назву країни");
					String name = bf.readLine();
					System.out.println(name);
					insertCountry(name);
				} break;
				
				case "2" : {
					System.out.println("Введіть назву країни:");
					String country_name = bf.readLine();
					System.out.println("Введіть назву міста:");
					String cityName = bf.readLine();
					insertCity(cityName, country_name);
				} break;
				
				case "3" : {
					System.out.println("Імя:");
					String first_name = bf.readLine();
					System.out.println("Прізвище:");
					String last_name = bf.readLine();
					System.out.println("Вік:");
					int age = Integer.valueOf(bf.readLine());
					System.out.println("Місто: ");
					String city_name = bf.readLine();
					insertPerson(first_name, last_name, age, city_name);
				} break;
				
				case "4" : {
					System.out.println("Список людей:");
					selectPerson();
				} break;
				
				case "5" : {
					System.out.println("Список міст:");
					selectCity();
				} break;
				
				case "6" : {
					System.out.println("Список країн:");
					selectCountry();
				} break;
				
				case "7" : {
					System.out.println("Введіть id:");
					int id = Integer.valueOf(bf.readLine());
					selectPerson(id);
				} break;
				
				case "8" : {
					System.out.println("Введіть id:");
					int id = Integer.valueOf(bf.readLine());
					selectCity(id);
				} break;
				
				case "9" : {
					System.out.println("Введіть id:");
					int id = Integer.valueOf(bf.readLine());
					selectCountry(id);
				} break;
				
				case "10" : {
					System.out.println("Введіть назву міста:");
					String cityName = bf.readLine();
					selectPersonFromCity(cityName);
				} break;
				
				case "11" : {
					System.out.println("Введіть назву країни");
					String name = bf.readLine();
					selectCityCountry(name);
				} break;
				
				case "12" : {
					selectPersonFullInfo();
				} break;
				
				case "13" : {
					System.out.println("Введіть щось :");
					String name = bf.readLine();
					selectPersonByShablon(name);
				} break;
				
				case "14" : {
						insertRandomPerson();
				} break;
				
				case "15" : {
					System.out.println("Змінити ім'я натисніть - 1 \n"
							+ "Змінити Прізвище натисніть - 2 \n"
							+ "Змінити Вік натисніть - 3 \n"
							+ "Змінити Місто натисніть - 4 \n");
					String count = bf.readLine();
					switch (count) {
						case "1" : {
							int id = Integer.valueOf(bf.readLine());
							String first_name = bf.readLine();
							updatePersonByFirstName(id, first_name);
						} break;
						case "2" : {
							int id = Integer.valueOf(bf.readLine());
							String last_name = bf.readLine();
							updatePersonByLastName(id, last_name);
						} break;
						
						case "3" : {
							int id = Integer.valueOf(bf.readLine());
							int age = Integer.valueOf(bf.readLine());
							updatePersonByAge(id, age);
						} break;
					}
				} break;
				 
				case "exit" : {
					exit = false;
				} break;
			}
		}
	}
	
	private static void createTablePerson() throws SQLException {
		String dropQuery = "drop table if exists person";
		String query = "create table person("
				+ "id int not null primary key auto_increment,"
				+ "first_name varchar(30) not null,"
				+ "last_name varchar(30) not null,"
				+ "age int,"
				+ "city_id int not null"
				+ ");";
		
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void createTableCity() throws SQLException {
		String dropQuery = "drop table if exists city";
		String query = "create table city("
				+ "id int not null primary key auto_increment,"
				+ "name varchar(30) not null,"
				+ "country_id int not null"
				+ ");";
		
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void createTableCountry() throws SQLException {
		String dropQuery = "drop table if exists country;";
		String query = "create table country("
				+ "id int not null primary key auto_increment,"
				+ "name varchar(30) not null"
				+ ");";
		
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void alterTableCityAndCountry() throws SQLException {
		String query = "alter table city add foreign key (country_id) references country (id);";
		
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void alterTablePersonAndCity() throws SQLException {
		String query = "alter table person add foreign key (city_id) references city (id);";
		
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void insertCountry(String name) throws SQLException {
		String query = "insert into country(name) values(?);";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, name);
		pstmt.executeUpdate();
		
		pstmt.close();
	}
	
	private static void insertCity(String cityName, String country_name) throws SQLException {
		int country_id = 0;
		boolean check = false;
		String query = "select * from country";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if (rs.getString("name").equals(country_name)) {
				country_id = rs.getInt("id");
				check = true;
			}
		}
		
		if (check) {
		query = "insert into city(name, country_id) values(?,?);";
		
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, cityName);
		pstmt.setInt(2, country_id);
		pstmt.executeUpdate();
		
		pstmt.close();
		} else {
			System.out.println("Країни не знайдено!");
		}
	}
	
	private static void insertPerson(String first_name, String last_name, int age, String city_name) throws SQLException {
		String query = "select * from city;";
		int city_id = 0;
		boolean check = false;
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			if (city_name.equals(rs.getString("name"))) {
				city_id = rs.getInt("id");
			}
		}
		
		query = "insert into person(first_name, last_name, age, city_id) values(?,?,?,?);";
		
		if (check) {
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, first_name);
		pstmt.setString(2, last_name);
		pstmt.setInt(3, age);
		pstmt.setInt(4, city_id);
		
		pstmt.executeUpdate();
		pstmt.close();
		} else {
			System.out.println("Місто із заданим ім'ям не знайдено");
		}
	}
	
	private static void insertRandomPerson() throws SQLException {
		try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("persons.txt"), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                String [] words = line.split(" ");
                String first_name = words[0];
                String last_name = words[1];
                int age = Integer.valueOf(words[2]);
                int city_id = Math.abs((int) (1 + Math.random() * 16)); 
                
                String query = "insert into person(first_name, last_name, age, city_id) values(?,?,?,?);";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, first_name);
                pstmt.setString(2, last_name);
                pstmt.setInt(3, age);
                pstmt.setInt(4, city_id);
                
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (IOException e) {
            // log error
        }
	}
	
	private static void updatePersonByFirstName(int id, String first_name) throws SQLException {
		String query = "update person set first_name = '" + first_name +"' where id = " + id + ";";
		
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void updatePersonByLastName(int id, String last_name) throws SQLException {
		String query = "update person set last_name = '" + last_name +"' where id = " + id + ";";
		
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void updatePersonByAge(int id, int age) throws SQLException {
		String query = "update person set age = '" + age +"' where id = " + id + ";";
		
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		
		stmt.close();
	}
	
	private static void selectCountry() throws SQLException {
		String query = "select * from country";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(rs.getString("name") );
		}
	}
	
	private static void selectCountry(int id) throws SQLException {
		String query = "select * from country where id = " + id;
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(rs.getString("name") );
		}
	}
	
	private static void selectCity() throws SQLException {
		String query = "select * from city order by name desc";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("name"));
		}
	}
	
	private static void selectCity(int id) throws SQLException {
		String query = "select * from city where id = " + id;
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("name"));
		}
	}
	
	private static void selectPerson() throws SQLException {
		String query = "select * from person order by first_name";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println("id: " + rs.getInt("id") + "\t "
					+ rs.getString("first_name") + " "
					+ rs.getString("last_name") + " "
					+ "age: " + rs.getInt("age") + " ");
			
		}
	}
	
	private static void selectPerson(int id) throws SQLException {
		String query = "select * from person where id = " + id;
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(rs.getString("first_name") + " "
					+ rs.getString("last_name") + " "
					+ "age: " + rs.getInt("age") + " ");
			
		}
	}
	
	private static void selectPersonFromCity(String cityName) throws SQLException {
		boolean check = true;
		String query = "select p.first_name, p.last_name, c.name from person p right join city  c on c.id = p.city_id;";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if (cityName.equals(rs.getString("name"))) {
			System.out.println(rs.getString("first_name") + " "+ rs.getString("last_name"));
			check = false;
			}
		}
		if (check) {
			System.out.println("Міста не існує!");
		}
	}
	
	private static void selectCityCountry(String name) throws SQLException {
		String query = "select c.name, ct.name from country ct join city c on ct.id = c.country_id;";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			if (rs.getString("ct.name").equals(name)) {
			System.out.println(rs.getString("c.name"));
			}
		}
	}
	
	private static void selectPersonFullInfo() throws SQLException {
		String query = "select p.first_name, p.last_name, c.name, ct.name from person p left join city c on c.id = p.city_id"
				+ " join country ct on ct.id = c.country_id;";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			System.out.println(rs.getString("first_name") + " " + rs.getString("last_name") + " " + rs.getString("c.name") + " " + rs.getString("ct.name"));
		}
	}
	
	private static void selectPersonByShablon(String name) throws SQLException {
		String query = "select * from person where first_name like '%" + name + "%';";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println("first name: " + rs.getString("first_name") + " "
					+ "last_name: " + rs.getString("last_name") + " "
					+ "age: " + rs.getInt("age") + " ");
			
		}
	}
}
	