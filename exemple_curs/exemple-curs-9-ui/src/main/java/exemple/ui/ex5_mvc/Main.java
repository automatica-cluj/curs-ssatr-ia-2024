package exemple.ui.ex5_mvc;

import exemple.ui.ex5_mvc.controller.PersonController;
import exemple.ui.ex5_mvc.model.PersonModel;
import exemple.ui.ex5_mvc.view.PersonView;

/**
 * @author Radu Miron
 * @version 1
 */
public class Main {
    public static void main(String[] args) {
        PersonModel personModel = new PersonModel();
        PersonView personView = new PersonView();
        PersonController personController = new PersonController(personModel, personView);
    }
}
