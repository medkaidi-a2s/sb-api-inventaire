package dz.a2s.a2spreparation.entities.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandeStatus {

    ASSIGNED(0, "Non affectée"),
    PREPARING(1, "En préparation"),
    PREPARED(2,"Préparée");

    private final int code;
    private final String status;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public static String getStatus(int code) {
        for(CommandeStatus status : CommandeStatus.values()) {
            if(status.getCode() == code)
                return status.getStatus();
        }
        throw new IllegalArgumentException("Code de statut invalide " + code);
    }

}
