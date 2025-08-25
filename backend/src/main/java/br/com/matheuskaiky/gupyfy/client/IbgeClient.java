package br.com.matheuskaiky.gupyfy.client;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class IbgeClient {

    private static final String IBGE_API_URL = "https://servicodados.ibge.gov.br/api/v1/";
    private static final int TIMEOUT = 30;
    private final OkHttpClient httpClient;

    public IbgeClient() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    // Get all states from IBGE API
    public
}
