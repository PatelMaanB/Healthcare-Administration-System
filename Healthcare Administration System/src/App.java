import java.sql.*;
import java.util.*;
import java.io.*;
    public class App {
        public static void main(String[] args) throws Exception {
            Scanner sc = new Scanner(System.in);
            String dbUrl = "jdbc:mysql://localhost:3306/Hospital";
            String dbUser = "root";
            String dbPass = "";
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            if (con != null) {
                System.out.println("Connection success");
                System.out.println();
            } else {
                System.out.println("Connection failed");
                System.out.println();
                return;
            }

            System.out.println("~~~~~~~~~~ Welcome To Hospital Management System ~~~~~~~~~~");
            System.out.println();

            while (true) {
                System.out.println("Enter 1 for Doctor Details");
                System.out.println("Enter 2 for Patients");
                System.out.println("Enter 3 for Appointments");
                System.out.println("Enter 4 for Exit");
                int choice = sc.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        displayDoctor(con);
                        break;
                    case 2:
                        System.out.println("Enter 1 for Add Patient Details");
                        System.out.println("Enter 2 for Delete Patient Details");
                        System.out.println("Enter 3 for Update Patient Details");
                        System.out.println("Enter 4 for Search Patient Details");
                        int patientOption = sc.nextInt();
                        System.out.println();

                        switch (patientOption) {
                            case 1:
                                sc.nextLine();
                                addPatients(sc, con);
                                break;
                            case 2:
                                deletePatients(sc, con);
                                break;
                            case 3:
                                updatePatients(sc, con);
                                break;
                            case 4:
                                selectPatients(sc, con);
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("Enter 1 for Add Appointment");
                        System.out.println("Enter 2 for Display Appointments");
                        int appointmentOption = sc.nextInt();
                        System.out.println();

                        switch (appointmentOption) {
                            case 1:
                                addAppointment(sc, con);
                                break;
                            case 2:
                                displayAppointments(con);
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("~~~~~~~~~~~~~~~~~~ Thank You ~~~~~~~~~~~~~~~~~~");
                        System.exit(0);
                        break;
                }
            }
        }

        public static void displayDoctor(Connection con) throws Exception {
            String sql = "SELECT * FROM doctors";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println("Name: " + rs.getString(2));
                System.out.println("Specialty: " + rs.getString(3));
                System.out.println("Contact Number: " + rs.getString(4));
                System.out.println("Date Of Joining: " + rs.getString(5));
                System.out.println();
            }
        }

        public static void addPatients(Scanner sc, Connection con) throws Exception {
            System.out.println("Enter name of Patient");
            String pname = sc.nextLine();
            System.out.println("Enter address of Patient");
            String address = sc.nextLine();
            System.out.println("Enter contact_no of Patient");
            String contact_no = sc.nextLine();
            System.out.println("Enter Age of Patient");
            int age = sc.nextInt();
            sc.nextLine();
        
            System.out.println("Choose the illness:");
            System.out.println("Enter 1 for heart diseases");
            System.out.println("Enter 2 for skin/hair diseases");
            System.out.println("Enter 3 for stomach or liver diseases");
            System.out.println("Enter 4 for childhood diseases");
            System.out.println("Enter 5 for bone diseases");
        
            int n = sc.nextInt();
            String illness = "";
        
            switch (n) {
                case 1:
                    illness = "Heart Diseases";
                    break;
                case 2:
                    illness = "Skin/Hair Diseases";
                    break;
                case 3:
                    illness = "Stomach or Liver Diseases";
                    break;
                case 4:
                    illness = "Childhood Diseases";
                    break;
                case 5:
                    illness = "Bone Diseases";
                    break;
                default:
                    System.out.println("Invalid illness choice");
                    return;
            }
        
            String sql = "INSERT INTO patients(p_name, address, contact_no, age, illness) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, pname);
            pst.setString(2, address);
            pst.setString(3, contact_no);
            pst.setInt(4, age);
            pst.setString(5, illness);
        
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Failed to add patient.");
            }
        }
        
        public static void deletePatients(Scanner sc, Connection con) throws Exception {
            System.out.println("Enter ID of patient you want to delete");
            int id = sc.nextInt();

            String sql = "DELETE FROM patients WHERE p_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Patient deleted successfully.");
            } else {
                System.out.println("Failed to delete patient.");
            }
        }
        public static void updatePatients(Scanner sc, Connection con) throws Exception {
            System.out.println("Enter ID of patient you want to update");
            int id = sc.nextInt();
            System.out.println("Enter what you want to update:");
            System.out.println("1. Update name");
            System.out.println("2. Update address");
            System.out.println("3. Update contact number");
            System.out.println("4. Update age");
            int choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice){
                case 1:
                    System.out.println("Enter new name of patient");
                    String new_name = sc.nextLine();
                    String sql = "UPDATE patients SET p_name = ? WHERE p_id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, new_name);
                    pst.setInt(2, id);
                    int result = pst.executeUpdate();
                    if (result > 0) {
                        System.out.println("Name updated successfully.");
                    } else {
                        System.out.println("Failed to update name.");
                    }
                    break;

                case 2:
                    System.out.println("Enter new address of patient");
                    String new_address = sc.nextLine();

                    String sql1 = "UPDATE patients SET address = ? WHERE p_id = ?";
                    PreparedStatement pst1 = con.prepareStatement(sql1);
                    pst1.setString(1, new_address);
                    pst1.setInt(2, id);

                    int result1 = pst1.executeUpdate();
                    if (result1 > 0) {
                        System.out.println("Address updated successfully.");
                    } else {
                        System.out.println("Failed to update address.");
                    }
                    break;

                case 3:
                    System.out.println("Enter new contact number of patient");
                    String new_contactno = sc.nextLine();

                    String sql2 = "UPDATE patients SET contact_no = ? WHERE p_id = ?";
                    PreparedStatement pst2 = con.prepareStatement(sql2);
                    pst2.setString(1, new_contactno);
                    pst2.setInt(2, id);

                    int result2 = pst2.executeUpdate();
                    if (result2 > 0) {
                        System.out.println("Contact number updated successfully.");
                    } else {
                        System.out.println("Failed to update contact number.");
                    }
                    break;

                case 4:
                    System.out.println("Enter new age of patient");
                    int new_age = sc.nextInt();

                    String sql3 = "UPDATE patients SET age = ? WHERE p_id = ?";
                    PreparedStatement pst3 = con.prepareStatement(sql3);
                    pst3.setInt(1, new_age);
                    pst3.setInt(2, id);

                    int result3 = pst3.executeUpdate();
                    if (result3 > 0) {
                        System.out.println("Age updated successfully.");
                    } else {
                        System.out.println("Failed to update age.");
                    }
                    break;
            }
        }

        public static void selectPatients(Scanner sc, Connection con) throws Exception {
            System.out.println("Enter ID of patient you want to see details");
            int id = sc.nextInt();

            String sql = "SELECT * FROM patients WHERE p_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("p_name"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Contact Number: " + rs.getString("contact_no"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Illness: " + rs.getString("illness"));
                System.out.println();
            }
        }

        public static void addAppointment(Scanner sc, Connection con) throws Exception {
            System.out.println("Enter Patient ID:");
            int patientId = sc.nextInt();
            String patientname="";
            String doctorname="";
            System.out.println("Enter Date (YYYY-MM-DD HH:MM:SS):");
            sc.nextLine();
            String appointmentDate = sc.nextLine();
            String doctorSpecialty="";
            String patientillness="";
            String sql="select illness,p_name from patients where p_id="+patientId+"";
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(sql);
            while(rs.next())
            {
                patientillness=rs.getString("illness");
                patientname=rs.getString("p_name");
            }
            if(patientillness.equalsIgnoreCase("Heart Diseases"))
            {
                doctorSpecialty="Cardiologist";
            }
            else if(patientillness.equalsIgnoreCase("Skin/Hair Diseases"))
            {
                doctorSpecialty="Dermatologist";
            }
            else if(patientillness.equalsIgnoreCase("Stomach or Liver Diseases"))
            {
                doctorSpecialty="Gastroenterologist";
            }
            else if(patientillness.equalsIgnoreCase("Childhood Diseases"))
            {
                doctorSpecialty="Pediatrician";
            }
            else if(patientillness.equalsIgnoreCase("Bone Diseases"))
            {
                doctorSpecialty="Orthopedic Surgeon";
            }
            
            String sql1 = "SELECT d_id,d_name FROM doctors WHERE d_speciality =?";
            PreparedStatement pst=con.prepareStatement(sql1);
            pst.setString(1, doctorSpecialty);
            ResultSet rs1=pst.executeQuery();
            while (rs1.next()) {
                int doctorId=rs1.getInt("d_id");
                doctorname=rs1.getString("d_name");
                String insertSql = "INSERT INTO appointments(p_id, d_id, date) VALUES (?, ?, ?)";
                PreparedStatement insertPst = con.prepareStatement(insertSql);
                insertPst.setInt(1, patientId);
                insertPst.setInt(2, doctorId);
                insertPst.setString(3, appointmentDate);
                
                int result = insertPst.executeUpdate();
                
                if (result > 0) {
                    System.out.println("Appointment added successfully.");
                } else {
                    System.out.println("Failed to add appointment.");
                }
            }
            FileWriter fw=new FileWriter("Appointment.txt",true);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write("Patient "+patientname+" Has Appointment with Dr."+doctorname+" at Date "+appointmentDate);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        
        public static void displayAppointments(Connection con) throws Exception {
            String sql = "SELECT * FROM appointments";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int patientId = rs.getInt("p_id");
                int doctorId = rs.getInt("d_id");
                String appointmentDate = rs.getString("date");

                System.out.println("Patient ID: " + patientId);
                System.out.println("Doctor ID: " + doctorId);
                System.out.println("Appointment Date: " + appointmentDate);
                System.out.println();
            }
        }
    }
    