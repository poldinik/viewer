package it.unifi.ing.hci;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="Viewer API",
                version = "1.0.0",
                description = "Image EXIF Viewer API for HCI exam project",
                contact = @Contact(
                        name = "Lorenzo Vannucchi",
                        email = "lorenzo.vannucchi2@stud.unifi.it"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class ViewerApplication extends Application {
}
