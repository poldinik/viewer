package it.unifi.ing.hci.controller;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.History;
import gwt.material.design.jscore.client.api.JsObject;
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
    private Boolean loading = false;

    public MainController(){
        History.addValueChangeHandler(this);
        //REST.getImageList((json) -> { });

        REST.getMediaList((json) -> {
            mediaList.clear();
            JSONArray ml = json.isObject().get("media").isArray();
            JsArrayString ja = (JsArrayString) ml.getJavaScriptObject();
            Console.log("ARRAY");
            Console.log(ja);
            for (int i = 0; i < ja.length(); i++){
                Media media = new Media();
                media.setUrl(ja.get(i));
                mediaList.add(media);
            }
            bind(MainView.getInstance());
        });
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
        loading = true;
        REST.getMediaList((json) -> {
            Console.log("ciao");
            mediaList.clear();
            JSONArray ml = json.isObject().get("media").isArray();
            JsArrayString ja = (JsArrayString) ml.getJavaScriptObject();
            for (int i = 0; i < ja.length(); i++){
                Media media = new Media();
                media.setUrl(ja.get(i));
                mediaList.add(media);
            }
            loading = false;
            bind(MainView.getInstance());
        });
    }

}
