package com.lisetckiy.lab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FingerTable {
    private List<FingerTableItem> items;

    public FingerTable(FingerTable fingerTable){
        this.items = new ArrayList<>();
        for (FingerTableItem item :
                fingerTable.items) {
            this.items.add(new FingerTableItem(item));
        }
    }

    public FingerTable(int n, int m) {
        items = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            items.add(new FingerTableItem(n, i + 1, m));
        }
    }

    public void applyChanges(){
        items.forEach(FingerTableItem::applyChanges);
    }

    public FingerTableItem get(int i) {
        return items.get(i - 1);
    }

    public Stream<FingerTableItem> stream(){
        return items.stream();
    }

    @Override
    public String toString() {
        return "items=" + items.toString();
    }
}
