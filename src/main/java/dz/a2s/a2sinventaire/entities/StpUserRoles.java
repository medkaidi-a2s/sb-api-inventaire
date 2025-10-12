package dz.a2s.a2sinventaire.entities;

import dz.a2s.a2sinventaire.entities.keys.StpUserRolesId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "STP_USER_ROLES")
public class StpUserRoles {

    @Id
    @EmbeddedId
    private StpUserRolesId id;

    @Column(name = "URL_LECTURE")
    private Integer urlLecture;

    @Column(name = "URL_INSERT")
    private Integer urlInsert;

    @Column(name = "URL_UPDATE")
    private Integer urlUpdate;

    @Column(name = "URL_DELETE")
    private Integer urlDelete;

    @Column(name = "URL_CREER_USER")
    private String urlCreerUser;

    @Column(name = "URL_CREER_DATE")
    private Date urlCreerDate;

    @Column(name = "URL_MODI_USER")
    private String urlModiUser;

    @Column(name = "URL_MODI_DATE")
    private Date urlModiDate;

    @Column(name = "URL_IMPRIME")
    private Integer urlImprime;

    @Column(name = "URL_EXECUTE")
    private Integer urlExecute;

    @Column(name = "URL_SYS_ID")
    private Integer urlSysId;

    @Column(name = "URL_EXPORT")
    private Integer urlExport;

}
