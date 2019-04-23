package clazzes;

import org.jetbrains.annotations.Contract;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class FacesFormBean implements Serializable {
    private static final long UID = 1L;
    private String name;
    private String discipline;

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

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        System.out.println("set discipline: " + discipline);
        this.discipline = discipline;
    }

    public void submit() {
        System.out.println("submit");
        //return new Object();
    }
}
