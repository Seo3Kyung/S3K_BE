package com.s3k.backend.file.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileResizeService {

  private static final int PROFILE_SIZE = 300;

  public byte[] resizeImageBasic(MultipartFile file, String formatName) throws IOException {
    BufferedImage originalImage = ImageIO.read(file.getInputStream());
    if(originalImage == null) { throw new IllegalArgumentException("유효하지 않은 이미지이다."); }

    int originalWidth = originalImage.getWidth();
    int originalHeight = originalImage.getHeight();
    int cropSize = Math.min(originalWidth, originalHeight);

    int x = (originalWidth - cropSize)/2;
    int y = (originalHeight - cropSize)/2;

    BufferedImage croppedImage = originalImage.getSubimage(x,y,cropSize,cropSize);

    BufferedImage resizedImage = new BufferedImage(PROFILE_SIZE, PROFILE_SIZE, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = resizedImage.createGraphics();

    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    graphics2D.drawImage(croppedImage, 0, 0, PROFILE_SIZE, PROFILE_SIZE, null);
    graphics2D.dispose();

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      ImageIO.write(resizedImage, formatName, outputStream);
      return outputStream.toByteArray();
    }
  }
}
