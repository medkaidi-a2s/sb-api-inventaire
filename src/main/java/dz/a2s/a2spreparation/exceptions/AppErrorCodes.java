package dz.a2s.a2spreparation.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AppErrorCodes {
    PRODUIT_VALIDE("PRODUIT_VALIDE", "Le produit a été déjà validé"),
    PRODUIT_CONTROLE("PRODUIT_CONTROLE", "Le produit a été déjà controllé"),
    BON_EN_PREPARATION("BON_EN_PREPARATION", "Le bon est déjà en cours de préparation"),
    BON_EN_CONTROLE("BON_EN_CONTROLE", "Le bon est déjà en cours de contrôle");

    private final String code;
    private final String message;
}
