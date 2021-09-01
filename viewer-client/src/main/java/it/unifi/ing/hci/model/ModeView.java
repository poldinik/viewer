package it.unifi.ing.hci.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModeView {
    private Mode mode = Mode.GRID;

    public static ModeView of(Mode mode){
        return new ModeView(mode);
    }

    public enum Mode {
        GRID,
        CAROUSEL
    }
}
