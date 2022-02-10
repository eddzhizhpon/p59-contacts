package ec.edu.ups.p59contactsapi.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.p59contactsapi.model.Contact;

@Stateless
public class ContactDAO {

	@PersistenceContext	
	private EntityManager em;
	
	public void create(Contact contact) throws Exception {
		em.persist(contact);
		em.flush();
	}
	
	public Contact read(int id) throws Exception {
		return em.find(Contact.class, id);
	}
	
	public void update(Contact contact) throws Exception {
		em.merge(contact);
		em.flush();
	}
	
	public void delete(Contact contact) throws Exception {
		em.remove(em.merge(contact));
	}
	
	public List<Contact> list() throws Exception {
		String sql = "SELECT c FROM Contact c";
		return em.createQuery(sql, Contact.class).getResultList();
	}
	
}
