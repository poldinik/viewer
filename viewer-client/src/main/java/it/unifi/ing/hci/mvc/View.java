package it.unifi.ing.hci.mvc;

import com.google.gwt.user.client.ui.Composite;
import it.unifi.ing.hci.App;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class View<C extends Controller> extends Composite implements Bind<C>, Render {

    private C controller;

    @Override
    public void bind(C controller){
        this.controller = controller;
        controller.setView(this);
        App.replace(this);
    }

}
