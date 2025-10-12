package dz.a2s.a2sinventaire.services;

import dz.a2s.a2sinventaire.dto.CommandeResponseDto;
import dz.a2s.a2sinventaire.dto.CommandeZoneResponseDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdColisageDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2sinventaire.dto.controle.response.BonCommandeZoneDto;
import dz.a2s.a2sinventaire.dto.preparation.LigneQteZoneDto;

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

    Integer setCommandeControlled(CmdColisageDto data) throws Exception;

    Integer startControleZone(
            int v_vbz_cmp_id,
            int v_vbz_vnt_id,
            String v_vbz_vnt_type,
            String v_vbz_stk_code,
            int v_vbz_zone
    ) throws Exception;

    Integer startControleCommandeZone(
            Integer v_vbz_cmp_id,
            Integer v_vbz_vnt_id,
            String v_vbz_vnt_type,
            String v_vbz_stk_code
    );

    Integer setControlledQuantityZone(LigneQteZoneDto ligne) throws Exception;

    Integer setCommandeZoneControlled(CmdZoneColisageDto data) throws Exception;

    Integer setCommandeZoneGlobalControlled(CmdColisageDto data);

}
