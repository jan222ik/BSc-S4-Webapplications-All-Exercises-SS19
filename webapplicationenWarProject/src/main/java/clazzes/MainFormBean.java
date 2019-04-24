package clazzes;

import org.jetbrains.annotations.Contract;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

@ManagedBean
@SessionScoped
public class MainFormBean implements Serializable {
    private static final long UID = 1L;
    private String name;
    private String team;

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

    @Override
    public String toString() {
        return "MainFormBean{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
