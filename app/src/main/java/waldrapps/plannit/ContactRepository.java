package waldrapps.plannit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepository {
    
    private ContactDoa contactDoa;
    private LiveData<List<Contact>> allContacts;

    ContactRepository(Application application) {
        ContactDatabaseSingleton db = ContactDatabaseSingleton.getDatabase(application);
        contactDoa = db.contactDoa();
        allContacts = contactDoa.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact) {
        new insertAsyncTask(contactDoa).execute(contact);
    }

    public Contact getContactById(int id) {
        return contactDoa.getContactById(id);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDoa asyncTaskDao;

        insertAsyncTask(ContactDoa dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
