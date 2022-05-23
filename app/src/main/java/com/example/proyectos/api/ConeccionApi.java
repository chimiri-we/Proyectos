package com.example.proyectos.api;

import com.example.proyectos.api.mapping.ListUsuario;
import com.example.proyectos.api.mapping.ListaEmpresa;
import com.example.proyectos.api.mapping.LoginBody;
import com.example.proyectos.api.mapping.RegistroBody;
import com.example.proyectos.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * REST service de SaludMock
 */

public interface ConeccionApi {

    /*
    Elige la dirección del localhost según tu caso.

    Si usas un teléfono real, entonces pon la IP de tu PC local. Pero asegúrate que estén bajo la
    misma red, de lo contrario la conexión será rechazada.

    Luego asignale la variable a HOST para que sea reemplazada en BASE_URL.

    (Nota: todas las conexiones serán rechazadas si no inicias los servicios de Apache y MySQL)
     */

   // String GENYMOTION_LOCALHOST = "10.0.3.2";
   // String AVD_LOCALHOST = "10.0.2.2";
   // String PC_LOCALHOST = "[Pon aquí tu IP de red local]";
    String HOST = "api.pedromorap.online";
    String PATH = "/v1/";

    String BASE_URL = String.format("http://%s/%s", HOST, PATH);

    String HEADER_AUTHORIZATION = "Authorization";


    @POST("usuario/login")
    Call<Usuario> login(@Body LoginBody loginBody);

 @POST("usuario/registrar")
 Call<Usuario> registro(@Body RegistroBody registroBody);

 @GET("usuario/")
 Call<ListUsuario> getListUsuarios(@Header(HEADER_AUTHORIZATION) String token);

 @GET("usuario/")
 Call<ListaEmpresa> getListEmpresa(@Header(HEADER_AUTHORIZATION) String token);


    /*@GET("appointments")
    Call<ApiResponseAppointments> getAppointments(@Header(HEADER_AUTHORIZATION) String token,
                                                  @QueryMap Map<String, Object> parameters);

    @Headers("Content-Type: application/json")
    @PATCH("appointments/{id}")
    Call<ApiMessageResponse> cancelAppointment(@Path("id") int appoitmentId,
                                               @Header(HEADER_AUTHORIZATION) String token,
                                               @Body HashMap<String, String> statusMap);

    @GET("medical-centers")
    Call<MedicalCentersRes> getMedicalCenters(@Header(HEADER_AUTHORIZATION) String token);

    @GET("doctors/availability")
    Call<DoctorsAvailabilityRes> getDoctorsSchedules(@Header(HEADER_AUTHORIZATION) String token,
                                                     @QueryMap Map<String, Object> parameters);

    @Headers("Content-Type: application/json")
    @POST("appointments")
    Call<ApiMessageResponse> createAppointment(@Header(HEADER_AUTHORIZATION) String token,
                                               @Body PostAppointmentsBody body);

    @GET("issue-types")
    Call<List<IssueType>> getIssueTypes(@Header(HEADER_AUTHORIZATION) String token);

    @Multipart
    @POST("issues")
    Call<Issue> createIssue(@Header(HEADER_AUTHORIZATION) String token,
                            @Part("issue_type") RequestBody type,
                            @Part("description") RequestBody description,
                            @Part MultipartBody.Part image);*/

}
