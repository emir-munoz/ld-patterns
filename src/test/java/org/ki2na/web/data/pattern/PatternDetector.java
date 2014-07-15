package org.ki2na.web.data.pattern;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.Ignore;
import org.junit.Test;
import org.ki2na.web.data.tartar.algorithm_DataProg.DataProg;
import org.rdfhdt.hdt.exceptions.NotFoundException;
import org.rdfhdt.hdt.hdt.HDT;
import org.rdfhdt.hdt.hdt.HDTManager;
import org.rdfhdt.hdt.triples.IteratorTripleString;
import org.rdfhdt.hdt.triples.TripleString;

/**
 * Test for extraction of lexical patterns.
 * 
 * @author Emir Munoz (emir@emunoz.org)
 * 
 */
public class PatternDetector
{

	public void getPattern(List<String> list)
	{
		// INITIALIZATION
		DataProg dataProg = new DataProg();

		// for (int i = 0; i < 5; i++)
		// {
		ArrayList<String> listAux = new ArrayList<String>(list);
		// System.out.println("=========================");
		dataProg.setTokens(listAux);
		// SIGNIFICANT PATTERNS
		listAux = dataProg.getSignificantPatterns(0.5);

		ListIterator<String> listIter = listAux.listIterator();
		while (listIter.hasNext())
		{
			System.out.println("Significant pattern: " + (String) listIter.next());
		}
	}

	@Test
	@Ignore
	public void test1()
	{
		List<String> listText = new ArrayList<String>();

		listText.add("(315) 111 - 2222");
		listText.add("(314) 211 - 3222");
		listText.add("(314) 311 - 4222");
		listText.add("(310) 411 - 5222");
		listText.add("(310) 511 - 6222");
		listText.add("(311) 611 - 7222");
		listText.add("(311) 711 - 8222");
		listText.add("(312) 811 - 9222");
		listText.add("(312) 111 - 2322");
		listText.add("(312) 211 - 2422");
		listText.add("(312) 211 - 2522");

		this.getPattern(listText);
	}

	@Test
	@Ignore
	public void test2()
	{
		List<String> listText = new ArrayList<String>();

		listText.add("Jack Markell");
		listText.add("Peter Shumlin");
		listText.add("Daniele Capezzone");
		listText.add("S. Thiyagarajah");
		listText.add("Hakim Bahadur Khan");
		listText.add("Mian Muhammad Awais");
		listText.add("Terri Lynn Land");
		listText.add("Dries Oelofse");
		listText.add("Farriet Stemmet");
		listText.add("Mark Nicholson");
		listText.add("Michael W. Branco JP");
		listText.add("Asad Qaiser KPK");
		listText.add("Ejaz Chaudhary Punjab");
		listText.add("Nadir Laghari Punjab");
		listText.add("Qasim Khan Suri Baluchistan");
		listText.add("Frank Albrecht");

		String str = Normalizer.normalize("Volker Schäfer", Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");

		listText.add(str); // Schäfer
		listText.add("Susan Todt");
		listText.add("Ken Capstick");
		listText.add("Bryan Epps SM '14");

		this.getPattern(listText);
	}

	@Test
	@Ignore
	public void test3()
	{
		PredicatePattern pp = new PredicatePattern();
		// read triples' values for the predicate
		List<String> predValues = pp
				.readTriples("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/23969_isbn.hdt.gz");
		for (String pred : predValues)
		{
			System.out.println(pred);
		}
	}

	@Test
	@Ignore
	public void test4()
	{
		PredicatePattern pp = new PredicatePattern();
		// read triples' values for the predicate
		List<String> predValues = pp
				.readTriples("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/02335_2v4a.hdt.gz");
		for (String pred : predValues)
		{
			System.out.println(pred);
		}
	}

	@Test
	public void test5()
	{
		String filename = "00203_birthDate.hdt.gz";

		HDT hdt;
		try
		{
			hdt = HDTManager.loadHDT(
					"/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/"
							+ filename, null);
			// Search pattern: Empty string means "any"
			IteratorTripleString it = hdt.search("", "", "");
			TripleString ts;

			while (it.hasNext())
			{
				ts = it.next();
				System.out.println(ts);
			}
		} catch (IOException ex)
		{
			ex.printStackTrace();
		} catch (NotFoundException ex)
		{
			ex.printStackTrace();
		}
	}

}
