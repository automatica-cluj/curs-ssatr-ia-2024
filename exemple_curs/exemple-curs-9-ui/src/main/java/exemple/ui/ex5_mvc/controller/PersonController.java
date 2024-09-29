package exemple.ui.ex5_mvc.controller;

/**
 * @author Radu Miron
 * @version 1
 */


import exemple.ui.ex5_mvc.model.PersonModel;
import exemple.ui.ex5_mvc.view.PersonView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonController implements ActionListener {
    private PersonModel model;
    private PersonView view;

    public PersonController(PersonModel model, PersonView view) {
        this.model = model;
        this.view = view;
        view.addSubmitListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String id = view.getId();
        String firstName = view.getFirstName();
        String lastName = view.getLastName();
        model.addPerson(Integer.parseInt(id), firstName, lastName);
        view.updatePersonList(model.getPersons());
    }
}

