package org.ki2na.web.data.pattern;

import gnu.trove.set.hash.THashSet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.ki2na.web.data.util.ConfigReader;
import org.ki2na.web.data.util.DirectoryUtils;
import org.rdfhdt.hdt.exceptions.NotFoundException;
import org.rdfhdt.hdt.hdt.HDT;
import org.rdfhdt.hdt.hdt.HDTManager;
import org.rdfhdt.hdt.triples.IteratorTripleString;
import org.rdfhdt.hdt.triples.TripleString;

public class MappingFileProperties
{

	public static void main(String[] args) throws FileNotFoundException
	{
		// Read the full list of predicates
		THashSet<String> fileNames = DirectoryUtils.getFilesList(ConfigReader.getProperty("sources", "predicates"),
				".hdt.gz");
		int total = fileNames.size();
		System.out.println("# of files=" + total);

		PrintWriter out = new PrintWriter("./mappingFilePropertiesv3.txt");
		String[] nameParts;
		int count = 0;

		for (String file : fileNames)
		{
			nameParts = file.split("/");
			HDT hdt;
			try
			{
				hdt = HDTManager.loadHDT(file, null);
				// Search pattern: Empty string means "any"
				IteratorTripleString it = hdt.search("", "", "");
				TripleString ts;

				if (it.hasNext())
				{
					ts = it.next();
					System.out.println(count++ + "/" + total + "\t" + nameParts[nameParts.length - 1] + "\t"
							+ ts.getPredicate());
					out.println(nameParts[nameParts.length - 1] + "\t" + ts.getPredicate());
				}
				// hdt.close();
				out.flush();
			} catch (IOException ex)
			{
				System.err.println(file);
				ex.printStackTrace();
			} catch (NotFoundException ex)
			{
				System.err.println(file);
				ex.printStackTrace();
			} catch (NegativeArraySizeException ex)
			{
				System.err.println(file);
				ex.printStackTrace();
			}
		}
		// close file
		out.close();
	}

}
