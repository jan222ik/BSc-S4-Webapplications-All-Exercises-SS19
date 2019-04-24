package clazzes;

import org.jetbrains.annotations.NotNull;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import static clazzes.STATIC_NAMES.CompetitionParticipation.*;

@ManagedBean
@SessionScoped
public class CompetitionBean {
    private String participation;
    private Boolean selfDrive;
    private int trCompetitionId;
    private int lift;

    public void setParticipation(String value) {
        System.out.println(value);
        System.out.println("ID: " + trCompetitionId);
        participation = value;
    }

    public String getParticipation() {
        return participation;
    }

    public Boolean getSelfDrive() {

        return selfDrive;
    }

    public void setSelfDrive(Boolean selfDrive) {
        System.out.println("Set self drive" + selfDrive);
        System.out.println("ID: " + trCompetitionId);
        //Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        //String selfDriveId = (String) params.get("selfDriveId");
        //System.out.println(selfDriveId);
        this.selfDrive = selfDrive;
    }

    public int getTrCompetitionId() {
        return trCompetitionId;
    }

    public void setTrCompetitionId(int trCompetitionId) {
        System.out.println("set id:" + trCompetitionId);
        this.trCompetitionId = trCompetitionId;
    }

    public String getCompetitionInfoAsString(@NotNull CompetitionSupplierBean instance) {
        CompetitionSupplierBean.CompetitionEntry competitionFromId = instance.getCompetitionFromId(trCompetitionId);
        return "Wettkampf #" + competitionFromId.getNumberInSeason()
                + " am " + competitionFromId.getDate().format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))
                + " in " + competitionFromId.getLocation()
                + " um " + competitionFromId.getDate().format(DateTimeFormatter.ofPattern("HH:mm"))
                + " Uhr.\n";
    }

    public int getLift() {
        return lift;
    }

    public void setLift(int lift) {
        this.lift = lift;
    }

    public void submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        MainFormBean bean = context.getApplication().evaluateExpressionGet(context, "#{mainFormBean}", MainFormBean.class);
        System.out.println("Persisting: " + toString());
        System.out.println("User and Team: " + bean);
        String delimitWSpace = " , ";
        String delimit = "','";
        String colString = COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + delimitWSpace
                + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + delimitWSpace
                + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING + delimitWSpace
                + COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING + delimitWSpace
                + COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING;
        try {
            HSQLDBEmbeddedServer.getInstance().getConnection().prepareStatement(
                    "INSERT INTO " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING + "(" + colString + ")" +
                    " VALUES ('" + bean.getName() +delimit +bean.getTeam() +delimit + trCompetitionId + delimit + participation + delimit + ((participation.matches("nein") || !selfDrive ) ? 0:lift)
                   + "');").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            context.getExternalContext().redirect(Pages.DATABASE.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "CompetitionBean{" +
                "participation='" + participation + '\'' +
                ", selfDrive=" + selfDrive +
                ", trCompetitionId=" + trCompetitionId +
                ", lift=" + lift +
                '}';
    }
}
