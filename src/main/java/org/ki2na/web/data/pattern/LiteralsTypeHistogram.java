package org.ki2na.web.data.pattern;

import gnu.trove.set.hash.THashSet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.ki2na.web.data.util.ConfigReader;
import org.ki2na.web.data.util.DirectoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Compute histogram of literal types.
 * 
 * @author Emir Munoz (emir@emunoz.org)
 * 
 */
public class LiteralsTypeHistogram
{

	private final static transient Logger _log = LoggerFactory.getLogger(LiteralsTypeHistogram.class.getName());

	/**
	 * Main. List all the literal types found in the index and write them into another file.
	 * 
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
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/24145_iupacName.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/11739_convictionStatus.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/30877_netIncome.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/07994_buildingCosts.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/05696_audDamage.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/07322_blank1InfoSec.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/37931_products.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/19863_gdp.hdt.gz");
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/12184_cover.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/45507_stats.hdt.gz");
		// out of memory
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/23111_imagesize.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/00718_iupacName.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/16892_exportPartners.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/01430_wikiPageWikiLink.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/35046_pdb.hdt.gz");
		// out of memory
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/39819_recorded.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/45556_stdinchi.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/41953_scoring.hdt.gz");
		// too much time
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/23229_importPartners.hdt.gz");
		// out of memory
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/41763_school.hdt.gz");
		// negative array size exception
		exp.add("/media/sf_projects/experiments/predicates/srvgal84.deri.ie/~marari/DBPedia-3.9-by-predicate/00090_abstract.hdt.gz");

		PrintWriter out;
		List<String> literalTypes;
		int counter = 0;

		try
		{
			out = new PrintWriter("./dtHistogram/literalHistogram2.txt");

			for (String file : fileNames)
			{
				// skip files that cause exception because of the encoding of "$" symbol
				if (exp.contains(file))
				{
					_log.error(file);
					continue;
				}
				System.out.println(++counter + "\t" + file);

				// initialise the array
				literalTypes = new ArrayList<String>();
				// read dataTypes found in each triple
				literalTypes = pp.getLiteralTypes(file);

				// avoid to call the pattern detector with less than one element
				if (literalTypes.size() >= 1)
				{
					for (String value : literalTypes)
					{
						out.println(value);
					}
					out.flush();
				}
			}
			// close file
			out.close();
		} catch (FileNotFoundException e)
		{
			_log.info("File not found error.");
			System.exit(1);
		}
	}

}
