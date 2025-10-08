package repository;

import dto.CarreraReporteDTO;

import java.util.List;

public interface CarreraRepository {
    void cargarDesdeCSV();
    List<CarreraReporteDTO> reporteCarreras();
}
