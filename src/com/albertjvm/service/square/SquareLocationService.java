package com.albertjvm.service.square;

import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.ListLocationsResponse;
import com.squareup.connect.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SquareLocationService {

    private final SquareOauthService squareOauthService;

    @Autowired
    public SquareLocationService(SquareOauthService squareOauthService) {
        this.squareOauthService = squareOauthService;
    }

    public List<String> getLocationIds(String merchantId) throws ApiException {
        LocationsApi locationsApi = locationsApi(squareOauthService.getAndRefreshTokenForMerchant(merchantId));
        ListLocationsResponse response = locationsApi.listLocations();

        return response.getLocations().stream().map(Location::getId).collect(Collectors.toList());
    }

    private LocationsApi locationsApi (String accessToken) {
        ApiClient apiClient = Configuration.getDefaultApiClient();
        OAuth oAuth2 = (OAuth) apiClient.getAuthentication("oauth2");
        oAuth2.setAccessToken(accessToken);

        LocationsApi locationsApi = new LocationsApi();
        locationsApi.setApiClient(apiClient);
        return locationsApi;
    }
}
