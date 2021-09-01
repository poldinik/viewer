package it.unifi.ing.hci.mvc;

import it.unifi.ing.hci.App;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Controller implements Bind<View> {

    private View view;

    @Override
    public void bind(View view) {
        this.view = view;
        view.setController(this);
        App.replace(view);
    }
}
