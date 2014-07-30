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

import com.beust.jcommander.internal.Lists;

/**
 * Test for extraction of lexical patterns.
 * 
 * @author Emir Munoz (emir@emunoz.org)
 * 
 */
public class PatternDetectorTest
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
		// System.out.println();
		// }
	}

	@Test
	@Ignore
	public void phoneNumbers()
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
	public void address()
	{
		List<String> listText = Lists.newArrayList();

		listText.add("5 Dudley Avenue");
		listText.add("5711 West Century Boulevard");
		listText.add("10835 Venice Blvd");
		listText.add("11614 Santa Monica Blvd.");
		listText.add("1544 S.La Cienega");
		listText.add("6751 Santa Monica Blvd.");
		listText.add("7912 Beverly Blvd.");
		listText.add("224 Lincoln Blvd");
		listText.add("2628 Wilshire Blvd (@ 26th St)");
		listText.add("510 Santa Monica Blvd (betw 5th&6th)");
		listText.add("3110 Main Street");
		listText.add("1315 3rd St Promenade Ste H");
		listText.add("1622 Ocean Park Blvd");
		listText.add("13723 Fiji Way @ Fisherman's Village");
		listText.add("215 Broadway");
		listText.add("10916 W Pico Blvd (1/2 Blk W Of Westwood Blvd)");
		listText.add("11829 Wilshire Blvd");
		listText.add("119 Broadway @ Ocean");
		listText.add("119 Culver Boulevard");
		listText.add("101 Broadway");
		listText.add("1928 Lincoln Boulevard");
		listText.add("12217 Santa Monica Blvd. #201");
		listText.add("3105 Washington Boulevard");
		listText.add("10032 Venice Blvd");
		listText.add("401 Santa Monica Pier");
		listText.add("3110 Santa Monica Boulevard");
		listText.add("1621 Wilshire Blvd");
		listText.add("1451 3rd Street Promenade");
		listText.add("1401 Ocean Avenue");
		listText.add("1413 5th St");

		this.getPattern(listText);
	}

	@Test
	@Ignore
	public void cities()
	{
		List<String> listText = Lists.newArrayList();

		listText.add("Los Angeles");
		listText.add("Los Angeles");
		listText.add("Pasadena");
		listText.add("Marina Del Ray");
		listText.add("Marina Del Ray");
		listText.add("Marina Del Ray");
		listText.add("Venice");
		listText.add("New York");
		listText.add("Goleta");
		listText.add("Brooklyn");
		listText.add("New York");
		listText.add("New York");
		listText.add("New York");
		listText.add("New York");
		listText.add("Buffallo");
		listText.add("Los Angeles");
		listText.add("Pasadena");
		listText.add("West Hollywood");
		listText.add("Los Angeles");
		listText.add("Los Angeles");
		listText.add("Venice");
		listText.add("Marina Del Ray");
		listText.add("Marina Del Ray");
		listText.add("New York");
		listText.add("New York");
		listText.add("Los Angeles");
		listText.add("Culver City");
		listText.add("Marina Del Ray");
		listText.add("Culver City");
		listText.add("Marina Del Ray");
		listText.add("West Hollywood");

		this.getPattern(listText);
	}

	@Test
	@Ignore
	public void diary()
	{
		List<String> listText = Lists.newArrayList();

		listText.add("Tokyo 4-Day");
		listText.add("Tokyo Atami 1-Day");
		listText.add("Tokyo Xtami 1-Day");
		listText.add("Tokyo Ctami 1-Day");
		listText.add("Tokyo Rrami 1-Day");
		listText.add("Tokyo Tami 1-Day");
		listText.add("Tokyo Ytami 1-Day");
		listText.add("Tokyo Utami 7-Day");
		listText.add("Tokyo Itami 8-Day");
		listText.add("Tokyo Btami 2-Day");
		listText.add("Tokyo Rtami 4-Day");
		listText.add("Tokyo-Kyoto 5-Day");
		listText.add("Tokyo Banana 5-Day");
		listText.add("Golden Route 5-Day");
		listText.add("Golden Route 6-Day");
		listText.add("Golden Route 7-Day");
		listText.add("Japan Alps 7-Day");
		listText.add("Japanese Pottery 10-Day");
		listText.add("World Heritage 7-Day");

		this.getPattern(listText);
	}

	@Test
	@Ignore
	public void names()
	{
		List<String> listText = Lists.newArrayList();

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
				.getPredicateValues("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/23969_isbn.hdt.gz");
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
				.getPredicateValues("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/02335_2v4a.hdt.gz");
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
