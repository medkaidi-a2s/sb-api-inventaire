package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;

import java.util.List;

public interface CheckingService {

    List<CommandeResponseDto> getAllPreparedCommandes(String date);

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
            String motif
    ) throws Exception;

}
