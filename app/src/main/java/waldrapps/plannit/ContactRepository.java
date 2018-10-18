package waldrapps.plannit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepository {
    
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    ContactRepository(Application application) {
        ContactDatabaseSingleton db = ContactDatabaseSingleton.getDatabase(application);
        contactDao = db.contactDoa();
        allContacts = contactDao.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact) {
        new insertAsyncTask(contactDao).execute(contact);
    }

    public Contact getContactById(int id) {
        return contactDao.getContactById(id);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao asyncTaskDao;

        insertAsyncTask(ContactDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
