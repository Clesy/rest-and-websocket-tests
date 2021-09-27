package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InsensitiveOrderList<T> extends ArrayList<T> {
    public InsensitiveOrderList(int initialCapacity) {
        super(initialCapacity);
    }

    public InsensitiveOrderList() {
    }

    public InsensitiveOrderList(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public int hashCode() {
        int result = 0;

        for (T item : this) {
            result += item != null ? item.hashCode() : 0;
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof InsensitiveOrderList<?> list) {
            return size() == list.size() && containsAll(list);
        }
        return false;
    }
}
