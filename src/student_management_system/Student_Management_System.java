package student_management_system;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


public class Student_Management_System extends JFrame {
    private JTextField txtName ,txtAge,txtCourse,txtId;
    private JButton btnAdd ,btnView , btnUpdate, btnDelete;
    private int selectedStudentId = -1;
    
    public Student_Management_System() {
        setTitle("System_Managament_System");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,20)); 

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(10);
        txtId.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtId.getPreferredSize().height));
        panel1.add(lblId);
        panel1.add(txtId);
        
        lblId.setVisible(false);
        txtId.setVisible(false);
               
        JLabel label1 = new JLabel("Name:");
        txtName = new JTextField(10);
        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtName.getPreferredSize().height));
        panel1.add(label1);
        panel1.add(txtName);

        JLabel label2 = new JLabel("Age:");
        txtAge = new JTextField(10);
        txtAge.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtAge.getPreferredSize().height));
        panel1.add(label2);
        panel1.add(txtAge);

        JLabel label3 = new JLabel("Course:");
        txtCourse = new JTextField(10);
        txtCourse.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtCourse.getPreferredSize().height));
        panel1.add(label3);
        panel1.add(txtCourse);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));        
        btnAdd = new JButton("Add student");
        btnView = new JButton("View students");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        
       
        panel1.add(Box.createRigidArea(new Dimension(0, 15))); 
        panel1.add(panel2);
        
        panel2.add(btnAdd);
        panel2.add(btnView);
        panel2.add(btnUpdate);
        panel2.add(btnDelete);
        
        add(panel1, BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);
        
        StudentDAO dao = new StudentDAO();
        
        btnAdd.addActionListener(e -> {
            
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String course = txtCourse.getText();
            
            dao.addStudent(name, age ,course);
    });

        btnView.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter first letter (or leave blank for all):");
             List<StudentEntity> students;

            if(input != null && !input.trim().isEmpty()) {
            students = dao.getStudentsByFirstLetter(input.trim().charAt(0));
            }
            else {
                students = dao.getStudents();
            }

            String[] columnNames = {"ID", "Name", "Age", "Course"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (StudentEntity s : students) {
                Object[] row = { s.getStudentId(), s.getName(), s.getAge(), s.getCourse() 
            };
                model.addRow(row);
            }
            JTable table = new JTable(model);

            table.getSelectionModel().addListSelectionListener(event -> {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    selectedStudentId = (int) table.getValueAt(table.getSelectedRow(), 0); 
                    txtName.setText((String) table.getValueAt(table.getSelectedRow(), 1));
                    txtAge.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                    txtCourse.setText((String) table.getValueAt(table.getSelectedRow(), 3));
               }
            });
                JScrollPane scrollPane = new JScrollPane(table);
                JOptionPane.showMessageDialog(this, scrollPane, "Student Records", JOptionPane.PLAIN_MESSAGE);
            });

        btnUpdate.addActionListener(e -> {
            if (selectedStudentId != -1) {
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());
                String course = txtCourse.getText();

                dao.updateStudent(selectedStudentId, name, age, course);
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.");
            }
        });
        btnDelete.addActionListener(e -> {
            if (selectedStudentId != -1) {
                int choice = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this student?","Confirm Deletion",
                JOptionPane.YES_NO_OPTION
           );
            if (choice == JOptionPane.YES_OPTION) {
                dao.deleteStudent(selectedStudentId);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            }
            }
        else {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.");
        }
        });
        
        setVisible(true);
        }         
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Student_Management_System());
       
}
}