import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Patient {
  private static int idCounter = 1;
  private int id;
  private String name;
  private int age;
  private String gender;

  public Patient(String name, int age, String gender) {
    this.id = idCounter++;
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }
}

class Doctor {
  private static int idCounter = 1;
  private int id;
  private String name;
  private String specialty;

  public Doctor(String name, String specialty) {
    this.id = idCounter++;
    this.name = name;
    this.specialty = specialty;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSpecialty() {
    return specialty;
  }
}

class Appointment {
  private Patient patient;
  private Doctor doctor;
  private String date;

  public Appointment(Patient patient, Doctor doctor, String date) {
    this.patient = patient;
    this.doctor = doctor;
    this.date = date;
  }

  public Patient getPatient() {
    return patient;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public String getDate() {
    return date;
  }
}

public class HospitalManagementGUI {
  private static ArrayList<Patient> patients = new ArrayList<>();
  private static ArrayList<Doctor> doctors = new ArrayList<>();
  private static ArrayList<Appointment> appointments = new ArrayList<>();

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new HospitalManagementGUI().createAndShowGUI());
  }

  private void createAndShowGUI() {
    JFrame frame = new JFrame("Hospital Management System");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);
    frame.setLayout(new GridLayout(3, 2));

    JButton btnAddPatient = new JButton("Add Patient");
    JButton btnAddDoctor = new JButton("Add Doctor");
    JButton btnScheduleAppointment = new JButton("Schedule Appointment");
    JButton btnViewPatients = new JButton("View Patients");
    JButton btnViewDoctors = new JButton("View Doctors");
    JButton btnViewAppointments = new JButton("View Appointments");

    btnAddPatient.addActionListener(e -> addPatient());
    btnAddDoctor.addActionListener(e -> addDoctor());
    btnScheduleAppointment.addActionListener(e -> scheduleAppointment());
    btnViewPatients.addActionListener(e -> viewPatients());
    btnViewDoctors.addActionListener(e -> viewDoctors());
    btnViewAppointments.addActionListener(e -> viewAppointments());

    frame.add(btnAddPatient);
    frame.add(btnAddDoctor);
    frame.add(btnScheduleAppointment);
    frame.add(btnViewPatients);
    frame.add(btnViewDoctors);
    frame.add(btnViewAppointments);

    frame.setVisible(true);
  }

  private void addPatient() {
    JTextField nameField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField genderField = new JTextField();

    Object[] fields = {
        "Enter Name:", nameField,
        "Enter Age:", ageField,
        "Enter Gender:", genderField
    };

    int option = JOptionPane.showConfirmDialog(null, fields, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      String name = nameField.getText();
      int age = Integer.parseInt(ageField.getText());
      String gender = genderField.getText();

      Patient patient = new Patient(name, age, gender);
      patients.add(patient);
      JOptionPane.showMessageDialog(null, "✅ Patient added successfully! ID: " + patient.getId());
    }
  }

  private void addDoctor() {
    JTextField nameField = new JTextField();
    JTextField specialtyField = new JTextField();

    Object[] fields = {
        "Enter Name:", nameField,
        "Enter Specialty:", specialtyField
    };

    int option = JOptionPane.showConfirmDialog(null, fields, "Add Doctor", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      String name = nameField.getText();
      String specialty = specialtyField.getText();

      Doctor doctor = new Doctor(name, specialty);
      doctors.add(doctor);
      JOptionPane.showMessageDialog(null, "✅ Doctor added successfully! ID: " + doctor.getId());
    }
  }

  private void scheduleAppointment() {
    JTextField patientIdField = new JTextField();
    JTextField doctorIdField = new JTextField();
    JTextField dateField = new JTextField();

    Object[] fields = {
        "Enter Patient ID:", patientIdField,
        "Enter Doctor ID:", doctorIdField,
        "Enter Appointment Date (YYYY-MM-DD):", dateField
    };

    int option = JOptionPane.showConfirmDialog(null, fields, "Schedule Appointment", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      int patientId = Integer.parseInt(patientIdField.getText());
      int doctorId = Integer.parseInt(doctorIdField.getText());
      String date = dateField.getText();

      Patient patient = findPatientById(patientId);
      Doctor doctor = findDoctorById(doctorId);

      if (patient != null && doctor != null) {
        Appointment appointment = new Appointment(patient, doctor, date);
        appointments.add(appointment);
        JOptionPane.showMessageDialog(null, "✅ Appointment scheduled successfully!");
      } else {
        JOptionPane.showMessageDialog(null, "❌ Invalid Patient ID or Doctor ID.");
      }
    }
  }

  private void viewPatients() {
    String[] columnNames = { "ID", "Name", "Age", "Gender" };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    for (Patient patient : patients) {
      model.addRow(new Object[] { patient.getId(), patient.getName(), patient.getAge(), patient.getGender() });
    }

    showTable("Patients List", model);
  }

  private void viewDoctors() {
    String[] columnNames = { "ID", "Name", "Specialty" };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    for (Doctor doctor : doctors) {
      model.addRow(new Object[] { doctor.getId(), doctor.getName(), doctor.getSpecialty() });
    }

    showTable("Doctors List", model);
  }

  private void viewAppointments() {
    String[] columnNames = { "Patient", "Doctor", "Date" };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    for (Appointment appointment : appointments) {
      model.addRow(new Object[] { appointment.getPatient().getName(), appointment.getDoctor().getName(),
          appointment.getDate() });
    }

    showTable("Appointments List", model);
  }

  private void showTable(String title, DefaultTableModel model) {
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);
    JFrame frame = new JFrame(title);
    frame.setSize(500, 300);
    frame.add(scrollPane);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private Patient findPatientById(int id) {
    for (Patient patient : patients) {
      if (patient.getId() == id) {
        return patient;
      }
    }
    return null;
  }

  private Doctor findDoctorById(int id) {
    for (Doctor doctor : doctors) {
      if (doctor.getId() == id) {
        return doctor;
      }
    }
    return null;
  }
}