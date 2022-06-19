package timuraya.invoice_biaya_operational.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import timuraya.invoice_biaya_operational.dto.PengajuanDto;
import timuraya.invoice_biaya_operational.dto.PengajuanRequestDto;
import timuraya.invoice_biaya_operational.dto.ValidasiPengajuanDto;
import timuraya.invoice_biaya_operational.entity.Item;
import timuraya.invoice_biaya_operational.entity.Pengajuan;
import timuraya.invoice_biaya_operational.repository.ItemRepository;
import timuraya.invoice_biaya_operational.repository.PengajuanRepository;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 08/06/22
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class PengajuanService {

    private final PengajuanRepository pengajuanRepository;
    private final ItemRepository itemRepository;
    private final MapperFacade mapperFacade;
    public String generateInvoiceNumber(){
        String prefix="INV";
        String slash = "/";
        LocalDateTime date = LocalDateTime.now();
        String year = ""+date.getYear();

        return prefix+slash+year+slash+UUID.randomUUID().toString().substring(0,8);
    }

    @Transactional
    public PengajuanDto createPengajuan(PengajuanRequestDto pengajuanRequestDto) {

        Pengajuan pengajuan = mapperFacade.map(pengajuanRequestDto,Pengajuan.class);
        pengajuan.setJumlah(BigDecimal.valueOf(pengajuan.getItems().stream().mapToInt(data->data.getHarga().intValue()).sum()));
        pengajuan.setNoPengajuan(generateInvoiceNumber());
        Pengajuan pengajuanSave =  pengajuanRepository.save(pengajuan);
        pengajuan.getItems().forEach(data-> {
            data.setPengajuan(pengajuanSave);
            Item item = mapperFacade.map(data,Item.class);
            itemRepository.save(item);
        });

        pengajuan.setStatus(Pengajuan.Status.SUB);

        return mapperFacade.map(pengajuan,PengajuanDto.class);
    }

    public List<PengajuanDto> getPengajuan(PengajuanRequestDto pengajuanRequestDto) {
        GenericSpecificationService<Pengajuan> specificationService = new GenericSpecificationService<>();
        specificationService.add(new SearchCriteria<>("kegiatan",pengajuanRequestDto.getKegiatan(), SearchCriteria.SearchOperation.EQUAL));
        specificationService.add(new SearchCriteria<>("keterangan",pengajuanRequestDto.getKegiatan(), SearchCriteria.SearchOperation.EQUAL));
        specificationService.add(new SearchCriteria<>("jumlah",pengajuanRequestDto.getJumlah(), SearchCriteria.SearchOperation.EQUAL));
        specificationService.add(new SearchCriteria<>("divisi",pengajuanRequestDto.getDivisi(), SearchCriteria.SearchOperation.EQUAL));
        specificationService.add(new SearchCriteria<>("noPengajuan",pengajuanRequestDto.getNoPengajuan(), SearchCriteria.SearchOperation.EQUAL));
        return pengajuanRepository.findAll(specificationService)
                .stream().map(data->mapperFacade.map(data,PengajuanDto.class))
                .collect(Collectors.toList());
    }

    public PengajuanDto getPengajuanById(Long id) throws NotFoundException {
        return pengajuanRepository.findById(id).map(data->mapperFacade.map(data,PengajuanDto.class))
                .orElseThrow(()->new NotFoundException("Pengajuan tidak di temukan"));
    }

    @Transactional
    public PengajuanDto updatePengajuan(Long id, PengajuanRequestDto pengajuanRequestDto) throws NotFoundException {
        return pengajuanRepository.findById(id).map(data-> {
            data.setKegiatan(pengajuanRequestDto.getKegiatan());
            data.setKeterangan(pengajuanRequestDto.getKeterangan());
            data.setDivisi(pengajuanRequestDto.getDivisi());
            pengajuanRepository.save(data);
            itemRepository.deleteAll(data.getItems());
            pengajuanRequestDto.getItems().forEach(item-> {
                Item itemUpdate = mapperFacade.map(item,Item.class);
                itemUpdate.setPengajuan(data);
                itemRepository.save(itemUpdate);
            });
            data.setItems(pengajuanRequestDto.getItems().stream()
                    .map(item-> mapperFacade.map(item,Item.class)).collect(Collectors.toList()));

            return mapperFacade.map(data,PengajuanDto.class);
        }).orElseThrow(()-> new NotFoundException(""));
    }

    public void deletePengajuan(Long id) {
         pengajuanRepository.findById(id).ifPresent(pengajuanRepository::delete);
    }


    public byte[] getLetter(Long id) throws NotFoundException {
       return pengajuanRepository.findById(id).map(data-> {
           InputStream in = null;
           try {
               in = new FileInputStream(data.getLetterFile());
               var byteAarray = IOUtils.toByteArray(in);
               return addQRCode(byteAarray,"http://localhost:8080/pengajuan/"+data.getId()+"/validate",QRCodePosition.TOP_RIGHT);
           } catch (IOException e) {
                log.error(e.getMessage());
            }
            return null;
        }).orElseThrow(()-> new NotFoundException(""));
    }

    private static byte[] writeLogo() throws IOException {
        var is= new ClassPathResource("logo-template.png").getInputStream();

        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        int i;
        byte[] data = new byte[1024];
        while ((i = is.read(data, 0, data.length)) != -1) {
            bis.write(data, 0, i);
        }

        bis.flush();
        is.close();

        return bis.toByteArray();
    }


    private static byte[] newQRCodeImage(String barcodeText) {
        try (ByteArrayOutputStream image = new ByteArrayOutputStream()) {
            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 100, 100);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", image);
            return image.toByteArray();
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error on generating QR code", e);
        }
    }

    public static byte[] addQRCode(byte[] pdfBytes, String barcodeText, QRCodePosition position) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfReader reader = new PdfReader(pdfBytes);

            PdfStamper stamper = new PdfStamper(reader, os);

            Image image = Image.getInstance(newQRCodeImage(barcodeText));

            Image logoImage = Image.getInstance(writeLogo());
            logoImage.scaleToFit(50,50);

            float x = (PageSize.A4.getWidth() - image.getScaledWidth()) / 2+10;
            float y = (PageSize.A4.getHeight() - 60);

            image.setAbsolutePosition(x, y);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                PdfContentByte content = stamper.getOverContent(i);
                image.setAbsolutePosition(position.getAbsoluteX(), position.getAbsoluteY());
                logoImage.setAbsolutePosition(x,y);
                content.addImage(image);
                content.addImage(logoImage);
            }

            stamper.close();
            reader.close();
            return os.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error on writing QR code", e);
        }
    }

    public InputStreamResource downloadLetter(long id) throws NotFoundException {
        return pengajuanRepository.findById(id).map(data-> {
            InputStream in = null;
            try {
                in = new FileInputStream(data.getLetterFile());
                var byteAarray = IOUtils.toByteArray(in);
                byte[] bytes = addQRCode(byteAarray,"http://localhost:8080/pengajuan/letter/"+data.getId()+"/validate",QRCodePosition.TOP_RIGHT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                return new InputStreamResource(byteArrayInputStream);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            return null;
        }).orElseThrow(()-> new NotFoundException(""));
    }

    public ValidasiPengajuanDto validasiPengajuan(long id) throws NotFoundException {
       return pengajuanRepository.findById(id).map(data-> {
            if(data.getStatus().equals(Pengajuan.Status.APPROVED) && !data.isValidate()){
                data.setValidate(true);
                ValidasiPengajuanDto validasiPengajuanDto =  mapperFacade.map(pengajuanRepository.save(data),ValidasiPengajuanDto.class);
                validasiPengajuanDto.setStatus("VALID");
                validasiPengajuanDto.setAmount(data.getJumlah());
                return validasiPengajuanDto;
            }else {
                ValidasiPengajuanDto validasiPengajuanDto =  mapperFacade.map(pengajuanRepository.save(data),ValidasiPengajuanDto.class);
                validasiPengajuanDto.setStatus("NOT VALID");
                validasiPengajuanDto.setAmount(data.getJumlah());
                return validasiPengajuanDto;
            }
        }).orElseThrow(()-> new NotFoundException("Data tidak di temukan"));
    }

    public enum QRCodePosition {
        TOP_LEFT(0f, 700f),
        CENTER(400f, 700),
        TOP_RIGHT(480f, 750f),
        BOTTOM_LEFT(0f, 0f),
        BOTTOM_RIGHT(500f, 0f);

        private final float absoluteX;
        private final float absoluteY;

        QRCodePosition(float absoluteX, float absoluteY) {
            this.absoluteX = absoluteX;
            this.absoluteY = absoluteY;
        }

        public float getAbsoluteX() {
            return absoluteX;
        }

        public float getAbsoluteY() {
            return absoluteY;
        }
    }

}
