package clazzes;

import java.util.LinkedList;

public class HistoryContainer {
    private LinkedList<HistoryEntry> entries = new LinkedList<>();

    public LinkedList<HistoryEntry> getEntries() {
        return entries;
    }

    public void setEntries(LinkedList<HistoryEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "HistoryContainer{" +
                "entries=" + entries.size() +
                '}';
    }
}
