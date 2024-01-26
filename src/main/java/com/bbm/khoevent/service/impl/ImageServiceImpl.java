package com.bbm.khoevent.service.impl;

import com.bbm.khoevent.exception.BadRequestException;
import com.bbm.khoevent.exception.EntityNotFoundException;
import com.bbm.khoevent.model.Event;
import com.bbm.khoevent.model.Image;
import com.bbm.khoevent.repository.ImageRepository;
import com.bbm.khoevent.service.EventService;
import com.bbm.khoevent.service.ImageService;
import com.bbm.khoevent.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final EventService eventService;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public String upload(MultipartFile file, Long eventId) {
        Event event = eventService.getEventById(eventId);
        String contentType = file.getContentType();
        boolean isAnImageFile = ImageUtils.isValidImageFile(contentType);
        if (isAnImageFile) {
            try {
                Image image = Image.builder()
                        .originalFileName(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .image(compressBytes(file.getBytes()))
                        .event(event)
                        .build();
                imageRepository.save(image);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                throw new BadRequestException("Não foi possível salvar a Imagem: " + file.getOriginalFilename() +
                        " Ocorreu um erro interno. Por favor tente novamente. Caso o erro persista contacte o ADMIN!");
            }
        } else {
            throw new BadRequestException("Tipo de ficheiro inválido");
        }
        return "A imagem foi salva com sucesso!";
    }

    @Transactional
    public Image download(Long userId) {
        var img = imageRepository.findByEventId(userId).orElseThrow(() ->
                new EntityNotFoundException("Não foi possível fazer o download da imagem!"));

        return Image.builder()
                .id(img.getId())
                .originalFileName(img.getOriginalFileName())
                .fileType(img.getFileType())
                .image(decompressBytes(img.getImage()))
                .build();
    }

    @Transactional
    public void delete(Long eventId) {
        var img = imageRepository.findByEventId(eventId).orElseThrow(() ->
                new EntityNotFoundException("Não foi possível eliminar a imagem!"));
        imageRepository.delete(img);
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new BadRequestException("Erro ao Salvar Imagem");
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ioe) {
            System.out.println(ioe.getMessage());
            throw new BadRequestException("Ocorreu um erro interno ao carregar a imagem");
        }
        return outputStream.toByteArray();
    }
}
