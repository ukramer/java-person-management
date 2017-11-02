/*
 * Copyright (c) 2017, Ueli Kramer
 */

package com.uelikramer.personManagement;

import com.uelikramer.personManagement.factories.PersonFactory;
import com.uelikramer.personManagement.models.Country;
import com.uelikramer.personManagement.models.Person;
import com.uelikramer.personManagement.models.Sex;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * This class handles interactions with the view prepared with PersonManagement.fxml
 *
 * @author Ueli Kramer
 */
public class Controller {
    /**
     * The date format used for the logging textarea
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    /**
     * The css class to use for error fields
     */
    private static final String ERROR_CSS_CLASS = "error";
    /**
     * The list containing a variable amount of Person objects.
     * This list has been assigned to the ListView
     */
    private static ObservableList<Person> persons;
    /**
     * Observable list with countries
     */
    private static ObservableList<Country> countries;
    /**
     * Observable list with sexes
     */
    private static ObservableList<Sex> sexes;
    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private ComboBox<Country> countryInput;

    @FXML
    private TextField salaryInput;

    @FXML
    private TextField ageInput;

    @FXML
    private ComboBox<Sex> sexInput;

    @FXML
    private Button addBtn;

    @FXML
    private TextArea logTextArea;

    @FXML
    private Text statsMales;

    @FXML
    private Text statsFemales;

    @FXML
    private Text statsTotal;

    @FXML
    private Text statsTotalSalary;

    @FXML
    private Text statsAverageSalary;

    @FXML
    private Text statsTotalAge;

    @FXML
    private Text statsAverageAge;

    @FXML
    private ListView<Person> personListView;

    /**
     * Initialize mock data using a factory which generates random Person objects
     * After binding the data we also sort it
     * <p>
     * Will also generate observable
     */
    public Controller() {
        persons = FXCollections.observableArrayList(PersonFactory.getSomePersons());
        Collections.sort(persons);

        countries = FXCollections.observableArrayList(Country.values());
        sexes = FXCollections.observableArrayList(Sex.values());
    }

    /**
     * Initialize everything depending on the javafx view
     */
    public void initialize() {
        bindEvents();
        bindData();
        updateStats();
    }

    /**
     * Bind events
     */
    private void bindEvents() {
        // each time a Person object gets added or deleted, update the stats
        persons.addListener((ListChangeListener<? super Person>) c -> {
            updateStats();
        });
        // each modification of the value in age field should be checked to be numbers only
        ageInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Bind data to view
     */
    private void bindData() {
        // Set ObservableList as Items on ListView
        // thanks to ObservableList each modification will automatically be affected to the view.
        personListView.setItems(persons);

        // Fill combo boxes with countries and sexes
        countryInput.setItems(countries);
        sexInput.setItems(sexes);
    }

    /**
     * The add button has been clicked.
     * Validates the input data and will generate a new Person object.
     * After generating it will be filled with the data entered.
     * The newly created object gets added to the ObservableList which is assigned to the ListView.
     *
     * @param event Detailed information about the event
     */
    @FXML
    private void addPerson(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageInput.getText());
        } catch (NumberFormatException e) {
            age = 0;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryInput.getText());
        } catch (NumberFormatException e) {
            salary = 0;
        }

        Person p = new Person(
                age,
                salary,
                firstnameInput.getText(),
                lastnameInput.getText(),
                countryInput.getValue(),
                sexInput.getValue()
        );
        persons.add(p);

        showMessage("Person " + p.getFirstName() + " " + p.getLastName() + " added successfully!");

        Collections.sort(persons);
        clearFields();
    }

    private boolean validateInputs() {
        boolean isValid = true;
        if (firstnameInput.getText().isEmpty()) {
            showMessage("ERROR: Please enter a value for 'firstname'!");
            firstnameInput.getStyleClass().add(ERROR_CSS_CLASS);
            isValid = false;
        } else {
            firstnameInput.getStyleClass().remove(ERROR_CSS_CLASS);
        }
        if (lastnameInput.getText().isEmpty()) {
            showMessage("ERROR: Please enter a value for 'lastname'!");
            lastnameInput.getStyleClass().add(ERROR_CSS_CLASS);
            isValid = false;
        } else {
            lastnameInput.getStyleClass().remove(ERROR_CSS_CLASS);
        }

        try {
            int age = Integer.parseInt(ageInput.getText());
            ageInput.getStyleClass().remove(ERROR_CSS_CLASS);
        } catch (NumberFormatException e) {
            showMessage("ERROR: Please enter a valid value for 'age'!");
            ageInput.getStyleClass().add(ERROR_CSS_CLASS);
            isValid = false;
        }

        try {
            double salary = Double.parseDouble(salaryInput.getText());
            salaryInput.getStyleClass().remove(ERROR_CSS_CLASS);
        } catch (NumberFormatException e) {
            showMessage("ERROR: Please enter a valid value for 'salary'!");
            salaryInput.getStyleClass().add(ERROR_CSS_CLASS);
            isValid = false;
        }

        if (countryInput.getValue() == null) {
            showMessage("ERROR: Please choose a 'country'!");
            countryInput.getStyleClass().add(ERROR_CSS_CLASS);
            isValid = false;
        } else {
            countryInput.getStyleClass().remove(ERROR_CSS_CLASS);
        }

        if (sexInput.getValue() == null) {
            showMessage("ERROR: Please choose a 'sex'!");
            sexInput.getStyleClass().add(ERROR_CSS_CLASS);
            isValid = false;
        } else {
            sexInput.getStyleClass().remove(ERROR_CSS_CLASS);
        }

        return isValid;
    }

    /**
     * This method gets invoked as soon as all fields have been used to generate a new Person object.
     * It clears all the input fields.
     */
    private void clearFields() {
        ageInput.clear();
        salaryInput.clear();
        firstnameInput.clear();
        lastnameInput.clear();
        countryInput.getSelectionModel().clearSelection();
        sexInput.getSelectionModel().clearSelection();
    }

    /**
     * Each time a Person gets clicked in the ListView, it will get deleted from the ObservableList
     *
     * @param event Detailed information about the event
     */
    @FXML
    private void deletePerson(MouseEvent event) {
        Person p = personListView.getSelectionModel().getSelectedItem();
        if (p == null) {
            return; // skip if an empty row has been clicked
        }

        showMessage("Person " + p.getFirstName() + " " + p.getLastName() + " removed successfully!");
        persons.remove(p);
        personListView.getSelectionModel().clearSelection(); // clear selection
    }

    /**
     * Display log Message
     *
     * @param message The message to be displayed
     */
    private void showMessage(String message) {
        logTextArea.setText(sdf.format(new Date()) + ": " + message + System.lineSeparator() + logTextArea.getText());
    }

    /**
     * Updates the statistics
     */
    private void updateStats() {
        int countMales = 0;
        int countFemales = 0;
        double salaryTotal = 0;
        int ageTotal = 0;
        for (Person p : persons) {
            if (p.getSex() == Sex.m) {
                countMales++;
            } else {
                countFemales++;
            }
            salaryTotal += p.getSalary();
            ageTotal += p.getAge();
        }

        statsMales.setText("" + countMales);
        statsFemales.setText("" + countFemales);
        statsTotal.setText("" + persons.size());
        statsTotalSalary.setText(String.format("%.2f", salaryTotal));
        statsAverageSalary.setText(String.format("%.2f", salaryTotal / persons.size()));
        statsTotalAge.setText("" + ageTotal);
        statsAverageAge.setText("" + ageTotal / persons.size());
    }
}
