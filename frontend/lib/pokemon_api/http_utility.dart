import 'package:http/http.dart';

/// This is a wrapper class for the http package provided by dart.
/// It provides methods for GET, PUT, and POST requests for string payloads.
class HttpUtility {
  String origin;

  HttpUtility({
    required this.origin,
  });

  /// Makes a HTTP GET request to the provided [endpoint]
  Future<String> getRequest(String endpoint) async {
    try {
      Uri url = Uri.http(origin, endpoint);
      Response response = await get(url);
      if (response.statusCode != 200) {
        // Return if the request did not succeed
        return "";
      }
      return response.body;
    } catch (e) {
      print("Error calling get api $endpoint: $e");
    }
    return "";
  }

  /// Makes a HTTP PUT request to the provided [endpoint] and supplies
  /// the [payload] as the request body.
  Future<String> putRequest(String endpoint, String payload) async {
    try {
      Uri url = Uri.http(origin, endpoint);
      Response response = await put(
        url,
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: payload,
      );
      if (response.statusCode != 200) {
        // Return if the request did not succeed
        return "";
      }
      return response.body;
    } catch (e) {
      print("Error calling put api $endpoint: $e");
    }
    return "";
  }

  /// Makes a HTTP POST request to the provided [endpoint] and supplies
  /// the [payload] as the request body.
  Future<String> postRequest(String endpoint, String payload) async {
    try {
      Uri url = Uri.http(origin, endpoint);
      Response response = await post(
        url,
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: payload,
      );
      if (response.statusCode != 200) {
        // Return if the request did not succeed
        return "";
      }
      return response.body;
    } catch (e) {
      print("Error calling post api $endpoint: $e");
    }
    return "";
  }
}
