package waldrapps.plannit.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import waldrapps.plannit.Event;
import waldrapps.plannit.Repository;

public class EventViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<Event>> allEvents;

    public interface Query {
        void queryCallback(List<Event> events);
    }

    public EventViewModel (Application application) {
        super(application);
        repository = new Repository(application);
        allEvents = repository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() { return allEvents; }

    public void insert(Event event) { repository.insertEvent(event); }

    public void deleteAllEvents() { repository.deleteAllEvents(); }

    public Event getEventById(int id) { return repository.getEventById(id); }

    public LiveData<List<Event>> getEventsByContactIDLive(String id) { return repository.getEventsByContactIDLive(id); }

    public void getEventsByContactID(String id, Repository.OnTaskCompleted onTaskCompleted) {
        repository.getEventsByContactID(id, onTaskCompleted);
    }

    public LiveData<List<Event>> getEventByContactIDAndDay(String contactID, int day) { return repository.getEventsByContactIDAndDay(contactID, day); }
}
