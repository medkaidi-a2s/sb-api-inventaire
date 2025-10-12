package dz.a2s.a2sinventaire.entities.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthorizationCodes {

    AFFECTATION(480),
    PREPARATION(481),
    CONTROLE(482),
    STATISTICS(489),
    STOCKS(790),
    COMMANDES_EDIT(38),
    COMMANDES_LIST(513);

    private final Integer code;

    public int getCode() {
        return code;
    }

}
