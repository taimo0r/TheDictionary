package com.taimoor.dictionary;

import com.taimoor.dictionary.Models.APIResponse;

public interface onFetchDataListener {

    void onFetchData(APIResponse apiResponse, String message);

    void onError(String message);

}
