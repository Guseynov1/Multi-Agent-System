package lab.pr;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class eLinked<E> implements Collection<E> {

    public int size;
    eLink first, last;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class eLink {
        int size = 0;
        Object [] data = new Object[5];
        eLink previous, next;
    }

    public eLinked() { clear(); }
    private void accept(Object object) { addLast((E) object); }

    @Override
    public void clear() {
        first = new eLink();
        last = first;
        size = 0;
    }

    public boolean addAll(int index, @NotNull Collection<? extends E> collection) {
        AtomicInteger atomicIndex = new AtomicInteger();
        collection.forEach(e -> {
            add(atomicIndex.addAndGet(index), e);
            atomicIndex.getAndIncrement();
        });
        return true;
    }

    public void add(int index, E element) {
        Object[] result = new Object[this.toArray().length + 1];
        System.arraycopy(this.toArray(), 0, result, 0, index);
        System.arraycopy(this.toArray(), index, result, index + 1, this.toArray().length - index);
        result[index] = element;
        clear();
        Arrays.stream(result).forEach(this::accept);
    }

    public void remove(int index) {
        Object[] result = new Object[this.toArray().length - 1];
        if (index > 0) System.arraycopy(this.toArray(), 0, result, 0, index);
        if (result.length > index) System.arraycopy(this.toArray(), index + 1, result, index, this.toArray().length - index - 1);
        clear();
        Arrays.stream(result).forEach(this::accept);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        collection.stream().filter(object -> indexOf(object) > -1).forEach(object -> remove(indexOf(object)));
        return false;
    }

    public int indexOf(Object object) {
        for (int i = 0; i < size; i++) {
            if (get(i).equals(object)) return i;
        } return -1;
    }

    public void addFirst(E e){
        if (first.data[0] != null) {
            eLink link = new eLink();
            link.next = first;
            first.previous = link;
            first = link;
            link.data[4] = e;
            link.size++;
        }
        else
            if ((4 - first.size) >= 0) {
                first.data[4 - first.size] = e;
                first.size++;
            }
        size++;
    }

    public void addLast(E e) {
        if (last.data[4] != null) {
            eLink leLink = last;
            eLink eLink = new eLink();
            eLink.data[0] = e;
            leLink.next = eLink;
            eLink.previous = last;
            last = eLink;
            size++;
        } else
            for (int i = 0; i < last.data.length; i++) {
                if (last.data[i] == null) {
                    last.data[i] = e;
                    size++;
                    break;
                }
            }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private eLink eLink = first;
            private int lastNode, nextNode = 0;

            @Override
            public boolean hasNext() {
                return nextNode < size;
            }

            @Override
            public E next() {
                nextNode++;
                if (lastNode == 5) {
                    if (eLink.next != null) eLink = eLink.next;
                    lastNode = 0;
                }
                return (E) eLink.data[lastNode++];
            }
        };
    }

    public E get(int index) {
        if (index <= size()) {
            AtomicInteger indexAtomic = new AtomicInteger();
            for (E k : this) {
                if (indexAtomic.getAndIncrement() == index) return k;
            }
        } throw new IndexOutOfBoundsException();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        AtomicInteger index = new AtomicInteger();
        this.forEach(e -> array[index.getAndIncrement()] = e);
        return array;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }
    @Override
    public boolean remove(Object object) {
        remove(indexOf(object));
        return false;
    }
    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return collection.stream().allMatch(this::contains);
    }
    @Override
    public boolean addAll(@NotNull Collection<? extends E> collection) { return addAll(size, collection); }
    @Override
    public int size() { return size; }
    @Override
    public boolean isEmpty() {  return size == 0; }
    @Override
    public boolean contains(Object object) { return indexOf(object) != -1; }
    @Override
    public boolean retainAll(@NotNull Collection<?> collection) { return false; }
    @Override
    public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) { return null; }
}