package dz.a2s.a2sinventaire.entities;

import dz.a2s.a2sinventaire.entities.keys.DiMessagesId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "DI_MESSAGES")
public class DiMessages {

    @Id
    @EmbeddedId
    private DiMessagesId id;

    @Column(name = "MSG_TYPE")
    private Integer msgType;

    @Column(name = "MSG_MDL_ID")
    private String msgMdlId;

    @Column(name = "MSG_DESC_LOC")
    private String msgDescLoc;

    @Column(name = "MSG_DESC_ETR")
    private String msgDescEtr;

    @Column(name = "MSG_ACTIVE_FLAG")
    private Integer msgActiveFlag;

    @Column(name = "MSG_INACTIVE_DATE")
    private Date msgInactiveDate;

    @Column(name = "MSG_INACTIVE_RAISON")
    private String msgInactiveRaison;

    @Column(name = "MSG_CREER_PAR")
    private String msgCreerPar;

    @Column(name = "MSG_CREER_DATE")
    private Date msgCreerDate;

    @Column(name = "MSG_MODIFIER_PAR")
    private String msgModifierPar;

    @Column(name = "MSG_MODIFIER_DATE")
    private Date msgModifierDate;

}
