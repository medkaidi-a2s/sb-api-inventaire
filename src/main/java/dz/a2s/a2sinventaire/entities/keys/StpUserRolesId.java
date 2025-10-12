package dz.a2s.a2sinventaire.entities.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class StpUserRolesId {

    @Column(name = "URL_CMP_ID")
    private Integer urlCmpId;

    @Column(name = "URL_USR_CODE")
    private String urlUsrCode;

    @Column(name = "URL_FORME_CODE")
    private Integer urlFormeCode;

}
