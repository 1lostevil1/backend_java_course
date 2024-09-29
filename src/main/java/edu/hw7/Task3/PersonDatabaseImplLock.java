package edu.hw7.Task3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("MagicNumber")
public class PersonDatabaseImplLock implements PersonDatabase {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final Map<Integer, Person> ID_MAP = new HashMap<>();
    private static final Map<String, Person> NAME_MAP = new HashMap<>();
    private static final Map<String, Person> ADDRESS_MAP = new HashMap<>();
    private static final Map<String, Person> PHONE_MAP = new HashMap<>();

    public int size() {
        return ID_MAP.size();
    }

    public void add(Person person) {
        try {
            LOCK.writeLock().tryLock(100, TimeUnit.MILLISECONDS);
            ID_MAP.put(person.id(), person);
            if (person.name() != null) {
                NAME_MAP.put(person.name(), person);
            }
            if (person.address() != null) {
                ADDRESS_MAP.put(person.address(), person);
            }
            if (person.phoneNumber() != null) {
                PHONE_MAP.put(person.phoneNumber(), person);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    public void delete(int id) {
        try {
            LOCK.writeLock().tryLock(100, TimeUnit.MILLISECONDS);
            if (ID_MAP.containsKey(id)) {
                Person person = ID_MAP.remove(id);
                NAME_MAP.remove(person.name());
                ADDRESS_MAP.remove(person.address());
                PHONE_MAP.remove(person.phoneNumber());
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    public @Nullable Person findByName(String name) {
        try {
            LOCK.readLock().tryLock(100, TimeUnit.MILLISECONDS);
            if (NAME_MAP.containsKey(name)) {
                Person person = NAME_MAP.get(name);
                if (person.address() != null && person.phoneNumber() != null) {
                    return NAME_MAP.get(name);
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            LOCK.readLock().unlock();
        }
        return null;
    }

    public @Nullable Person findByAddress(String address) {
        try {
            LOCK.readLock().tryLock(100, TimeUnit.MILLISECONDS);
            if (ADDRESS_MAP.containsKey(address)
                && ADDRESS_MAP.get(address).phoneNumber() != null
                && ADDRESS_MAP.get(address).name() != null) {
                return ADDRESS_MAP.get(address);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            LOCK.readLock().unlock();
        }
        return null;
    }

    public @Nullable Person findByPhone(String phone) {
        try {
            LOCK.readLock().tryLock(100, TimeUnit.MILLISECONDS);
            if (PHONE_MAP.containsKey(phone)
                && PHONE_MAP.get(phone).name() != null
                && PHONE_MAP.get(phone).address() != null) {
                return PHONE_MAP.get(phone);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            LOCK.readLock().unlock();
        }
        return null;
    }
}
