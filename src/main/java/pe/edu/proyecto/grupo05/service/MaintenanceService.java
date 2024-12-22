package pe.edu.proyecto.grupo05.service;

import pe.edu.proyecto.grupo05.dto.ZapatillaDetailDto;
import pe.edu.proyecto.grupo05.dto.ZapatillaDto;
import pe.edu.proyecto.grupo05.dto.ZapatillaEditDto;

import java.util.List;

public interface MaintenanceService {

    List<ZapatillaDto> findAllZapatillas();

    ZapatillaDetailDto findDetailById(int id);

    ZapatillaEditDto findDetailEditbyId(int id);

    Boolean updateZapatilla(ZapatillaEditDto zapatillaEditDto);

    Boolean createZapatilla(ZapatillaEditDto zapatillaEditDto);

    Boolean deleteZapatilla(int id) throws Exception;



}
