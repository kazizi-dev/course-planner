package ca.cmpt213.as5.watchers;

/*
    The observable class design for the observable pattern.
 */
public interface IObservable {
    public void addObserver(IObserver e);
    public void removeObserver(IObserver e);
    public void notifyObservers();

}
