package com.esprit.microservice.mini_projet.service;

import com.esprit.microservice.mini_projet.models.Livraison;
import com.esprit.microservice.mini_projet.models.Livreur;
import com.esprit.microservice.mini_projet.repository.LivraisonRepo;
import com.esprit.microservice.mini_projet.repository.LivreurRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LivraisonService {

    @Autowired
    private LivreurRepo livreurRepo;

    @Autowired
    private LivraisonRepo livraisonRepo;

    public List<Livraison> getAllLivraisons() {
        return livraisonRepo.findAll();
    }

    public Page<Livraison> getAllLivraisons(Pageable pageable) {
        return livraisonRepo.findAll(pageable);
    }

    public Optional<Livraison> getLivraisonById(Integer id) {
        return livraisonRepo.findById(id);
    }

    public Livraison createLivraison(Livraison livraison) {
        Livreur livreur = livraison.getLivreur();
        if (livreur.getId() == null) {
            livreur = livreurRepo.save(livreur);
        }
        livraison.setLivreur(livreur);
        return livraisonRepo.save(livraison);
    }

    public Optional<Livraison> updateLivraison(int id, Livraison livraisonDetails) {
        return livraisonRepo.findById(id).map(livraison -> {
            livraison.setDateCreation(livraisonDetails.getDateCreation());
            livraison.setDateLivree(livraisonDetails.getDateLivree());
            livraison.setStatusLivraison(livraisonDetails.getStatusLivraison());
            livraison.setLivreur(livraisonDetails.getLivreur());
            return livraisonRepo.save(livraison);
        });
    }

    public boolean deleteLivraison(Integer id) {
        return livraisonRepo.findById(id).map(livraison -> {
            livraisonRepo.delete(livraison);
            return true;
        }).orElse(false);
    }

    public Optional<Livraison> assignLivreurToLivraison(Integer livraisonId, Integer livreurId) {
        Optional<Livraison> livraisonOpt = livraisonRepo.findById(livraisonId);
        Optional<Livreur> livreurOpt = livreurRepo.findById(livreurId);

        if (livraisonOpt.isPresent() && livreurOpt.isPresent()) {
            Livraison livraison = livraisonOpt.get();
            Livreur livreur = livreurOpt.get();

            livraison.setLivreur(livreur);
            return Optional.of(livraisonRepo.save(livraison));
        } else {
            return Optional.empty();
        }
    }

    public ByteArrayInputStream exportLivraisonsToCsv(Integer livreurId, Date start, Date end) {
        List<Livraison> filtered;

        if (livreurId != null && start != null && end != null) {
            filtered = livraisonRepo.findByLivreur_IdAndDateCreationBetween(livreurId, start, end);
        } else if (start != null && end != null) {
            filtered = livraisonRepo.findByDateCreationBetween(start, end);
        } else {
            filtered = livraisonRepo.findAll();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        writer.println("ID,DateCreation,DateLivree,StatusLivraison,LivreurNom");

        for (Livraison l : filtered) {
            writer.printf("%d,%s,%s,%s,%s %s\n",
                    l.getId(),
                    l.getDateCreation(),
                    l.getDateLivree(),
                    l.getStatusLivraison(),
                    l.getLivreur() != null ? l.getLivreur().getNom() : "",
                    l.getLivreur() != null ? l.getLivreur().getPrenom() : ""
            );
        }

        writer.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream exportLivraisonsToPdf(Integer livreurId, Date start, Date end) {
        List<Livraison> filtered;

        if (livreurId != null && start != null && end != null) {
            filtered = livraisonRepo.findByLivreur_IdAndDateCreationBetween(livreurId, start, end);
        } else if (start != null && end != null) {
            filtered = livraisonRepo.findByDateCreationBetween(start, end);
        } else {
            filtered = livraisonRepo.findAll();
        }

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontHeader.setSize(18);
            Paragraph title = new Paragraph("Livraison Report", fontHeader);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Spacer

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 2, 2, 3});

            Stream.of("ID", "Date Création", "Date Livrée", "Statut", "Livreur")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell();
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setPhrase(new Phrase(header));
                        table.addCell(cell);
                    });

            for (Livraison l : filtered) {
                table.addCell(String.valueOf(l.getId()));
                table.addCell(String.valueOf(l.getDateCreation()));
                table.addCell(String.valueOf(l.getDateLivree()));
                table.addCell(l.getStatusLivraison());
                table.addCell(l.getLivreur() != null ?
                        l.getLivreur().getNom() + " " + l.getLivreur().getPrenom() : "N/A");
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public Optional<Livraison> markAsLivree(Integer id) {
        return livraisonRepo.findById(id).map(livraison -> {
            livraison.setStatusLivraison("Livrée");
            livraison.setDateLivree(new Date(System.currentTimeMillis()));
            return livraisonRepo.save(livraison);
        });
    }
}
