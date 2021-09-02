package it.unifi.ing.hci.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    private String title;
    private String url;

    public Media(String title) {
        this.title = title;
    }
}
