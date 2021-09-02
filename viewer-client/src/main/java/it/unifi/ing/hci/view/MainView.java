package it.unifi.ing.hci.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.carousel.MaterialCarousel;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.*;
import gwt.material.design.jscore.client.api.JSON;
import it.unifi.ing.hci.controller.MainController;
import it.unifi.ing.hci.interop.Console;
import it.unifi.ing.hci.model.Media;
import it.unifi.ing.hci.model.ModeView;
import it.unifi.ing.hci.mvc.View;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
 * Ogni classe View è provvista di un corrispondende XML per definizione statica di oggetti, permette di definire
 * layout e stile
 */
public class MainView extends View<MainController> {

    /*
    * Sono righe di codice del framework per collegare la classe MainView al corrispondente definizione statica XML
    */
    interface MainViewUiBinder extends UiBinder<Widget, MainView> {}
    private static final MainViewUiBinder ourUiBinder = GWT.create(MainViewUiBinder.class);

    /*
     * Tramite @UiField è possibile definire attribuiti corrispondenti a widget nella definizione XML
     * Infatti nell'XML MainView.ui.xml troveremo un MaterialRow con attributo ui:field="row" a cui questa classe
     * corrisponde per poterla usare in codice Java
     */

    @UiField
    MaterialRow row, carouselRow;

    @UiField
    InputElement fileupload, submit;

    @UiField
    MaterialDialog dialog;

    @UiField
    MaterialButton btnCloseDialog;

    @UiField
    MaterialAnchorButton grid, carousel;

    @UiField
    MaterialCarousel multipleItemCarousel;

    FileUpload upload;

    @UiConstructor
    public MainView() {
        // Riga del framework per binding classe con XML
        initWidget(ourUiBinder.createAndBindUi(this));

        // Collega un gestore di evento all'elemento corrispondente all'input per il file
        Event.sinkEvents(fileupload, Event.ONCHANGE);
        Event.setEventListener(fileupload, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                Console.log(event);
                InputElement element = event.getEventTarget().cast();
                Console.log(element);
                submit.click();

            }
        });

        FormPanel panel = new FormPanel();
        panel.setStyleName("hide");

        panel.setMethod("post");
        panel.setEncoding("multipart/form-data");

        upload = new FileUpload();
        upload.setName("uploadedFile");
        upload.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                panel.submit();
            }
        });


        panel.add(upload);
        panel.setAction("http://localhost:8080/media");

        panel.addSubmitHandler(submitEvent -> {
        });

        panel.addSubmitCompleteHandler(submitCompleteEvent -> {
            GWT.log("AddMedia");
            getController().addMedia();
        });

        row.add(panel);

        btnCloseDialog.addClickHandler((clickEvent -> dialog.close()));
    }

    // Metodo statico per implementare un factory method e rendere il codice più pulito
    // e potenzialmente predisposto per creare oggetti con certe proprietà se necessario
    public static View getInstance(){
        return new MainView();
    }

    // Metodo che esegue la renderizzazione della UI in base al model
    @Override
    public void render(){
        MaterialLoader.loading(getController().getLoading());
        List<Media> mediaList = getController().getMediaList();
        if(mediaList.size() == 0){
            MaterialEmptyState materialEmptyState = new MaterialEmptyState();
            materialEmptyState.setHeight("100vh");
            materialEmptyState.setIconType(IconType.IMAGE);
            materialEmptyState.setIconColor(Color.BLUE_DARKEN_3);
            materialEmptyState.setTitle("Nessuna immagine");
            materialEmptyState.setDescription("Clicca sul menù in basso a destra per aggiungere una o più immagini.");
            row.add(materialEmptyState);
        }else {
            if(getController().getModeView().getMode().equals(ModeView.Mode.GRID)){
                for (Media media : mediaList) {
                    MaterialColumn column = new MaterialColumn();
                    column.setGrid("l2 m4 s12");
                    column.add(new MaterialCard());
                    Thumbnail thumbnail = new Thumbnail(media, () -> dialog.open());
                    column.add(thumbnail);
                    row.add(column);
                }
            }else {
                carouselRow.setDisplay(null);
                multipleItemCarousel.reload();
            }
        }
    }

    // Tramite annotazione @UiHandler è possibile collegare azioni a oggetti definiti nell'XML
    @UiHandler("add")
    public void onAddClick(ClickEvent clickEvent){
        getController().addItem();
    }

    @UiHandler("upload")
    public void onUploadClick(ClickEvent clickEvent){
        //fileupload.click();
        upload.click();
    }

    @UiHandler("grid")
    public void onGridClick(ClickEvent clickEvent){
        getController().setModeView(ModeView.Mode.GRID);
    }

    @UiHandler("carousel")
    public void onCarouselClick(ClickEvent clickEvent){
        getController().setModeView(ModeView.Mode.CAROUSEL);
    }

}