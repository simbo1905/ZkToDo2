package forum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkforge.zktodo2.Model;
import org.zkforge.zktodo2.Reminder;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class ControllerVM {
    // wired property     
    @WireVariable Model model = null; 

    public void setModel(Model model) { this.model = model; }
    public Model getModel() { return model; }

    @Init
    public void init(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
        model = new Model();
        reload();
    }

    protected void reload() {
        List<Reminder> reminders = new ArrayList<Reminder>();
        reminders.add(new Reminder("event1", 2, new Date()));
        reminders.add(new Reminder("event2", 1, new Date()));
        reminders.add(new Reminder("event3", 3, new Date()));
        reminders.add(new Reminder("event4", 1, new Date()));
        reminders.add(new Reminder("event5", 4, new Date()));
        this.model.getReminders().clear();
        this.model.getReminders().addAll(reminders);
    }
}