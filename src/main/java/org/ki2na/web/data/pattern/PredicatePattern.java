package org.ki2na.web.data.pattern;

import gnu.trove.set.hash.THashSet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.ki2na.web.data.rdf.HDTLiteral;
import org.ki2na.web.data.tartar.algorithm_DataProg.DataProg;
import org.ki2na.web.data.util.ConfigReader;
import org.ki2na.web.data.util.DirectoryUtils;
import org.rdfhdt.hdt.exceptions.IllegalFormatException;
import org.rdfhdt.hdt.exceptions.NotFoundException;
import org.rdfhdt.hdt.hdt.HDT;
import org.rdfhdt.hdt.hdt.HDTManager;
import org.rdfhdt.hdt.triples.IteratorTripleString;
import org.rdfhdt.hdt.triples.TripleString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Emir Munoz (emir@emunoz.org)
 * 
 */
public class PredicatePattern
{

	private final static transient Logger _log = LoggerFactory.getLogger(PredicatePattern.class.getName());
	private DataProg dataProg;

	public PredicatePattern()
	{
	}

	/**
	 * Normalise the input String
	 * 
	 * @param str Input String
	 * @return Normalised String
	 */
	public String normalise(final String str)
	{
		String norm = Normalizer.normalize(str, Normalizer.Form.NFD);
		norm = norm.replaceAll("[^\\p{ASCII}]", "");

		return norm;
	}

	public void getPatterns(String file, List<String> predValues, PrintWriter out)
	{
		// initialise the dataprog
		dataProg = new DataProg();

		ArrayList<String> listAux = new ArrayList<String>(predValues);
		try
		{
			dataProg.setTokens(listAux);
			// SIGNIFICANT PATTERNS
			listAux = dataProg.getSignificantPatterns(0.5);

			ListIterator<String> listIter = listAux.listIterator();
			while (listIter.hasNext())
			{
				out.println((String) listIter.next());
			}
		} catch (Exception ex)
		{
			_log.error(file);
		}
	}

	public int getSize(String filename)
	{
		int size = 0;
		// Load HDT file.
		// NOTE: Use loadIndexedHDT() for ?P?, ?PO or ??O queries
		HDT hdt;
		try
		{
			hdt = HDTManager.loadHDT(filename, null);
			size = (int) hdt.getTriples().size();
		} catch (IOException ex)
		{
			_log.info("Error reading index file: " + filename);
		} catch (IllegalFormatException ex)
		{
			_log.info("Error with the index");
		}

		return size;
	}

	public List<String> getDataTypes(String filename)
	{
		List<String> dtValues = new ArrayList<String>();

		// Load HDT file.
		// NOTE: Use loadIndexedHDT() for ?P?, ?PO or ??O queries
		HDT hdt;
		try
		{
			hdt = HDTManager.loadHDT(filename, null);
			// Search pattern: Empty string means "any"
			IteratorTripleString it = hdt.search("", "", "");
			TripleString ts;
			HDTLiteral lit;

			while (it.hasNext())
			{
				ts = it.next();
				lit = new HDTLiteral(ts.getObject());

				if (lit.getDatatype() != null && !lit.getDatatype().isEmpty())
					dtValues.add(lit.getDatatype());
			}
		} catch (IOException ex)
		{
			_log.info("Error reading index file: " + filename);
		} catch (NotFoundException ex)
		{
			_log.info("Index not found: " + filename);
		} catch (IllegalFormatException ex)
		{
			_log.info("Error with the index");
		}

		return dtValues;
	}

	public List<String> getLiteralTypes(String filename)
	{
		List<String> literalTypes = new ArrayList<String>();

		// Load HDT file.
		// NOTE: Use loadIndexedHDT() for ?P?, ?PO or ??O queries
		HDT hdt;
		try
		{
			hdt = HDTManager.loadHDT(filename, null);
			// Search pattern: Empty string means "any"
			IteratorTripleString it = hdt.search("", "", "");
			TripleString ts;
			HDTLiteral lit;

			while (it.hasNext())
			{
				ts = it.next();
				lit = new HDTLiteral(ts.getObject());

				if (lit.getDatatype() != null && !lit.getDatatype().isEmpty())
					literalTypes.add("typed");
				else if (lit.getLang() != null && !lit.getLang().isEmpty()) {
					literalTypes.add("plain");
				} else
					literalTypes.add("none");
			}
		} catch (IOException ex)
		{
			_log.info("Error reading index file: " + filename);
		} catch (NotFoundException ex)
		{
			_log.info("Index not found: " + filename);
		} catch (IllegalFormatException ex)
		{
			_log.info("Error with the index");
		}

		return literalTypes;
	}
	
	public List<String> readTriples(String filename)
	{
		List<String> predValues = new ArrayList<String>();

		// Load HDT file.
		// NOTE: Use loadIndexedHDT() for ?P?, ?PO or ??O queries
		HDT hdt;
		try
		{
			hdt = HDTManager.loadHDT(filename, null);
			// Search pattern: Empty string means "any"
			IteratorTripleString it = hdt.search("", "", "");
			TripleString ts;
			HDTLiteral lit;

			while (it.hasNext())
			{
				ts = it.next();
				lit = new HDTLiteral(ts.getObject());

				if (lit.getLang() != null)
				{
					if (lit.getValue() == null || lit.getValue().isEmpty() || lit.getValue().trim().length() == 0)
					{
						// || lit.getValue().equalsIgnoreCase("NA")
						// || lit.getValue().equalsIgnoreCase("N/A") ||
						// lit.getValue().equalsIgnoreCase("none")
						// || lit.getValue().equalsIgnoreCase("ISBN") ||
						// lit.getValue().equalsIgnoreCase("TBA")
						// || lit.getValue().equalsIgnoreCase("Unknown")
						continue;
					} else
					{
						// System.out.println(lit.getValue() + "###" + lit.getLang());
						predValues.add(this.normalise(lit.getValue()));
					}
				} else
				{
					if (lit.getValue() != null && !lit.getValue().isEmpty())
						predValues.add(lit.getValue());
					// System.out.println(lit.getValue() + "###" + lit.getDatatype());
				}
			}
		} catch (IOException ex)
		{
			_log.info("Error reading index file: " + filename);
		} catch (NotFoundException ex)
		{
			_log.info("Index not found: " + filename);
		}

		return predValues;
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		PredicatePattern pp = new PredicatePattern();

		// Read the full list of predicates
		THashSet<String> fileNames = DirectoryUtils.getFilesList(ConfigReader.getProperty("sources", "predicates"),
				".hdt.gz");

		THashSet<String> exp = new THashSet<String>();
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/28122_marketCapitalisation.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/16186_entryfee.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/05271_assets.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/53199_rdf-schema#comment.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/46487_surplus.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/06856_below.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/05444_associatedActs.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/38407_purse.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/28120_marketCap.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/20742_gross.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/16502_estimatedValue.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/04010_alsoKnownAs.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/17421_fdi.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/29257_mission.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/04291_amount.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/24145_iupacName.hdt.gz"); // too
																																		// much
																																		// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/11739_convictionStatus.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/30877_netIncome.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/07994_buildingCosts.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/05696_audDamage.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/07322_blank1InfoSec.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/37931_products.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/19863_gdp.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/12184_cover.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/45507_stats.hdt.gz"); // too
																																	// much
																																	// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/23111_imagesize.hdt.gz"); // out
																																		// of
																																		// memory
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/00718_iupacName.hdt.gz"); // too
																																		// much
																																		// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/16892_exportPartners.hdt.gz"); // too
																																			// much
																																			// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/01430_wikiPageWikiLink.hdt.gz"); // too
																																				// much
																																				// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/35046_pdb.hdt.gz"); // too
																																	// much
																																	// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/39819_recorded.hdt.gz"); // out
																																		// of
																																		// memory
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/45556_stdinchi.hdt.gz"); // too
																																		// much
																																		// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/41953_scoring.hdt.gz"); // too
																																		// much
																																		// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/23229_importPartners.hdt.gz"); // too
																																			// much
																																			// time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/41763_school.hdt.gz"); // out
																																	// of
																																	// memory
		// negative array size exception
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/00090_abstract.hdt.gz");

		PrintWriter out;
		String[] nameParts;
		List<String> predValues;
		int counter = 0;

		for (String file : fileNames)
		{
			// skip files that cause exception because of the encoding of "$" symbol
			if (exp.contains(file))
			{
				_log.error(file);
				continue;
			}

			System.out.println(++counter + "\t" + file);
			nameParts = file.split("/");

			predValues = new ArrayList<String>();
			try
			{
				// "/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/23969_isbn.hdt.gz"

				// read triples' values for the predicate
				predValues = pp.readTriples(file);
				// System.out.println(predValues.size() + "=" + predValues.toString());
				// shuffle the elements in the array to introduce randomness
				Collections.shuffle(predValues);
				// select the first 500 items
				if (predValues.size() > 500)
					predValues = predValues.subList(0, 500);

				// avoid to call the pattern detector with less than one element
				if (predValues.size() > 1)
				{
					out = new PrintWriter("./patterns2/" + nameParts[nameParts.length - 1] + ".pattern");
					// System.out.println("Number of literal objects=" + predValues.size());
					pp.getPatterns(file, predValues, out);

					// close file
					out.flush();
					out.close();
				}
				// else
				// {
				// out.println(predValues);
				// }
			} catch (FileNotFoundException e)
			{
				_log.info("File not found error.");
			}
		}
	}
}
