package com.insanity.rs2.world.entities;

import com.insanity.rs2.model.entity.Entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
@SuppressWarnings("unused")
public class EntityList<E extends Entity> implements Collection<E>, Iterable<E> {

    private Entity entities[];

    private int size;

    public EntityList(int capacity) {
        this.entities = new Entity[capacity + 1];
    }

    public int indexOf(Entity entity) {
        return entity.getIndex();
    }

    @SuppressWarnings("unchecked")
    public E get(int index) throws IndexOutOfBoundsException {
        if (index <= 0 || index >= entities.length) {
            throw new IndexOutOfBoundsException();
        }
        return (E) entities[index];
    }

    private int getNextIndex() {
        for (int i = 1; i < entities.length; i++) {
            if (entities[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean add(E entity) {
        int index = getNextIndex();
        if (index == -1) {
            return false;
        }
        entities[index] = entity;
        entity.setIndex(index);
        size++;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> entities) {
        boolean added = false;
        for (E entity : entities) {
            if (add(entity)) {
                added = true;
            }
        }
        return added;
    }

    @Override
    public boolean contains(Object o) {
        for (Entity entity : entities) {
            if (entity == o) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    public boolean remove(int index) {
        entities[index] = null;
        size--;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 1; i < entities.length; i++) {
            if (entities[i] == o) {
                entities[i] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = true;
        for (Object o : c) {
            if (!remove(o)) {
                removed = false;
            }
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retained = false;

        for (int i = 0; i < entities.length; i++) {
            if (!c.contains(entities[i])) {
                entities[i] = null;
                size--;
                retained = true;
            }
        }

        return retained;
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return this.entities.length - size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new EntityListIterator<E>(this);
    }

    @Override
    public Entity[] toArray() {
        int ptr = 0;
        Entity[] entities = new Entity[size];
        for (int i = 0; i < this.entities.length; i++) {
            if (this.entities[i] != null) {
                entities[ptr++] = this.entities[i];
            }
        }

        return entities;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        Entity[] entities = toArray();
        return (T[]) Arrays.copyOf(entities, entities.length, a.getClass());
    }

    @Override
    public void clear() {
        for (int i = 0; i < entities.length; i++) {
            entities[i] = null;
        }
        size = 0;
    }
}
