package ec.edu.ups.p59contactsapi.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ec.edu.ups.p59contactsapi.dao.ContactDAO;
import ec.edu.ups.p59contactsapi.model.Contact;

@Stateless
public class ContactManagement {
	
	@Inject
	private ContactDAO contactDAO;
	
	public void save(Contact contact) throws Exception {
		if (contactDAO.read(contact.getId()) != null )
			contactDAO.update(contact);
		else
			contactDAO.create(contact);
	}
	
	public Contact read(int id) throws Exception {
		return contactDAO.read(id);
	}
	
	public void delete(Contact contact) throws Exception {
		contactDAO.delete(contact);
	}
	
	public List<Contact> list() throws Exception {
		return contactDAO.list();
	}
}
