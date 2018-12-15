package thinkinjava.flow.limit;

import java.util.concurrent.ConcurrentHashMap;

import thinkinjava.util.currence.Semaphore;

public class SemaphoreManager{

    private ConcurrentHashMap<String, Semaphore> map = new ConcurrentHashMap<>();

    private SemaphoreManager(){}

    public static SemaphoreManager getInstance() {
        return SemaphoreManagerLazyHolder.INSTANCE;
    }

    private static class SemaphoreManagerLazyHolder {

        private static final SemaphoreManager INSTANCE = new SemaphoreManager();
    }

    public void putSemaphore(String unique, Semaphore semaphore) {
        map.put(unique, semaphore);
    }

    public Semaphore getSemaphore(String unique) {
        Semaphore semaphore =  map.get(unique);
        if (semaphore == null) {
            semaphore = new Semaphore(unique, 10);
            Semaphore old = map.putIfAbsent(unique, semaphore);
            if (old != null) {
                semaphore = old;
            }
        }
        return semaphore;
    }




}
