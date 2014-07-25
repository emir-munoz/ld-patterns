package org.ki2na.web.data.output;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.ki2na.web.data.util.DirectoryUtils;

public class PatternsToTSV
{

	private THashMap<String, String> mappings = new THashMap<String, String>();

	public PatternsToTSV()
	{

	}

	public String getMappingFor(String key)
	{
		return this.mappings.get(key);
	}

	public void readMappingFile(String filename)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));

			String line = br.readLine();
			String[] pair;
			while (line != null)
			{
				if (!line.isEmpty())
				{
					pair = line.split("\t");
					this.mappings.put(pair[0], pair[1]);
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public double getCoverageOfPattern(String pattern)
	{
		java.util.regex.Pattern PUNCT_RE = java.util.regex.Pattern.compile("<(\\d+)/(\\d+)>");
		java.util.regex.Matcher m = PUNCT_RE.matcher(pattern);
		double d = 0;

		while (m.find())
			d = Double.parseDouble(m.group(1)) / Double.parseDouble(m.group(2));

		return d;
	}

	public String[] getPatternAndScore(String line)
	{
		String score = String.valueOf(this.getCoverageOfPattern(line));
		String pattern = line.substring(0, line.lastIndexOf("<") - 1);

		return new String[] { pattern, score };
	}

	public static void main(String[] args)
	{
		PatternsToTSV tsv = new PatternsToTSV();
		tsv.readMappingFile("./mappingFilePropertiesv3.txt");

		THashSet<String> fileNames = DirectoryUtils.getFilesList("./patterns2/", ".hdt.gz.pattern");
		System.out.println(fileNames.size() + " pattern files found.");
		
		try
		{
			PrintWriter out = new PrintWriter("./patternsv2.tsv");
			BufferedReader br;
			String line;
			String property;
			String[] nameParts;
			String indexName;
			String[] score;

			// for each file, generate an entry in the TSV file
			for (String file : fileNames)
			{
//				System.out.println(file);
				
				// read the input file
				br = new BufferedReader(new FileReader(file));

				nameParts = file.split("/");
				indexName = nameParts[nameParts.length - 1].replace(".pattern", "");
				property = tsv.getMappingFor(indexName);
				
				if (property == null)
				{
					System.out.println(indexName);
				}

				line = br.readLine();
				while (line != null && !line.isEmpty())
				{
					// skip comments
					if (!line.startsWith("-------------- BELOW TRESHOLD"))
					{
						// System.out.println("###" + line);
						score = tsv.getPatternAndScore(line);
						out.println(property + "\t" + score[0] + "\t" + score[1]);
					}
					line = br.readLine();
				}
				// close
				br.close();
				out.flush();
			}
			// close
			out.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
