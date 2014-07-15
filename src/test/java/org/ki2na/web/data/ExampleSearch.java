package org.ki2na.web.data;

import org.junit.Test;
import org.rdfhdt.hdt.hdt.HDT;
import org.rdfhdt.hdt.hdt.HDTManager;
import org.rdfhdt.hdt.triples.IteratorTripleString;
import org.rdfhdt.hdt.triples.TripleString;

/**
 * Test for read HDT indices
 * 
 * @author Emir Munoz (emir@emunoz.org)
 * 
 */
public class ExampleSearch
{

	@Test
	// Load an HDT and perform a search. (examples/ExampleSearch.java)
	public void main(String[] args) throws Exception
	{
		// Load HDT file.
		// NOTE: Use loadIndexedHDT() for ?P?, ?PO or ??O queries
		HDT hdt = HDTManager.loadHDT("/media/sf_projects/experiments/hdt/raw_infobox_properties_en.hdt", null);

		// Search pattern: Empty string means "any"
		IteratorTripleString it = hdt.search("", "http://dbpedia.org/property/date", "");
		TripleString ts;
		while (it.hasNext())
		{
			ts = it.next();
			System.out.println(ts);
		}
	}

}
