package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.controle.response.BonCommandeZoneDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteZoneDto;

import java.util.List;

public interface CheckingService {

    List<CommandeResponseDto> getAllPreparedCommandes(String date);

    List<CommandeZoneResponseDto> getAllPreparedCommandesZone(String date);

    List<BonCommandeZoneDto> getPreparedBonCommandesZone(String date);

    Integer startControleCde(
            int p_vnt_cmp_id,
            int p_vnt_id,
            String p_vnt_type,
            String p_vnt_stk_code
    ) throws Exception;

    Integer setControlledQuantity(
            Integer cmpId,
            Integer id,
            String type,
            String stkCode,
            Integer no,
            Integer qte,
            Integer motif
    ) throws Exception;

    Integer setCommandeControlled(CmdIdDto id) throws Exception;

    Integer startControleZone(
            int v_vbz_cmp_id,
            int v_vbz_vnt_id,
            String v_vbz_vnt_type,
            String v_vbz_stk_code,
            int v_vbz_zone
    ) throws Exception;

    Integer setControlledQuantityZone(LigneQteZoneDto ligne) throws Exception;

    Integer setCommandeZoneControlled(CmdZoneIdDto id) throws Exception;

}
