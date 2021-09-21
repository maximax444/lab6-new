package common;

import java.io.Serializable;

/**
 * Class for get response value.
 */
public class Response implements Serializable {
    private int responseStatus;
    /*0 - error; 1 - ok; 2 - exit*/
    private String responseBody;

    public Response(int responseStatus, String responseBody) {
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    /**
     * @return Response сode.
     */
    public int getResponseStatus() {
        return responseStatus;
    }

    /**
     * @return Response body.
     */
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Response[" + responseStatus + ", " + responseBody + "]";
    }
}