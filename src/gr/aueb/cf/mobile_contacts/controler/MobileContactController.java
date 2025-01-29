package gr.aueb.cf.mobile_contacts.controler;

import gr.aueb.cf.mobile_contacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobile_contacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobile_contacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobile_contacts.model.MobileContact;
import gr.aueb.cf.mobile_contacts.service.IMobileContactService;
import gr.aueb.cf.mobile_contacts.service.MobileContactServiceImpl;
import gr.aueb.cf.mobile_contacts.validation.ValidationUtil;

/*
 * The controller is this that create the instances and connect(wire) the DAO with the Services.
 */
public class MobileContactController {

    //We made these below final because we want to ensure this don't change from anyone.
    private final IMobileContactDAO dao = new MobileContactDAOImpl();
    private final IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact(MobileContactInsertDTO insertDTO) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            //Validate input data
            String errorVector = ValidationUtil.validateDTO(insertDTO);
            if(!errorVector.isEmpty()) {
                return "Error." + "Validation error\n" + errorVector;
            }
            // If the validation is ok, insert contact
            mobileContact = service.insertMobileContact(insertDTO);
            readOnlyDTO = mapMobileContactToDTO(mobileContact);
            return "OK\n" + serializeDTO(readOnlyDTO); // return to client if all are good and the Data as JASON form.
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error\n" + e.getMessage() + "\n";
        }
    }

    private MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(), mobileContact.getLastname(), mobileContact.getPhoneNumber());
    }

    //Here we make serialization. We convert the instances to strings. JSON Strings
    private String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
        return "ID: " + readOnlyDTO.getId() + ", Name: " + readOnlyDTO.getFirstname() + ", Surname: " + readOnlyDTO.getLastname() +
                ", Tel. Number: " + readOnlyDTO.getPhoneNumber();
    }
}
