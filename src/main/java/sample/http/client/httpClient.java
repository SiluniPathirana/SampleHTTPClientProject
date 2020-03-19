package sample.http.client;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.http.client.methods.HttpHead;


public class httpClient {

    private static String urlVal = "https://testapi.karza.in/v2/pan";
    public static void main(String[] args) {

            int socketTimeout=Integer.parseInt(args[0]);
            int connectionTimeout=Integer.parseInt(args[1]);


        HttpHead head = new HttpHead(urlVal);
        //Change implementation to use http client as default http client do not work properly with mutual SSL.
        org.apache.commons.httpclient.HttpClient clientnew = new org.apache.commons.httpclient.HttpClient();
        // extract the host name and add the Host http header for sanity
        head.addHeader("Host", urlVal.replaceAll("https?://", "").replaceAll("(/.*)?", ""));
        clientnew.getParams().setParameter("http.socket.timeout", socketTimeout);
        clientnew.getParams().setParameter("http.connection.timeout", connectionTimeout);
        HttpMethod method = new HeadMethod(urlVal);


        try {
            // Execute the method.
            int statusCode = clientnew.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();
            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            System.out.println( "Status :"+ statusCode);
            System.out.println(method.getStatusLine());              // HTTP/1.1

            if(statusCode==200)
            {
                System.out.println( "Connected successfully Status :"+ statusCode);
            }

        } catch (ConnectTimeoutException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }

    }

}
