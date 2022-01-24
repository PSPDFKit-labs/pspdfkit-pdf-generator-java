import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class PSPDFKitApiExample {
    public static void main(final String[] args) throws IOException {
        final RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "index.html",
                        "index.html",
                        RequestBody.create(
                                new File("index.html"),
                                MediaType.parse("text/html")
                        )
                )
                .addFormDataPart(
                        "style.css",
                        "style.css",
                        RequestBody.create(
                                new File("style.css"),
                                MediaType.parse("text/css")
                        )
                )
                .addFormDataPart(
                        "Inter-Regular.ttf",
                        "Inter-Regular.ttf",
                        RequestBody.create(
                                new File("Inter-Regular.ttf"),
                                MediaType.parse("font/ttf")
                        )
                )
                .addFormDataPart(
                        "Inter-Medium.ttf",
                        "Inter-Medium.ttf",
                        RequestBody.create(
                                new File("Inter-Medium.ttf"),
                                MediaType.parse("font/ttf")
                        )
                )
                .addFormDataPart(
                        "Inter-Bold.ttf",
                        "Inter-Bold.ttf",
                        RequestBody.create(
                                new File("Inter-Bold.ttf"),
                                MediaType.parse("font/ttf")
                        )
                )
                .addFormDataPart(
                        "SpaceMono-Regular.ttf",
                        "SpaceMono-Regular.ttf",
                        RequestBody.create(
                                new File("SpaceMono-Regular.ttf"),
                                MediaType.parse("font/ttf")
                        )
                )
                .addFormDataPart(
                        "logo.svg",
                        "logo.svg",
                        RequestBody.create(
                                new File("logo.svg"),
                                MediaType.parse("image/svg+xml")
                        )
                )
                .addFormDataPart(
                        "instructions",
                        new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject()
                                                .put("html", "index.html")
                                                .put("assets", new JSONArray()
                                                        .put("style.css")
                                                        .put("Inter-Regular.ttf")
                                                        .put("Inter-Medium.ttf")
                                                        .put("Inter-Bold.ttf")
                                                        .put("SpaceMono-Regular.ttf")
                                                        .put("logo.svg")
                                                )
                                        )
                                ).toString()
                )
                .build();

        final Request request = new Request.Builder()
                .url("https://api.pspdfkit.com/build")
                .method("POST", body)
                .addHeader("Authorization", "Bearer <Your API Key>")
                .build();

        final OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        final Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            Files.copy(
                    response.body().byteStream(),
                    FileSystems.getDefault().getPath("result.pdf"),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } else {
            // Handle the error
            throw new IOException(response.body().string());
        }
    }
}
