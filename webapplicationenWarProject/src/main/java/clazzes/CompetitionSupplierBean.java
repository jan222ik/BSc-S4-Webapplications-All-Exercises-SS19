package clazzes;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

@ManagedBean
@SessionScoped
public class CompetitionSupplierBean implements Serializable {
    private static final long UID = 2L;
    @Contract(pure = true)
    public CompetitionSupplierBean() {
        CompetitionEntry c0 = new CompetitionEntry(0, 1, LocalDateTime.of(2019, 8, 13, 20, 0), "Altötting");
        CompetitionEntry c1 = new CompetitionEntry(1, 3, LocalDateTime.of(2019, 8, 20, 20, 0), "Hurlach");
        CompetitionEntry c2 = new CompetitionEntry(2, 5, LocalDateTime.of(2019, 8, 27, 19, 0), "Burching");
        CompetitionEntry c3 = new CompetitionEntry(3, 1, LocalDateTime.of(2019, 9, 1, 19, 0), "Burching");
        CompetitionEntry c4 = new CompetitionEntry(4, 2, LocalDateTime.of(2019, 9, 6, 20, 0), "Hurlach");
        CompetitionEntry c5 = new CompetitionEntry(5, 4, LocalDateTime.of(2019, 10, 15, 20, 0), "Altötting");
        luftgewehr.addAll(Arrays.asList(c0, c1, c2));
        luftpistole.addAll(Arrays.asList(c3, c4, c5));
        luftgewehr.forEach(e -> lookUpMap.put(e.getId(), e));
        luftpistole.forEach(e -> lookUpMap.put(e.getId(), e));
    }

    private LinkedList<CompetitionEntry> luftgewehr = new LinkedList<>();
    private LinkedList<CompetitionEntry> luftpistole = new LinkedList<>();
    private HashMap<Integer, CompetitionEntry> lookUpMap = new HashMap<>();

    @Contract(pure = true)
    public LinkedList<CompetitionEntry> getLuftgewehr() {
        return luftgewehr;
    }

    @Contract(pure = true)
    public LinkedList<CompetitionEntry> getLuftpistole() {
        return luftpistole;
    }

    public LinkedList<CompetitionEntry> getForTeam(@NotNull String teamName) {
        return (teamName.contains("gewehr")) ? luftgewehr: luftpistole;
    }

    public CompetitionEntry getCompetitionFromId(int id) {
        return lookUpMap.get(id);
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
