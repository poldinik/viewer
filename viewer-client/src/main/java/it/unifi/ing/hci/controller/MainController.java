package it.unifi.ing.hci.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.thirdparty.json.JSONStringer;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import elemental2.dom.DomGlobal;
import gwt.material.design.jscore.client.api.JSON;
import it.unifi.ing.hci.App;
import it.unifi.ing.hci.interop.Console;
import it.unifi.ing.hci.model.Media;
import it.unifi.ing.hci.model.ModeView;
import it.unifi.ing.hci.mvc.Controller;
import it.unifi.ing.hci.service.REST;
import it.unifi.ing.hci.view.MainView;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MainController extends Controller implements ValueChangeHandler<String> {

    private List<Media> mediaList = new ArrayList<>();
    private ModeView modeView = ModeView.of(ModeView.Mode.GRID);

    public MainController(){
        History.addValueChangeHandler(this);
        //REST.getImageList((json) -> { });
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> valueChangeEvent) {
        String token = valueChangeEvent.getValue();
        if (token != null) {
            switch (token){
                default:
                    App.replace(getView());
                    break;
            }
        }
    }

    public static Controller getInstance(){
        return new MainController();
    }

    public void addItem(){
        mediaList.add(new Media("Foto"));
        bind(MainView.getInstance());
    }


    public void deleteMedia(Media media){
        REST.deleteImage(media, jsonValue -> {
            bind(MainView.getInstance());
        });
    }

    public void setModeView(ModeView.Mode mode){
        modeView = ModeView.of(mode);
        bind(MainView.getInstance());
    }

    public void addMedia() {
        REST.getMediaCounts((json) -> {
            int count = Integer.parseInt(json.isObject().get("mediaCount").isNumber().toString());
            mediaList.clear();
            for (int i = 0; i < count; i++){
                mediaList.add(new Media());
            }
            bind(MainView.getInstance());
        });
    }
}
