package it.unifi.ing.hci.resource;

import it.unifi.ing.hci.model.FormData;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.*;

@Path("/media")
public class MediaResource extends CommonResource {

    @Inject
    S3Client s3;

    @ConfigProperty(name = "domain")
    public String rootDomain;

    private final String UPLOADED_FILE_PATH = "";

    @POST
    @Tag(name = "media")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(MultipartFormDataInput input){
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");
        System.out.println("Files are " + inputParts.size());

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);

                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                byte [] bytes = IOUtils.toByteArray(inputStream);

                fileName = UPLOADED_FILE_PATH + fileName;

                writeFile(bytes,fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return Response.ok().build();
    }

    @GET
    @Tag(name = "media")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFileList(){
        File dir = new File(".");
        int sum = 0;
        List<String> exts = Arrays.asList("jpg", "jpeg");
        Iterator<String> iterator = exts.iterator();
        while (iterator.hasNext()){
            String ce = "." + iterator.next();
            sum = sum +
                    dir.listFiles((dir1, name) -> name.endsWith(ce)).length +
                    dir.listFiles((dir1, name) -> name.endsWith(ce.toUpperCase())).length;
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("mediaCount", sum);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{name}")
    @Tag(name = "media")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getMedia(@PathParam("name") String fileName) throws IOException {
        byte[] buffer = new byte[4096];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                fileName));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytes = 0;
        while ((bytes = bis.read(buffer, 0, buffer.length)) > 0) {
            baos.write(buffer, 0, bytes);
        }
        baos.close();
        bis.close();
        Response.ResponseBuilder response = Response.ok((StreamingOutput) baos::writeTo);
        response.header("Content-Type", "image/jpeg");
        return response.build();
    }



    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }



}
