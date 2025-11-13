package com.ggames.GGames.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Kezeli a védett, bejelentkezést igénylő bináris erőforrások (pl. képek) kiszolgálását.
 *
 * <p>Ez a kontroller felelős a tartalomstreamelésért olyan erőforrások esetén, amelyek a Spring Security által védett elérési útvonalon találhatók.</p>
 *
 * @author [Ide írhatod az osztály szerzőjét]
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class ResourceController {

    /**
     * Erőforrásbetöltő segédosztály a fájlok eléréséhez, tipikusan a classpath-on belül.
     */
    private final ResourceLoader resourceLoader;

    /**
     * Bináris képfolyamot szolgáltat egy VÉDETT mappából a bejelentkezett felhasználóknak.
     *
     * <p>A metódus ellenőrzi a felhasználó hitelesítését, megkeresi az erőforrást a {@code classpath:private/images/} mappában,
     * és meghatározza a megfelelő {@code Content-Type} fejlécet.</p>
     *
     * @param imageName A kért kép fájlneve.
     * @return {@code ResponseEntity<Resource>} a kért erőforrással és a tartalomtípussal (200 OK),
     * vagy 404 NOT FOUND, illetve 500 INTERNAL SERVER ERROR státusszal.
     */
    @GetMapping("/protected-images/{imageName}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<Resource> getProtectedImage(@PathVariable String imageName) {

        if (imageName == null || imageName.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            String resourcePath = "classpath:private/images/" + imageName;
            Resource resource = resourceLoader.getResource(resourcePath);

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String filename = resource.getFilename();
            MediaType contentType;

            if (filename != null && filename.toLowerCase().endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG;
            } else if (filename != null && (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg"))) {
                contentType = MediaType.IMAGE_JPEG;
            } else {
                contentType = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(resource);

        } catch (Exception e) {
            System.err.println("Error serving protected image: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}