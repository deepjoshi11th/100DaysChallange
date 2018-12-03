package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import rest.RestMan;

public class Data {
	int daysPast;
	public Data(int daysPast) {
		super();
		this.daysPast = daysPast;
	}
	public int getDaysPast() {
		return daysPast;
	}
	public void setDaysPast(int daysPast) {
		this.daysPast = daysPast;
	}
	public String getRandomQuote() throws IOException  {
		File f = new File("Quote.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String quote;
		int quoteNumber = (int)(Math.random()*28);
		for(int i=0;((quote=br.readLine())!=null)&&(i<2*quoteNumber-1);i++) {
		}
		br.close();
		return quote;
	}
	public void tweet(String projectTitle, String githubLink) {
		RestMan restman = new RestMan();
		String shortUrl = restman.shortURl(githubLink);
		String tweet = projectTitle +"\nlink"+shortUrl+"\n#Day"+daysPast+"#100DaysOfCode";
		restman.tweet(tweet);
		daysPast++;
	}
}
