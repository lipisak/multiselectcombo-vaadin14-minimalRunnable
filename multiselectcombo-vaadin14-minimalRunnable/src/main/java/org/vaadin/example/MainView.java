package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.List;
import java.util.Map;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    private static final Map<String, String> translations = Map.of("MONDAY", "Pondělí", "TUESDAY", "Úterý",
            "WEDNESDAY", "Středa", "THURSDAY", "Čtvrtek", "FRIDAY", "Pátek",
            "SATURDAY", "Sobota", "SUNDAY", "Neděle");

    public MainView() {
        MultiComboBoxField<String> field = new MultiComboBoxField<>();
        field.withItems(List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"));
        field.withItemCaptionGenerator(translations::get);
        field.setValue(List.of("MONDAY", "TUESDAY", "WEDNESDAY"));
        field.setWidthFull();

        addAndExpand(field);
    }
}
