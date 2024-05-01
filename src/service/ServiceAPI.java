package service;

public interface ServiceAPI<T> {

    // Abstract methods
    void add(T object);
    void remove(T object);

    // Print method
    void print();

}
