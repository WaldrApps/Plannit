package waldrapps.plannit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository {
    
    private ContactDao contactDao;
    private EventDao eventDao;
    private LiveData<List<Contact>> allContacts;
    private LiveData<List<Event>> allEvents;

    public Repository(Application application) {
        DatabaseSingleton db = DatabaseSingleton.getDatabase(application);
        contactDao = db.contactDao();
        eventDao = db.eventDao();
        allContacts = contactDao.getAllContacts();
        allEvents = eventDao.getAllEvents();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public void deleteContact(Contact contact) {
        AsyncTask.execute(() -> contactDao.delete(contact));
    }

    public void insertContact(Contact contact) {
        new insertAsyncContactTask(contactDao).execute(contact);
    }

    public Contact getContactById(int id) {
        return contactDao.getContactById(id);
    }

    private static class insertAsyncContactTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao asyncTaskDao;

        insertAsyncContactTask(ContactDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insertEvent(Event event) {
        new insertAsyncEventTask(eventDao).execute(event);
    }

    public Event getEventById(int id) {
        return eventDao.getEventById(id);
    }

    public LiveData<List<Event>> getEventsByContactAndDay(String contactId, int dayOfWeek) {
        return eventDao.getEventsByContactAndDay(contactId, dayOfWeek);
    }

    private static class insertAsyncEventTask extends AsyncTask<Event, Void, Void> {

        private EventDao asyncTaskDao;

        insertAsyncEventTask(EventDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
