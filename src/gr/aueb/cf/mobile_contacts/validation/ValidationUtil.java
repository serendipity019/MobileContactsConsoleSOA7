package gr.aueb.cf.mobile_contacts.validation;

import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.dto.MobileContactUpdateDTO;

public class ValidationUtil {
    /**
     * No instances of this class should be available.
     */
    private ValidationUtil () {

    }

    // This is if the user try to insert
    public static String validateDTO(MobileContactInsertDTO insertDTO) {
        String errorResponse = "";

        if (insertDTO.getPhoneNumber().length() < 10) {
            errorResponse += "The telephone number must have ten ore more symbols. \n";
        }

        if (insertDTO.getFirstname().length() < 2) {
            errorResponse += "The firstname must have two or more characters. \n ";
        }

        if (insertDTO.getLastname().length() < 2) {
            errorResponse += "The lastname must have two or more characters. \n ";
        }

        return errorResponse;
    }

    // This is if the user try to update.
    public static String validateDTO(MobileContactUpdateDTO updateDTO) {
        String errorResponse = "";

        if (updateDTO.getPhoneNumber().length() < 10) {
            errorResponse += "The telephone number must have ten ore more symbols. \n";
        }

        if (updateDTO.getFirstname().length() < 2) {
            errorResponse += "The firstname must have two or more characters. \n ";
        }

        if (updateDTO.getLastname().length() < 2) {
            errorResponse += "The lastname must have two or more characters. \n ";
        }

        return errorResponse;
    }
}
