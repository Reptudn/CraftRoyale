package de.reptudn.Resourcepack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ResourcePackHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getRequestURI().getPath().substring("/resourcepack".length());
        System.out.println("ResourcePack request for path: " + path);

		if (path.isEmpty() || path.equals("/")) {
			path = "/pack.mcmeta";
		}

		try (InputStream is = getClass().getResourceAsStream("/resourcepack" + path)) {
			if (is == null) {
				exchange.sendResponseHeaders(404, 0);
				exchange.close();
				return;
			}

			String contentType = getContentType(path);
			exchange.getResponseHeaders().set("Content-Type", contentType);

			byte[] response = is.readAllBytes();
			exchange.sendResponseHeaders(200, response.length);

			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response);
			}
		}
	}

	private String getContentType(String path) {
		if (path.endsWith(".mcmeta")) {
			return "application/json";
		} else if (path.endsWith(".png")) {
			return "image/png";
		} else if (path.endsWith(".zip")) {
			return "application/zip";
		} else {
			return "application/octet-stream";
		}
	}
}
