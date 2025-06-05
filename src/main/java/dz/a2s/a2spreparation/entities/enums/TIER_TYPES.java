package dz.a2s.a2spreparation.entities.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TIER_TYPES {

    PREPARATEUR(4),
    CONTROLEUR(5);

    private final Integer type;

    public int getType() {
        return type;
    }

}
