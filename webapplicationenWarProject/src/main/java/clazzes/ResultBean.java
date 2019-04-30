package clazzes;

import org.jetbrains.annotations.Contract;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static clazzes.STATIC_NAMES.CompetitionParticipation.*;

@ManagedBean
@SessionScoped
public class ResultBean implements Serializable {
    private String team;
    private HashMap<String, LinkedList<ResultEntry>> data = new HashMap<>();

    public void submit() {
        try {
            data = new HashMap<>();
            PreparedStatement preparedStatement = HSQLDBEmbeddedServer.getInstance().getConnection().prepareStatement(
                    "SELECT " + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING
                            + ", " + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING
                            + ", " + COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING
                            + ", " + COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING
                            + ", " + COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING
                            + " FROM " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING
                            + " WHERE " + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + " = '" + team + "'"
            );
            ResultSet r = preparedStatement.executeQuery();
            while (r.next()) {
                ResultEntry resultEntry = new ResultEntry(
                        r.getString(COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING),
                        r.getString(COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING),
                        r.getString(COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING),
                        r.getString(COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING),
                        r.getString(COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING));
                if (!data.containsKey(resultEntry.name)) {
                    data.put(resultEntry.name, new LinkedList<>());
                }
                data.get(resultEntry.name).add(resultEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public LinkedList<ResultEntry> getResultEntry(String personName) {
        return data.get(personName);
    }

    public LinkedList<String> getParticipants() {
        LinkedList<String> list = new LinkedList<>(data.keySet());
        list.sort(String::compareTo);
        return list;
    }

    public class ResultEntry {
        private String name;
        private String participation;
        private String lift;
        private String compID;
        private String self;

        @Contract(pure = true)
        public ResultEntry(String name, String participation, String lift, String compID, String self) {
            this.name = name;
            this.participation = participation;
            this.lift = lift;
            this.compID = compID;
            this.self = self;
        }


        public String getCompID() {
            return compID;
        }

        public void setCompID(String compID) {
            this.compID = compID;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getLift() {
            return lift;
        }

        public void setLift(String lift) {
            this.lift = lift;
        }

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getParticipation() {
            return participation;
        }

        public void setParticipation(String participation) {
            this.participation = participation;
        }
    }
}
