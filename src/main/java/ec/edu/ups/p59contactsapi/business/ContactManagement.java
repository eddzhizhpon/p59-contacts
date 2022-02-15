package ec.edu.ups.p59contactsapi.business;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import ec.edu.ups.p59contactsapi.dao.ContactDAO;
import ec.edu.ups.p59contactsapi.model.Contact;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

@Stateless
public class ContactManagement {
	
	private final String remoteHost = "192.168.100.111";
	private final String username = "p59contacts";
	private final String password = "Patito.123@456";
	private final String sharedFolder = "storage";
	
	@Inject
	private ContactDAO contactDAO;
	
	public void save(Contact contact) throws Exception {
		if (contactDAO.read(contact.getId()) != null )
			contactDAO.update(contact);
		else
			contactDAO.create(contact);
		
		updateFile();
	}
	
	public Contact read(int id) throws Exception {
		return contactDAO.read(id);
	}
	
	public void delete(Contact contact) throws Exception {
		contactDAO.delete(contact);
		updateFile();
	}
	
	public List<Contact> list() throws Exception {
		return contactDAO.list();
	}
	
	public SmbFileOutputStream connectNAS() throws Exception {
		String path = "smb://" + remoteHost + "/" + sharedFolder + "/contacts_list.json";
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", username, password);
		SmbFile smbFile = new SmbFile(path, auth);
		if (!smbFile.exists()) smbFile.createNewFile();
		return new SmbFileOutputStream(smbFile);
	    
	}
	
	public void updateFile() throws Exception {
		List<Contact> contactList = list();
		if (contactList == null) return;
		String json = JsonbBuilder.create().toJson(contactList);
		
		SmbFileOutputStream smbfos =  connectNAS();
	    smbfos.write(json.getBytes());
	    smbfos.close();
	}
	
	public File downloadFile() throws Exception {
		File temp = File.createTempFile("tmp-", GregorianCalendar.getInstance().getTimeInMillis() + ".tmp");
		temp.deleteOnExit();
		String path = "smb://" + remoteHost + "/" + sharedFolder + "/contacts_list.json";
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", username, password);
		SmbFile smbFile = new SmbFile(path, auth);
		if (!smbFile.exists()) smbFile.createNewFile();
		Files.copy(smbFile.getInputStream(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return temp;
	}
}
