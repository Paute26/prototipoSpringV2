package com.projectBackend.GMotors.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import okhttp3.*;
import java.io.IOException;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service-role-key}")
    private String serviceRoleKey;

    @Value("${supabase.storage.bucket}")
    private String bucketName;

    private final OkHttpClient httpClient = new OkHttpClient();

    /**
     * Sube una imagen de usuario a Supabase Storage
     * @param file archivo a subir
     * @return URL pública de la imagen
     */
    public String subirImagenUsuario(MultipartFile file) {
        return subirArchivo(file, "usuarios/perfil/");
    }

    /**
     * Sube una imagen de producto a Supabase Storage
     */
    public String subirImagenProducto(MultipartFile file) {
        return subirArchivo(file, "productos/imagen/");
    }

    /**
     * Sube una imagen de moto a Supabase Storage
     */
    public String subirImagenMoto(MultipartFile file) {
        return subirArchivo(file, "motos/perfil/");
    }

    /**
     * Método genérico para subir archivos
     */
    private String subirArchivo(MultipartFile file, String carpeta) {
        try {
            // Validar archivo
            if (file.isEmpty()) {
                throw new RuntimeException("El archivo está vacío");
            }

            // Generar nombre único
            String nombreArchivo = generarNombreArchivo(file.getOriginalFilename());
            String ruta = carpeta + nombreArchivo;

            // Obtener contenido del archivo
            byte[] fileContent = file.getBytes();

            // Construir URL de upload
            String uploadUrl = String.format(
                    "%s/storage/v1/object/%s/%s/%s",
                    supabaseUrl, bucketName, carpeta, nombreArchivo
            );

            // Crear request
            Request request = new Request.Builder()
                    .url(uploadUrl)
                    .post(RequestBody.create(fileContent))
                    .header("Authorization", "Bearer " + serviceRoleKey)
                    .header("Content-Type", file.getContentType())
                    .build();

            // Ejecutar request
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Error desconocido";
                    throw new RuntimeException("Error de Supabase: " + response.code() + " - " + errorBody);
                }

                // Retornar URL pública
                return construirUrlPublica(ruta);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo: " + e.getMessage());
        }
    }

    /**
     * Elimina una imagen de Supabase Storage
     */
    public void eliminarImagen(String urlImagen) {
        try {
            // Extraer la ruta de la URL
            String ruta = extraerRutaDeUrl(urlImagen);

            // Construir URL de delete
            String deleteUrl = String.format(
                    "%s/storage/v1/object/%s/%s",
                    supabaseUrl, bucketName, ruta
            );

            // Crear request
            Request request = new Request.Builder()
                    .url(deleteUrl)
                    .delete()
                    .header("Authorization", "Bearer " + serviceRoleKey)
                    .build();

            // Ejecutar request
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful() && response.code() != 204) {
                    System.err.println("Advertencia: No se pudo eliminar la imagen: " + response.code());
                }
            }

        } catch (IOException e) {
            System.err.println("Error al eliminar imagen: " + e.getMessage());
        }
    }

    // ============== MÉTODOS PRIVADOS ==============

    /**
     * Genera un nombre único para el archivo usando UUID
     */
    private String generarNombreArchivo(String nombreOriginal) {
        if (nombreOriginal == null || nombreOriginal.isEmpty()) {
            return UUID.randomUUID().toString() + ".jpg";
        }
        
        // Obtener extensión
        int lastDotIndex = nombreOriginal.lastIndexOf(".");
        String extension = lastDotIndex > 0 ? nombreOriginal.substring(lastDotIndex) : ".jpg";
        
        // Generar nombre único con UUID
        return UUID.randomUUID().toString() + extension;
    }

    /**
     * Construye la URL pública para acceder a la imagen
     */
    private String construirUrlPublica(String ruta) {
        return String.format(
                "%s/storage/v1/object/public/%s/%s",
                supabaseUrl, bucketName, ruta
        );
    }

    /**
     * Extrae la ruta relativa de una URL pública de Supabase
     * Ejemplo: https://proyecto.supabase.co/storage/v1/object/public/gmotors/usuarios/perfil/abc123.jpg
     * Resultado: usuarios/perfil/abc123.jpg
     */
    private String extraerRutaDeUrl(String url) {
        try {
            String[] partes = url.split("/public/");
            if (partes.length > 1) {
                return partes[1].substring(bucketName.length() + 1); // +1 para el /
            }
        } catch (Exception e) {
            System.err.println("Error al extraer ruta de URL: " + e.getMessage());
        }
        return url;
    }
}