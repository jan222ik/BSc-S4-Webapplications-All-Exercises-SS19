package clazzes;

import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class HistoryEntry {
    private Pages page;
    private LocalDateTime date;

    @Contract(pure = true)
    public HistoryEntry(Pages page, LocalDateTime date) {
        this.page = page;
        this.date = date;
    }

    public Pages getPage() {
        return page;
    }

    public void setPage(Pages page) {
        this.page = page;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoryEntry{" +
                "page=" + page +
                ", date='" + date + '\'' +
                '}';
    }

    public String toDisplayString() {
        return " Seite: " + page.getDisplayName() + " wurde besucht am: '" + date.format(DateTimeFormatter.ofPattern("dd MM YYYY")) + " um: "+ date.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "';";
    }
}
