package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.*;
import dz.a2s.a2spreparation.dto.preparation.request.AddLotRequest;
import dz.a2s.a2spreparation.dto.preparation.request.ReplaceLotRequest;
import dz.a2s.a2spreparation.dto.preparation.response.ProductLotDto;
import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.Motif;

import java.util.List;

public interface PreparationService {

    List<CommandeResponseDto> getCommandes(String date);

    List<PrpCmdPrlvUsrCodeDto> getCommandesPrlv(String date);

    Integer startPreparePrlv(int p_cmp,
                             int p_slt_id,
                             String p_slt_type,
                             int p_slt_annee) throws Exception;

    Integer startPrepareCde(
            int p_vnt_cmp_id,
            int p_vnt_id,
            String p_vnt_type,
            String p_vnt_stk_code
    ) throws Exception;

    Integer startPrepareZone(
            int v_vbz_cmp_id,
            int v_vbz_vnt_id,
            String v_vbz_vnt_type,
            String v_vbz_stk_code,
            int v_vbz_zone
    ) throws Exception;


    PrpCmdPrlvUsrCodeDto getOneCmdPrlv(Integer id, String type, Integer annee);

    List<LignePrlvDto> getDetailsVentePrlv(StkListesId id);

    List<LigneDto> getDetailsVente(VenteId id);

    List<LigneZoneDto> getDetailsVenteZone(CmdZoneIdDto id);

    Integer setPreparedQuantity(
            Integer cmpId,
            Integer id,
            String type,
            String stkCode,
            Integer no,
            Integer qte,
            Integer motif
    ) throws Exception;

    Integer setPreparedQuantityZone(LigneQteZoneDto ligne) throws Exception;

    List<Motif> getAllMotif();

    Integer setCommandePrepared(CmdIdDto id) throws Exception;

    Integer setCommandeZonePrepared(CmdZoneIdDto id) throws Exception;

    CommandeReceiptData getReceiptParCommande(CmdIdDto id);

    CommandeReceiptData getReceiptParZone(CmdZoneIdDto id);

    Integer deleteLigneCommande(LigneVenteDto ligne);
    Integer editQuantityCommande(LigneQteDto ligne);
    List<ProductLotDto> getAvailableLots(Integer cmpId, Integer medId, Integer oldLotId, Integer qte);
    Integer replaceProductLot(ReplaceLotRequest request);
    Integer addProductLot(AddLotRequest request);

}
