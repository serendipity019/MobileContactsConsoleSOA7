package gr.aueb.cf.mobile_contacts.service;

import gr.aueb.cf.mobile_contacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobile_contacts.exceptions.ContactNotfoundException;
import gr.aueb.cf.mobile_contacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobile_contacts.model.MobileContact;

import java.util.List;

public class MobileContactServiceImpl implements IMobileContactService {

    private final IMobileContactDAO dao;

    //Constructor dependency injection
    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistsException {

        MobileContact mobileContact;
        try {
            if (dao.phoneNumberExists(dto.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number " + dto.getPhoneNumber() + " already exists.");
            }
            mobileContact = mapInsertDTOToContact(dto);

            System.err.printf("MobileContactServiceImpl Logger: %s was insert. \n", mobileContact);
            return dao.insert(mobileContact);
        } catch (PhoneNumberAlreadyExistsException e) {
            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s already exists. \n", dto.getPhoneNumber());
            throw e;
        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws PhoneNumberAlreadyExistsException, ContactNotfoundException {

       MobileContact mobileContact;
       MobileContact newContact;
        try {
            if(!dao.userIdExists(dto.getId())) {
                throw new ContactNotfoundException("Contact with id: " + dto.getId() + " not found for update.");
            }

            mobileContact = dao.getById(dto.getId());
            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNumber().equals(dto.getPhoneNumber());
            boolean isPhoneNumberExists = dao.phoneNumberExists(dto.getPhoneNumber());

            if (isPhoneNumberExists && !isPhoneNumberOurOwn) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number: " + dto.getPhoneNumber() + " already exists and cannot be updated");
            }

            newContact = mapUpdateDTOToContact(dto); // here did happen something and i chose autocorrect
            System.err.printf("MobileContactServiceImpl Logger: %s was updated with new info: %s\n", mobileContact, newContact);
            return dao.update(dto.getId(), newContact);
        } catch (ContactNotfoundException | PhoneNumberAlreadyExistsException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n ", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotfoundException {
        try {
            if (!dao.userIdExists(id)) {
                throw new ContactNotfoundException("Contact with id: " + id + " not found for delete.");
            }

            System.err.println("MobileContactServiceImpl Logger: contact with id: " + id + " was deleted.");

            dao.deleteById(id);
        } catch (ContactNotfoundException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n", e.getMessage());
            throw e;
        }
    }

    @Override
    public MobileContact getContactById(Long id) throws ContactNotfoundException {
        MobileContact mobileContact;

        try {
            mobileContact = dao.getById(id);
            if(mobileContact == null) {
                throw new ContactNotfoundException("Contact with id: " + id + " not found.");
            }
            return mobileContact;
        } catch (ContactNotfoundException e) {
            System.err.printf("Contact with id: %s was not found to get returned\n", id);
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
        return dao.getAll();
    }

    public MobileContact getContactByPhoneNumber(String phoneNumber) throws ContactNotfoundException {
        MobileContact mobileContact;

        try {
            mobileContact = dao.getByPhoneNumber(phoneNumber);
            if(mobileContact == null) {
                throw new ContactNotfoundException("Contact with phone number: " + phoneNumber + " not found.");
            }
            return mobileContact;
        } catch (ContactNotfoundException e) {
            System.err.printf("Contact with phone number: %s was not found to get returned\n", phoneNumber);
            throw e;
        }
    }

    public void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotfoundException {
        try {
            if (!dao.phoneNumberExists(phoneNumber)) {
                throw new ContactNotfoundException("Contact with id: " + phoneNumber + " not found for delete.");
            }

            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s was deleted.\n", phoneNumber);

            dao.deleteByPhoneNumber(phoneNumber);
        } catch (ContactNotfoundException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n", e.getMessage());
            throw e;
        }
    }


    private MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    private MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }
}
