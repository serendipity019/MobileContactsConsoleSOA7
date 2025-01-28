package gr.aueb.cf.mobile_contacts.dao;

import gr.aueb.cf.mobile_contacts.model.MobileContact;

import java.util.ArrayList;
import java.util.List;

public class MobileContactDAOImpl implements IMobileContactDAO {
    //We use the List<> as data source
    private static final List<MobileContact> contacts = new ArrayList<>();
    private static Long id = 1L;

    @Override
    public MobileContact insert(MobileContact mobileContact) {
        mobileContact.setId(id++); // simulate the auto increment of data bases.
        contacts.add(mobileContact); // The add is from these API where give us the Java.
        return mobileContact;
    }

    @Override
    public MobileContact update(Long id, MobileContact mobileContact) {
        contacts.set(getIndexById(id), mobileContact);
        return mobileContact;
    }

    @Override
    public void deleteById(Long id) {
        //contacts.remove(getIndexById());
        contacts.removeIf(contact -> contact.getId().equals(id)); // with this logic we don't need to use For
    }

    @Override
    public MobileContact getById(Long id) {
        int positionToReturn = getIndexById(id);
        return (positionToReturn != -1) ? contacts.get(positionToReturn) : null;
    }

    @Override
    public List<MobileContact> getAll() {

        return new ArrayList<>(contacts); // with this we have immutability. For this reason we used new < >
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        contacts.removeIf(contact -> contact.getPhoneNumber().equals(phoneNumber));
    }

    @Override
    public MobileContact getByPhoneNumber(String phoneNumber) {
        int positionToReturn = getIndexByPhoneNumber(phoneNumber);
        return (positionToReturn != -1) ? contacts.get(positionToReturn) : null;
    }

    @Override
    public boolean userIdExists(Long id) {
        int position = getIndexById(id);
        return position != -1;
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        int position = getIndexByPhoneNumber(phoneNumber);
        return position != -1;
    }

    //This is extra method because need for the app to check if exists .
    private int getIndexById(Long id) {
        int positionToReturn = -1;
        for (int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).getId().equals(id)){
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
    }
    //Also like above
    private int getIndexByPhoneNumber(String phoneNumber) {
        int positionToReturn = -1;
        for (int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).getPhoneNumber().equals(phoneNumber)){
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
    }
}
