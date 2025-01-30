package gr.aueb.cf.mobile_contacts.mapper;

import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobile_contacts.model.MobileContact;

public class Mapper {

    /**
     * No instance of this class should be available.
     */
    private Mapper() {

    }

    public static MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    public static MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    public static MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(), mobileContact.getLastname(), mobileContact.getPhoneNumber());
    }

}
