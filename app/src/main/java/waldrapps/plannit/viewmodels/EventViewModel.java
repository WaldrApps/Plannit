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

    public EventViewModel (Application application) {
        super(application);
        repository = new Repository(application);
        allEvents = repository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() { return allEvents; }

    public void insert(Event event) { repository.insertEvent(event); }

    public Event getEventById(int id) { return repository.getEventById(id); }

    public LiveData<List<Event>> getEventByContactAndDay(String contactId, int dayOfWeek) { return repository.getEventsByContactAndDay(contactId, dayOfWeek); }
}
