package gr.aueb.cf.mobile_contacts.controler;

import gr.aueb.cf.mobile_contacts.core.serializer.Serializer;
import gr.aueb.cf.mobile_contacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobile_contacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobile_contacts.exceptions.ContactNotfoundException;
import gr.aueb.cf.mobile_contacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobile_contacts.mapper.Mapper;
import gr.aueb.cf.mobile_contacts.model.MobileContact;
import gr.aueb.cf.mobile_contacts.service.IMobileContactService;
import gr.aueb.cf.mobile_contacts.service.MobileContactServiceImpl;
import gr.aueb.cf.mobile_contacts.validation.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

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
                return "Error.\n" + "Validation error\n" + errorVector;
            }
            // If the validation is ok, insert contact
            mobileContact = service.insertMobileContact(insertDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO); // return to client if all are good and the Data as JASON form.
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error\n" + e.getMessage() + "\n";
        }
    }

    public String updateContact(MobileContactUpdateDTO updateDTO) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            //Validate input data
            String errorVector = ValidationUtil.validateDTO(updateDTO);
            if(!errorVector.isEmpty()) {
                return "Error.\n" + "Validation error\n" + errorVector;
            }
            // If the validation is ok, insert contact
            mobileContact = service.updateMobileContact(updateDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO); // return to client if all are good and the Data as JASON form.
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error\n" + e.getMessage() + "\n";
        } catch (ContactNotfoundException e) {
            return "Error\n" + e.getMessage() + "\n";
        }
    }

    public String deleteContactById(Long id) {
        try {
            service.deleteContactById(id);
            return "OK. The contact deleted.\n";
        } catch (ContactNotfoundException e) {
            return "Error\n Error during deletion. The contact not found." + e.getMessage() + "\n";
        }
    }

    public String getContactById(Long id) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getContactById(id);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (ContactNotfoundException e) {
            return "error. \n The contact not found \n";
        }
    }

    public List<String> getAllContacts() {
        List<MobileContact> contacts;
        List<String> serializedList = new ArrayList<>();
        MobileContactReadOnlyDTO readOnlyDTO;
        String serialized;

        contacts = service.getAllContacts();
        for (MobileContact contact : contacts) {
            readOnlyDTO = Mapper.mapMobileContactToDTO(contact);
            serialized = Serializer.serializeDTO(readOnlyDTO);
            serializedList.add(serialized);
        }

        return serializedList;
    }

    public String getContactByPhoneNumber(String phoneNumber) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getContactByPhoneNumber(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "OK\n" + Serializer.serializeDTO(readOnlyDTO);
        } catch (ContactNotfoundException e) {
            return "error. \n The contact not found \n";
        }
    }

//    public String deleteContactByPhoneNumber(String phoneNumber) {
//        try {
//            service.deleteContactByPhoneNumber(phoneNumber);
//            return "OK. The contact deleted.\n";
//        } catch (ContactNotfoundException e) {
//            return "Error\n Error during deletion. The contact not found." + e.getMessage() + "\n";
//        }
//    }

    //If we want to give in return more info than above.
    public String deleteContactByPhoneNumber(String phoneNumber) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;
        try {
            mobileContact = service.getContactByPhoneNumber(phoneNumber); // take the phone number
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact); //put this contact to read only dto
            service.deleteContactByPhoneNumber(phoneNumber); // make delete
            return "OK. The contact deleted" + Serializer.serializeDTO(readOnlyDTO); // return message and JASON
        } catch (ContactNotfoundException e) {
            return "Error\n Error during deletion. The contact not found." + e.getMessage() + "\n";
        }
    }



//    //Here we make serialization. We convert the instances to strings. JSON Strings
//    private String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
//        return "ID: " + readOnlyDTO.getId() + ", Name: " + readOnlyDTO.getFirstname() + ", Surname: " + readOnlyDTO.getLastname() +
//                ", Tel. Number: " + readOnlyDTO.getPhoneNumber();
//    }
}
