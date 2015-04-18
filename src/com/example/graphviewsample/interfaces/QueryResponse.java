package com.example.graphviewsample.interfaces;

import java.util.List;

import twitter4j.Status;

public interface QueryResponse {
	public void onError(String errorResponse);
	public void onResponse(List<Status> list);
	

}
