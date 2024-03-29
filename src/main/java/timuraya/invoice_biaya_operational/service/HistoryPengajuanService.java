package timuraya.invoice_biaya_operational.service;

import com.itextpdf.html2pdf.HtmlConverter;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import timuraya.invoice_biaya_operational.dto.HistoryPengajuanDto;
import timuraya.invoice_biaya_operational.dto.HistoryPengajuanRequestDto;
import timuraya.invoice_biaya_operational.entity.Biodata;
import timuraya.invoice_biaya_operational.entity.HistoryPengajuan;
import timuraya.invoice_biaya_operational.entity.Pengajuan;
import timuraya.invoice_biaya_operational.repository.BiodataRepository;
import timuraya.invoice_biaya_operational.repository.PengajuanHistoryRepository;
import timuraya.invoice_biaya_operational.repository.PengajuanRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 08/06/22
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryPengajuanService {

    private final PengajuanRepository pengajuanRepository;
    private final PengajuanHistoryRepository pengajuanHistoryRepository;
    private final BiodataRepository biodataRepository;
    private final MapperFacade mapperFacade;
    @Value("${app.home}")
    private String appHomePath;


    public HistoryPengajuanDto createHistoryPengajuan(HistoryPengajuanRequestDto historyPengajuanRequestDto) throws NotFoundException, IOException {

        Optional<Biodata> biodataOptional = biodataRepository.findById(historyPengajuanRequestDto.getBiodataId());
        if(biodataOptional.isEmpty()){
            throw new NotFoundException("Biodata tidak di temukan");
        }

        Optional<Pengajuan> pengajuanOptional = pengajuanRepository.findById(historyPengajuanRequestDto.getPengajuanId());

        if(pengajuanOptional.isEmpty()){
            throw new NotFoundException("Pengajuan tidak di temukan");
        }

        Biodata biodata = biodataOptional.get();
        Pengajuan pengajuan=pengajuanOptional.get();

        HistoryPengajuan historyPengajuan = new HistoryPengajuan();
        historyPengajuan.setBiodata(biodata);
        historyPengajuan.setPengajuan(pengajuan);
        historyPengajuan.setCatatan(historyPengajuanRequestDto.getCatatan());
        historyPengajuan.setStatus(historyPengajuanRequestDto.getStatus());

        if(historyPengajuan.getStatus().equals(HistoryPengajuan.Status.REJECT)){
            pengajuan.setStatus(Pengajuan.Status.REJECT);
        }else {
            pengajuan.setStatus(Pengajuan.Status.getStatusByApproveBy(biodata.getJabatan()));
        }

        if(pengajuan.getStatus().equals(Pengajuan.Status.APPROVED)){
            generateLetter(pengajuan);
        }

        pengajuanRepository.save(pengajuan);

        return mapperFacade.map(pengajuanHistoryRepository.save(historyPengajuan), HistoryPengajuanDto.class);

    }

    public String dateTimeFormat(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return localDateTime.format(formatter);
    }

    public String currencyFormat(int amount){
        Locale usa = new Locale("id", "ID");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(amount);
    }


    public void generateLetter(Pengajuan pengajuan) throws IOException {

        String header = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "</head>\n" +
                "<body><div style=\"background: #f2f2f2; margin: 0px; padding: 0px; font-family: Inter,sans-serif;\">\n" +
                "<div class=\"\" style=\"width: 100%; margin: 0px auto;\">\n" +
                "<div class=\"m_6168105696157522579content\" style=\"background: #fff; padding: 24px; border-top: 0px; border-bottom: 0px;\">\n" +
                "<h1 style=\"margin-top: 15px; font-size: 24px; margin-left:250px;\">INVOICE </h1>\n" +
                "<p style=\"font-weight: 100; font-size: 13px; padding: 0 0px 0px 0; margin: 0; vertical-align: middle;\">No. Pengajuan : "+pengajuan.getNoPengajuan()+"</p>\n" +
                "<p style=\"font-weight: 100; font-size: 13px; padding: 0 0px 0px 0; margin: 0; vertical-align: middle; margin-top:10px;\">Tanggal Pengajuan : "+dateTimeFormat(pengajuan.getTanggalDibuat())+"</p>\n" +
                "<p style=\"font-weight: 100; font-size: 13px; padding: 0 0px 0px 0; margin: 0; vertical-align: middle; margin-top:10px;\">Kegiatan : "+pengajuan.getKegiatan()+"</p>\n" +
                "<p style=\"font-weight: 600; font-size: 18px; padding: 0 0px 0px 0; margin: 0; vertical-align: middle;\">&nbsp;</p>\n" +
                "\n" +
                "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td align=\"left\">\n" +
                "\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table style=\"margin-top: 16px; height: 18px; width: 100%;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tbody>\n" +
                "<tr style=\"height: 18px;\">\n" +
                "<td style=\"height: 18px;\"><strong style=\"font-size: 13px;\">Detail Pengajuan :</strong><hr style=\"border: none; border-bottom: 1px solid #FFFFFF; margin: 7px 0; margin-top: 6px;\" /></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "\n" +
                "<table style=\"height: 10px; width: 100%; border-collapse: collapse;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tbody>\n" +
                "<tr style=\"height: 36px; border-bottom: 1px solid #e9eceb;\">\n" +
                "\n" +
                "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                "<strong style=\"font-size: 15px; color: #00000; display: inline-block;\">No.</strong>\n" +
                "</td>\n" +
                "\n" +
                "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                "<strong style=\"font-size: 15px; color: #00000; display: inline-block;\">Kode</strong>\n" +
                "</td>\n" +
                "\n" +
                "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                "<strong style=\"font-size: 15px;  color: #00000; display: inline-block;\">Item/Nama</strong>\n" +
                "</td>\n" +
                "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                "<strong style=\"font-size: 15px; color: #00000; display: inline-block;\">Qty</strong>\n" +
                "</td>\n" +
                "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                "<strong style=\"font-size: 15px; color: #00000; display: inline-block;\">Harga</strong>\n" +
                "</td>\n" +
                "<td style=\"height: 36px; width: 10%;\" align=\"right\"><span style=\"font-size: 15px; color: #333; display: inline-block;\"><strong>Total</strong></span></td>\n" +
                "</tr>";

                String footer = "\n" +
                        "</tbody>\n" +
                        "</table>\n" +
                        "<br />\n" +
                        "<table style=\"margin-top: 16px; height: 18px; width: 100%;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                        "<tbody>\n" +
                        "\n" +
                        "\n" +
                        "</tbody>\n" +
                        "</table>\n" +
                        "<table style=\"height: 32px; width: 99.4286%; border-collapse: collapse;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                        "<tbody>\n" +
                        "\n" +
                        "<tr style=\"height: 10px; border-bottom: 1px solid #e9eceb;\">\n" +
                        "<td style=\"height: 10px; width: 28.246%; vertical-align: middle;\" align=\"left\"><span style=\"color: #000000;\"><strong><span style=\"color: black;\"><span style=\"font-size: 13px; margin-left:50px\">Total</span></span></strong></span></td>\n" +
                        "<td style=\"height: 36px; width: 56%;\" align=\"right\"><span style=\"font-size: 12px; color: #333; display: inline-block; margin-right:50px;\"><strong>"+currencyFormat(pengajuan.getJumlah().intValue())+"</strong></span></td>\n" +
                        "</tr>\n" +
                        "\n" +
                        "</tbody>\n" +
                        "\n" +
                        "<tbody>\n" +
                        "\n" +
                        "<tr style=\"height: 10px; margin-top:10px;\">\n" +
                        "\n" +
                        "<td style=\"height: 10px; width: 28.246%; vertical-align: middle;\" align=\"left\"><span style=\"color: #000000;\"><strong><span style=\"color: black;\"><span style=\"font-size: 13px; margin-left:50px\"></span></span></strong></span></td>\n" +
                        "\n" +
                        "\n" +
                        "<td style=\" width: 56%;\" align=\"right\"><span style=\"font-size: 12px; color: #333; display: inline-block; margin-right:50px; margin-top:100px;\"><strong>Mengetahui</strong></span></td>\n" +
                        "</tr>\n" +
                        "\n" +
                        "<tr style=\" margin-top:30px;\">\n" +
                        "<td style=\"height: 10x; width: 28.246%; vertical-align: middle;\" align=\"left\"><span style=\"color: #000000;\"><span><span style=\"color: black;\"><span style=\"font-size: 9px; \"></span></span></span></span></td>\n" +
                        "<td style=\" width: 56%;\" align=\"right\"><span style=\"font-size: 12px; color: #333; display: inline-block; margin-right:60px;\"><strong><img src=\"src/main/resources/ttd-barcode.png\" style=\"height:50px;width:50px;\"/></strong></span></td>\n" +
                        "</tr>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "<tr style=\" margin-top:30px;\">\n" +
                        "<td style=\"height: 10x; width: 28.246%; vertical-align: middle;\" align=\"left\"><span style=\"color: #000000;\"><span><span style=\"color: black;\"><span style=\"font-size: 9px; \">Note : Bukti untuk pengajuan ke kasir</span></span></span></span></td>\n" +
                        "<td style=\" width: 56%;\" align=\"right\"><span style=\"font-size: 12px; color: #333; display: inline-block; margin-right:60px;\"><strong>Nur Laila</strong></span></td>\n" +
                        "</tr>\n" +
                        "\n" +
                        "<tr style=\" \">\n" +
                        "<td style=\" width: 28.246%; vertical-align: middle;\" align=\"left\"><span style=\"color: #000000;\"><strong><span style=\"color: black;\"><span style=\"font-size: 13px; margin-left:50px\"></span></span></strong></span></td>\n" +
                        "<td style=\" width: 56%;\" align=\"right\"><span style=\"font-size: 10px; color: #333; display: inline-block; margin-right:45px;\"><span>Kepala Keuangan</span></span></td>\n" +
                        "</tr>\n"+
                        "</tbody>\n" +
                        "</table>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div></body></html>";

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(header);
                AtomicInteger index = new AtomicInteger(1);
                pengajuan.getItems().forEach(item-> {
                            stringBuilder.append("<tr style=\"height: 36px; border-bottom: 1px solid #e9eceb;\">\n" +
                                    "\n" +
                                    "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                                    "<p style=\"font-size: 15px; color: #00000; display: inline-block;\">"+index.get()+"</p>\n" +
                                    "</td>\n" +
                                    "\n" +
                                    "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                                    "<p style=\"font-size: 15px; color: #00000; display: inline-block;\">"+UUID.randomUUID().toString().substring(0,6)+"</p>\n" +
                                    "</td>\n" +
                                    "\n" +
                                    "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                                    "<p style=\"font-size: 15px;  color: #00000; display: inline-block;\">"+item.getNama()+"</p>\n" +
                                    "</td>\n" +
                                    "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                                    "<p style=\"font-size: 15px; color: #00000; display: inline-block;\">"+item.getQty()+"</p>\n" +
                                    "</td>\n" +
                                    "<td style=\"height: 36px; width: 10%; vertical-align: middle;\" align=\"left\">\n" +
                                    "<p style=\"font-size: 15px; color: #00000; display: inline-block;\">"+currencyFormat(item.getHarga().intValue())+"</p>\n" +
                                    "</td>\n" +
                                    "<td style=\"height: 36px; width: 10%;\" align=\"right\"><span style=\"font-size: 15px; color: #333; display: inline-block;\"><p>"+currencyFormat(item.getTotal().intValue())+"</p></span></td>\n" +
                                    "</tr>");
                            index.addAndGet(1);

                        });

                stringBuilder.append(footer);

        String fileName = "/letter/"+ UUID.randomUUID()+".pdf";

        HtmlConverter.convertToPdf(stringBuilder.toString(), new FileOutputStream(appHomePath+fileName));

        pengajuan.setLetterFile(fileName);

    }


}
