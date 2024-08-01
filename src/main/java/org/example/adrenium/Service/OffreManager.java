package org.example.adrenium.Service;

import org.example.adrenium.Dao.Entities.Offre;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

 @Service
public interface OffreManager {
    Optional<Offre> getOffreById(int id);
    List<Offre> getAllOffres();
    Offre addOffre(Offre offre);
    Offre updateOffre(Offre offre);
    boolean deleteOffre(int id);
    Page<Offre> getAllOffre2(int page, int size);
    Page<Offre> searchOffre(String keyword, int page, int size);
    List<Offre> getByKeyword(String keyword);

}
