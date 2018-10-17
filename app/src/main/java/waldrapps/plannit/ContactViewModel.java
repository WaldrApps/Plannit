package waldrapps.plannit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    
    private ContactRepository repository;

    private LiveData<List<Contact>> allContacts;

    public ContactViewModel (Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() { return allContacts; }

    public void insert(Contact contact) { repository.insert(contact); }

    public Contact getContactById(int id) { return repository.getContactById(id); }
}
