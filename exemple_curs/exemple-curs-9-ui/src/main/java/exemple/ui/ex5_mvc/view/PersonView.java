package exemple.ui.ex5_mvc.view;

/**
 * @author Radu Miron
 * @version 1
 */


import exemple.ui.ex5_mvc.model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PersonView extends JFrame {
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton submitButton;
    private JScrollPane personListPanel;

    public PersonView() {
        this.setTitle("Person List");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2,1));

        // setup the form for entering a person's information
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4,2));
        formPanel.add(new JLabel("ID:"));
        idField = new JTextField(10);
        formPanel.add(idField);
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(10);
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(10);
        formPanel.add(lastNameField);
        submitButton = new JButton("Submit");
        formPanel.add(submitButton);
        add(formPanel);

        // setup the panel for displaying the list of persons
        JList<Person> list = new JList<>();
        personListPanel = new JScrollPane(list);
        add(personListPanel);

        setVisible(true);
    }

    public String getId() {
        return idField.getText();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public void addSubmitListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void updatePersonList(List<Person> persons) {
        // add persons to the display
        JList<Person> personList = new JList<>();
        personList.setListData(persons.toArray(new Person[persons.size()]));

        //update the person list
        personListPanel.setViewportView(personList);
        personList.repaint();

        // repaint the display
        revalidate();
        repaint();
    }
}

