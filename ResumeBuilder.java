package com.ResumeBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileOutputStream;
import java.sql.*;

public class ResumeBuilder extends Application {
    private TextField nameField, emailField, phoneField;
    private TextArea introField, educationField, skillsField, experienceField, projectsField;
    private Button saveButton, generatePdfButton;

    private static final String URL = "jdbc:mysql://localhost:3306/resume_db";
    private static final String USER = "root";
    private static final String PASSWORD = "2496";

    @Override
    public void start(Stage primaryStage) {
        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label phoneLabel = new Label("Phone:");
        phoneField = new TextField();

        Label introLabel = new Label("Introduction:");
        this.introField = new TextArea();

        Label educationLabel = new Label("Education:");
        this.educationField = new TextArea();

        Label skillsLabel = new Label("Skills:");
        skillsField = new TextArea();

        Label experienceLabel = new Label("Experience:");
        experienceField = new TextArea();

        Label projectsLabel = new Label("Projects:"); 
        this.projectsField = new TextArea();          

        saveButton = new Button("Save to Database");
        generatePdfButton = new Button("Generate PDF");

        saveButton.setOnAction(e -> saveToDatabase());
        generatePdfButton.setOnAction(e -> generatePDF());

        VBox root = new VBox(10, 
            nameLabel, nameField, emailLabel, emailField, phoneLabel, phoneField, 
            introLabel, introField, educationLabel, educationField, 
            skillsLabel, skillsField, experienceLabel, experienceField,
            projectsLabel, projectsField, 
            saveButton, generatePdfButton
        );

        Scene scene = new Scene(root, 400, 550);
        primaryStage.setTitle("Resume Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveToDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO resumes (name, email, phone, introduction, education, skills, experience, projects) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, nameField.getText());
            stmt.setString(2, emailField.getText());
            stmt.setString(3, phoneField.getText());
            stmt.setString(4, introField.getText());  
            stmt.setString(5, educationField.getText());  
            stmt.setString(6, skillsField.getText());
            stmt.setString(7, experienceField.getText());
            stmt.setString(8, projectsField.getText()); 

            stmt.executeUpdate();
            showAlert("Success", "Resume saved to database!");
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void generatePDF() {
        try {
            String filePath = System.getProperty("user.home") + "/Desktop/resume.pdf";
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            document.add(new Paragraph("Resume")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            // Name Section
            document.add(new Paragraph("\n" + nameField.getText())
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            // Contact Details
            document.add(new Paragraph("Email: " + emailField.getText()));
            document.add(new Paragraph("Phone: " + phoneField.getText()));

            // Separator Line
            document.add(new Paragraph("\n-------------------------------------------------------------\n"));

            // Introduction Section
            document.add(new Paragraph("Introduction")
                    .setBold()
                    .setFontSize(14)
                    .setUnderline());
            document.add(new Paragraph(introField.getText()));

            // Education Section
            document.add(new Paragraph("\nEducation")
                    .setBold()
                    .setFontSize(14)
                    .setUnderline());
            document.add(new Paragraph(educationField.getText()));

            // Skills Section
            document.add(new Paragraph("\nSkills")
                    .setBold()
                    .setFontSize(14)
                    .setUnderline());
            document.add(new Paragraph(skillsField.getText()));

            // Experience Section
            document.add(new Paragraph("\nExperience")
                    .setBold()
                    .setFontSize(14)
                    .setUnderline());
            document.add(new Paragraph(experienceField.getText()));

            // Projects Section
            document.add(new Paragraph("\nProjects")  // ✅ Added Projects Section
                    .setBold()
                    .setFontSize(14)
                    .setUnderline());
            document.add(new Paragraph(projectsField.getText()));

            // Footer
            document.add(new Paragraph("\nGenerated using Resume Builder").setFontSize(10));

            document.close();
            showAlert("Success", "PDF Generated Successfully at " + filePath);
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
