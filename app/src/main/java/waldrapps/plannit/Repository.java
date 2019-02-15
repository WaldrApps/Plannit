package waldrapps.plannit;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository {
    
    private ContactDao contactDao;
    private EventDao eventDao;
    private LiveData<List<Contact>> allContacts;
    private LiveData<List<Event>> allEvents;

    public interface OnTaskCompleted{
        void onTaskCompleted(List<Event> events);
    }

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

    @SuppressLint("StaticFieldLeak")
    public void deleteAllContacts() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                contactDao.deleteAllContacts();
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteAllEvents() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                eventDao.deleteAllEvents();
                return null;
            }
        }.execute();
    }

    public void insertContact(Contact contact) {
        new insertAsyncContactTask(contactDao).execute(contact);
    }

    public Contact getContactById(String id) {
        return contactDao.getContactById(id);
    }

    public LiveData<List<Event>> getEventsByContactIDLive(String id) {
        return eventDao.getEventsByContactIDLive(id);
    }

    public void getEventsByContactID(String id, OnTaskCompleted onTaskCompleted) {
        new getEventsByContactIDAsyncTask(eventDao, onTaskCompleted).execute(id);
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

    public LiveData<List<Event>> getEventsByContactIDAndDay(String contactID, int day) {
        return eventDao.getEventsByContactIDAndDay(contactID, day);
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

    private static class getEventsByContactIDAsyncTask extends AsyncTask<String, Void, List<Event>> {

        private EventDao asyncTaskDao;
        private OnTaskCompleted onTaskCompletedlistener;

        getEventsByContactIDAsyncTask(EventDao dao, OnTaskCompleted onTaskCompletedlistener) {
            asyncTaskDao = dao;
            this.onTaskCompletedlistener = onTaskCompletedlistener;
        }

        @Override
        protected List<Event> doInBackground(final String... params) {
            return asyncTaskDao.getEventsByContactID(params[0]);
        }

        @Override
        protected void onPostExecute(List<Event> result) {
            onTaskCompletedlistener.onTaskCompleted(result);
        }
    }
}
