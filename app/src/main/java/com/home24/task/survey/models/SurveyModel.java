package com.home24.task.survey.models;

import java.util.ArrayList;

public class SurveyModel
{
	public boolean isLiked;
	//public ArrayList<String> _links;
	public String description;

	public String url;
	public String title;

	public ArrayList<MediaModel> media = new ArrayList<>();

	public class MediaModel
	{
		public String uri;
		public String type;
	}

	public SurveyModel()
    {
    }
}
