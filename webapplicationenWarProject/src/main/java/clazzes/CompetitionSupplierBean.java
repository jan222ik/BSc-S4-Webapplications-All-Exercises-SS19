package clazzes;

import org.jetbrains.annotations.Contract;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;

@ManagedBean
@SessionScoped
public class CompetitionSupplierBean implements Serializable {
    private static final long UID = 2L;
    @Contract(pure = true)
    public CompetitionSupplierBean() {
        luftgewehr.add(new CompetitionEntry(0, 1, LocalDateTime.of(2019,8,13, 20, 0), "Altötting"));
        luftgewehr.add(new CompetitionEntry(1, 3, LocalDateTime.of(2019,8,20, 20, 0), "Hurlach"));
        luftgewehr.add(new CompetitionEntry(2, 5, LocalDateTime.of(2019,8,27, 19, 0), "Burching"));
        luftpistole.add(new CompetitionEntry(3, 1, LocalDateTime.of(2019,9,1, 19, 0), "Burching"));
        luftpistole.add(new CompetitionEntry(4, 2, LocalDateTime.of(2019,9,6, 20, 0), "Hurlach"));
        luftpistole.add(new CompetitionEntry(5, 4, LocalDateTime.of(2019,10,15, 20, 0), "Altötting"));
    }

    private LinkedList<CompetitionEntry> luftgewehr = new LinkedList<>();
    private LinkedList<CompetitionEntry> luftpistole = new LinkedList<>();

    @Contract(pure = true)
    public LinkedList<CompetitionEntry> getLuftgewehr() {
        return luftgewehr;
    }

    @Contract(pure = true)
    public LinkedList<CompetitionEntry> getLuftpistole() {
        return luftpistole;
    }

    public class CompetitionEntry {
        private int id;
        private int numberInSeason;
        private LocalDateTime date;
        private String location;

        @Contract(pure = true)
        public CompetitionEntry(int id, int numberInSeason, LocalDateTime date, String location) {
            this.id = id;
            this.numberInSeason = numberInSeason;
            this.date = date;
            this.location = location;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getNumberInSeason() {
            return numberInSeason;
        }

        public void setNumberInSeason(int numberInSeason) {
            this.numberInSeason = numberInSeason;
        }

        public int getId() {
            return id;
        }
    }

}
