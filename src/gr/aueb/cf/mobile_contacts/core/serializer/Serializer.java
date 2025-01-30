package gr.aueb.cf.mobile_contacts.core.serializer;

import gr.aueb.cf.mobile_contacts.dto.MobileContactReadOnlyDTO;

public class Serializer {
    /**
     * No instance of this class should be available.
     */
    private Serializer() {

    }

    //Here we make serialization. We convert the instances to strings. JSON Strings
    public static String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
        return "ID: " + readOnlyDTO.getId() + ", Name: " + readOnlyDTO.getFirstname() + ", Surname: " + readOnlyDTO.getLastname() +
                ", Tel. Number: " + readOnlyDTO.getPhoneNumber();
    }
}
