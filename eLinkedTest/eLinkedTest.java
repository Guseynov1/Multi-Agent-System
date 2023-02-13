package eLinkedTest;

import lab.pr.eLinked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class eLinkedTest {

    eLinked<Object> list = new eLinked<>();
    @Test
    public void sizeTest() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5));
        Assertions.assertEquals(6, list.size());
    }

    @Test
    public void addTest() {
        list.addFirst(4);
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addFirst(0);
        list.add(8);
        list.add(9);
        Assertions.assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, list.toArray());
    }

    @Test
    public void isEmptyTest() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        list.removeAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void containsTest() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        Assertions.assertFalse(list.contains(13));
    }

    @Test
    public void containsAll() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        Assertions.assertTrue(list.containsAll(List.of(0, 1, 2, 3, 4, 12)));
    }

    @Test
    public void iteratorTest() {
        int i = 0;
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        for (Object object : list) { Assertions.assertEquals(i, object); i++; }
    }

    @Test
    public void clearTest() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        list.clear();
        Assertions.assertEquals(0, list.size);
    }

    @Test
    public void getTest() {
        list.addAll(List.of(6, 5, 5, 1, 3, 8, 9, 7, 8, 2));
        Assertions.assertEquals(3, list.get(4));
    }

    @Test
    public void removeByIndexTest() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        list.remove(0);
        list.remove(2);
        Assertions.assertArrayEquals(new Integer[] {1, 2, 4, 5, 6, 7, 8, 9}, list.toArray());
    }

    @Test
    public void addByIndexTest() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        list.add(0, 7);
        list.add(2, 3);
        Assertions.assertArrayEquals(new Integer[] {7, 0, 3, 1, 2, 3, 4, 5, 6, 7, 8, 9}, list.toArray());
    }

    @Test
    public void removeByValueTest() {
        list.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        list.remove(2);
        Assertions.assertArrayEquals(new Integer[] {0, 1, 3, 4, 5, 6, 7, 8, 9}, list.toArray());
    }

    @Test
    public void indexOfTest() {
        list.addAll(List.of(22, 33, 42, 55, 46, 524, 6, 77, 89, 9));
        Assertions.assertEquals(4, list.indexOf(46));
    }

    @Test
    public void sSizeTest() {
        list.addAll(List.of("a", "b", "c"));
        Assertions.assertEquals(3, list.size());
    }

    @Test
    public void sAddTest() {
        list.addAll(List.of("a", "b", "c"));
        list.addFirst("d");
        list.addFirst("e");
        list.addFirst("f");
        list.addFirst("g");
        list.addFirst("h");
        Assertions.assertArrayEquals(new String[] {"h", "g", "f", "e", "d", "a", "b", "c", }, list.toArray());
    }

    @Test
    public void sIsEmptyTest() {
        list.addAll(List.of("a", "b", "c"));
        list.removeAll(List.of("a", "b", "c"));
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void sContainsTest() {
        list.addAll(List.of("a", "b", "c"));
        Assertions.assertTrue(list.contains("b"));
    }

    @Test
    public void sContainsAll() {
        list.addAll(List.of("a", "b", "c"));
        Assertions.assertTrue(list.containsAll(List.of("a", "b", "c")));
    }

    @Test
    public void sIteratorTest() {
        list.addAll(List.of("a", "b", "c"));
        Assertions.assertEquals(list.get(0), "a");
        Assertions.assertEquals(list.get(1), "b");
        Assertions.assertEquals(list.get(2), "c");
    }

    @Test
    public void sClearTest() {
        list.addAll(List.of("a", "b", "c"));
        list.clear();
        Assertions.assertEquals(0, list.size);
    }

    @Test
    public void sGetTest() {
        list.addAll(List.of("a", "b", "c"));
        Assertions.assertEquals("a", list.get(0));
    }

    @Test
    public void sRemoveByIndexTest() {
        list.addAll(List.of("a", "b", "c"));
        list.remove(0);
        list.remove(1);
        Assertions.assertArrayEquals(new String[] {"b"}, list.toArray());
    }

    @Test
    public void sAddByIndexTest() {
        list.addAll(List.of("a", "b", "c"));
        list.add(0, "a");
        list.add(1, "c");
        Assertions.assertArrayEquals(new String[] {"a", "c", "a", "b", "c"}, list.toArray());
    }

    @Test
    public void sRemoveByValueTest() {
        list.addAll(List.of("a", "b", "c"));
        list.remove("b");
        Assertions.assertArrayEquals(new String[] {"a", "c"}, list.toArray());
    }

    @Test
    public void sIndexOfTest() {
        list.addAll(List.of("a", "b", "c"));
        Assertions.assertEquals(2, list.indexOf("c"));
    }
}