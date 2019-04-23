package clazzes;

import org.jetbrains.annotations.Contract;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

@ManagedBean
@SessionScoped
public class FacesFormBean implements Serializable {
    private static final long UID = 1L;
    private String name;
    private String team;
    private String participation;
    private Boolean selfDrive;
    private int trCompetitionId;

    @Contract(pure = true)
    public static long getUID() {
        return UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("set name: " + name);
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        System.out.println("set team: " + team);
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String eventId = (String) params.get("eventId");
        System.out.println(eventId);
        this.team = team;
    }

    public void submit() {
        System.out.println("submit");
        //return new Object();
    }

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
}
