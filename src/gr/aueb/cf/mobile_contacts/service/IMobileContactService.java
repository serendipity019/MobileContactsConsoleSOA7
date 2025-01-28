package gr.aueb.cf.mobile_contacts.service;

import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobile_contacts.exceptions.ContactNotfoundException;
import gr.aueb.cf.mobile_contacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobile_contacts.model.MobileContact;

import java.util.List;

public interface IMobileContactService {

    MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistsException;

    MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws
            PhoneNumberAlreadyExistsException, ContactNotfoundException;

    void deleteContactById(Long id) throws ContactNotfoundException;
    MobileContact getContactById(Long id) throws ContactNotfoundException;
    List<MobileContact> getAllContacts();

}
