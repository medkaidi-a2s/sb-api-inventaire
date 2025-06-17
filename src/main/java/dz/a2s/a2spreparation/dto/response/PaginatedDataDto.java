package dz.a2s.a2spreparation.dto.response;

import java.util.List;

public record PaginatedDataDto<T>(List<T> data, int totalRecords, int totalPages, int currentPage, int pageSize) {
}
