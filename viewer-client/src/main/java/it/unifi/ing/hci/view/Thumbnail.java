package it.unifi.ing.hci.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLink;
import it.unifi.ing.hci.model.Media;
import lombok.Setter;

@Setter
public class Thumbnail extends Composite {

    interface MainViewUiBinder extends UiBinder<Widget, Thumbnail> { }
    private static final MainViewUiBinder ourUiBinder = GWT.create(MainViewUiBinder.class);

    @UiField
    MaterialCardTitle title;

    @UiField
    MaterialLink visualize;

    public Thumbnail(Media media, Thumbnail.onVisualize onVisualize) {
        initWidget(ourUiBinder.createAndBindUi(this));
        title.setText(media.getTitle());
        visualize.addClickHandler(clickEvent -> onVisualize.onvisualize());
    }

    @FunctionalInterface
    public interface onVisualize{
        void onvisualize();
    }

}