package it.unifi.ing.hci;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.RootPanel;
import io.reactivex.Flowable;
import it.unifi.ing.hci.mvc.Controller;
import it.unifi.ing.hci.controller.MainController;
import it.unifi.ing.hci.view.MainView;
import it.unifi.ing.hci.mvc.View;

// Classe entry point per il lancio dell'applicazione
public class App implements EntryPoint {

	// É il "main" dell'applicazione
	// Vengono instanziati un Controller e una View ed eseguito il loro binding
	public void onModuleLoad() {
		Controller controller = MainController.getInstance();
		View view = MainView.getInstance();
		controller.bind(view);
	}

	/*
	* Funzioni di uilità per la renderizzazione delle viste
	*/

	// Sono metodi che servono per renderizzare una vista
	// Appendendola all root del DOM
	public static void replace(View view){
		RootPanel.get().clear();
		RootPanel.get().add(view);
		render(view);
	}

	// Invoca il metodo render di una View.
	// Il metodo ha la responsabilità di eseguire codice in relazione al Model
	// Come ad esempio renderizzare una lista di oggetti
	public static void render(View view){
		view.render();
	}
}
