package dz.a2s.a2sinventaire.dto.response;

import java.util.List;

public record PaginatedResponse<T>(int status, String message, List<T> content, int totalRecords, int totalPages, int currentPage, int pageSize){

}
