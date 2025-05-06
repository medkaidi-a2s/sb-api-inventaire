package dz.a2s.a2spreparation.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AppErrorCodes {
    PRODUIT_VALIDE("PRODUIT_VALIDE", "Le produit a été déjà validé"),
    PRODUIT_CONTROLE("PRODUIT_CONTROLE", "Le produit a été déjà controllé"),
    PRESENCE_PRODUIT_INVALIDE("PRESENCE_PRODUIT_INVALIDE", "Présence de produits non validés dans la commande."),
    CDE_EN_PREPARATION("CDE_EN_PREPARATION", "La commande est déjà en cours de préparation"),
    CDE_EN_CONTROLE("CDE_EN_CONTROLE", "La commande est déjà en cours de contrôle"),
    CDE_PREPARE("CDE_PREPARE", "La commande a été déjà préparée"),
    CDE_CONTROLE("CDE_CONTROLE", "La commande a été déjà contrôlée");

    private final String code;
    private final String message;
}
