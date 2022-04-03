package guifx;

import application.controller.Controller;
import application.model.Company;
import application.model.Customer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class CompanyxCustomerWindow extends Stage {
    // Field variabler
    private Company company;
    private ListView<Company> lvwCompanies;

    /**
     * Constructor til at oprette en customer window
     * @param title
     */
    public CompanyxCustomerWindow(String title) {
        this(title, null);
    }
    public CompanyxCustomerWindow(String title, Company company) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.company = company;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }


    // -------------------------------------------------------------------------

    private TextField txfName;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblName = new Label("Name");
        pane.add(lblName, 0, 0);

        txfName = new TextField();
        pane.add(txfName, 0, 1);
        txfName.setPrefWidth(200);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 4);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 4);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        lblError = new Label();
        pane.add(lblError, 0, 5);
        lblError.setStyle("-fx-text-fill: red");

        //Lister alle firmaerne så vi kan vælge hvor kunden skal passe til
        lvwCompanies = new ListView<>();
        pane.add(lvwCompanies, 0, 2);
        lvwCompanies.setPrefWidth(200);
        lvwCompanies.setPrefHeight(300);
        lvwCompanies.getItems().setAll(Controller.getCompanies());

        this.initControls();
    }

    private void initControls() {
        if (company != null) {
            txfName.setText(company.getName());
        } else {
            txfName.clear();
        }
    }

    // -------------------------------------------------------------------------

    private void cancelAction() {
        this.hide();
    }

    // Når der bliver trykket ok
    private void okAction() {
        String name = txfName.getText().trim();

        // Vælger det firma der er blevet valgt i vores listview
        Company company = lvwCompanies.getSelectionModel().getSelectedItem();

        // Tjekker efter om navnet er større end 0 for at der ikke skal kunne komme korte navne ind
        if (name.length() == 0) {
            lblError.setText("Name is empty");
        } else{
            // Bruger vores controller metoder til at lave en customer og tilføje til firma
            Customer c1 = Controller.CreateCustomer(name);
            Controller.addCustomerToCompany(c1,company);
        }
        // Gemmer dialogvinduet igen
        this.hide();
    }
}
