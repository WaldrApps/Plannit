package waldrapps.plannit.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import waldrapps.plannit.Contact;
import waldrapps.plannit.Repository;

public class ContactViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<Contact>> allContacts;

    public ContactViewModel (Application application) {
        super(application);
        repository = new Repository(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() { return allContacts; }

    public void insert(Contact contact) { repository.insertContact(contact); }

    public Contact getContactById(int id) { return repository.getContactById(id); }
}
