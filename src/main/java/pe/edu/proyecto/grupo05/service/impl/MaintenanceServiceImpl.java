package pe.edu.proyecto.grupo05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.proyecto.grupo05.dto.ZapatillaDetailDto;
import pe.edu.proyecto.grupo05.dto.ZapatillaDto;
import pe.edu.proyecto.grupo05.dto.ZapatillaEditDto;
import pe.edu.proyecto.grupo05.entity.Zapatilla;
import pe.edu.proyecto.grupo05.repository.CategoriaRepository;
import pe.edu.proyecto.grupo05.repository.MarcaRepository;
import pe.edu.proyecto.grupo05.repository.ZapatillaRepository;
import pe.edu.proyecto.grupo05.service.MaintenanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    ZapatillaRepository zapatillaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    CategoriaRepository categoriaRepository;



    @Override
    public List<ZapatillaDto> findAllZapatillas() {

        List<ZapatillaDto> zapatillas = new ArrayList<ZapatillaDto>();
        Iterable<Zapatilla> iterable = zapatillaRepository.findAll();
        iterable.forEach(zapatilla -> {

            ZapatillaDto zapatillaDto = new ZapatillaDto(
                    zapatilla.getZapatillaId(),
                    zapatilla.getModelo(),
                    zapatilla.getMarca().getMarca(),
                    zapatilla.getCategoria().getCategoria(),
/*                    zapatilla.getDescripcion(),*/
                    zapatilla.getPrecio(),
                    zapatilla.getStock(),
                    zapatilla.getUrlimg());
            zapatillas.add(zapatillaDto);

        });
        return zapatillas;
    }

    @Override
    public ZapatillaDetailDto findDetailById(int id) {

        Optional<Zapatilla> optional = zapatillaRepository.findById(id);
        return optional.map(
        zapatilla -> new ZapatillaDetailDto(
                    zapatilla.getZapatillaId(),
                    zapatilla.getModelo(),
                    zapatilla.getMarca().getMarca(),
                    zapatilla.getCategoria().getCategoria(),
                    zapatilla.getDescripcion(),
                    zapatilla.getPrecio(),
                    zapatilla.getStock(),
                    zapatilla.getUrlimg())
        ).orElse(null);

    }


    @Override
    public ZapatillaEditDto findDetailEditbyId(int id) {

        Optional<Zapatilla> optional = zapatillaRepository.findById(id);
        return optional.map(
                zapatilla -> new ZapatillaEditDto(
                        zapatilla.getZapatillaId(),
                        zapatilla.getModelo(),
                        zapatilla.getMarca().getMarcaId(),
                        zapatilla.getCategoria().getCategoriaId(),
                        zapatilla.getDescripcion(),
                        zapatilla.getPrecio(),
                        zapatilla.getStock(),
                        zapatilla.getUrlimg())
        ).orElse(null);

    }


    public Boolean createZapatilla(ZapatillaEditDto zapatillaEditDto) {
        Zapatilla zapatilla = new Zapatilla();

        zapatilla.setModelo(zapatillaEditDto.modelo());
        zapatilla.setMarca(marcaRepository.findById(zapatillaEditDto.marcaId()).orElse(null));
        zapatilla.setCategoria(categoriaRepository.findById(zapatillaEditDto.categoriaId()).orElse(null));
        zapatilla.setDescripcion(zapatillaEditDto.descripcion());
        zapatilla.setPrecio(zapatillaEditDto.precio());
        zapatilla.setStock(zapatillaEditDto.stock());
        zapatilla.setUrlimg(zapatillaEditDto.urlimg());
        zapatillaRepository.save(zapatilla);

        return true;

    }


    @Override
    public Boolean updateZapatilla(ZapatillaEditDto zapatillaEditDto) {
        Optional<Zapatilla> optional = zapatillaRepository.findById(zapatillaEditDto.zapatillaId());
        return optional.map(
                zapatilla -> {
                    zapatilla.setModelo(zapatillaEditDto.modelo());
                    zapatilla.setMarca(marcaRepository.findById(zapatillaEditDto.marcaId()).orElse(null));
                    zapatilla.setCategoria(categoriaRepository.findById(zapatillaEditDto.categoriaId()).orElse(null));
                    zapatilla.setDescripcion(zapatillaEditDto.descripcion());
                    zapatilla.setPrecio(zapatillaEditDto.precio());
                    zapatilla.setStock(zapatillaEditDto.stock());
                    zapatilla.setUrlimg(zapatillaEditDto.urlimg());
                    zapatillaRepository.save(zapatilla);
                    return true;


                }
        ).orElse(false);
    }




    public Boolean deleteZapatilla(int id) {

        Optional<Zapatilla> optional = zapatillaRepository.findById(id);
        return optional.map(zapatilla -> {
            zapatillaRepository.delete(zapatilla);
            return true;
        }).orElse(false);

    }

}

