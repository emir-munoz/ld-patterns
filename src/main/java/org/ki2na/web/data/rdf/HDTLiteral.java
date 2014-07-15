package org.ki2na.web.data.rdf;

/**
 * Kudos to Mario Arias
 * 
 * @author Emir Munoz (emir@emunoz.org) Based on work from RDFHDT.org by Mario Arias.
 * 
 */
public class HDTLiteral
{

	private String value;
	private String datatype;
	private String lang;

	public HDTLiteral(CharSequence x)
	{
		// FIXME: Avoid converting to String?
		String str = x.toString();
		int len = str.length();

		char next = '\0';
		for (int i = len - 1; i > 0; i--)
		{
			char cur = str.charAt(i);

			if (cur == '"')
			{
				value = str.substring(1, i);
				if (next == '@')
				{
					lang = str.substring(i + 2, len);
				}
				return;
			} else if (cur == '^' && next == '^' && str.charAt(i - 1) == '"')
			{
				// String label = str.substring(1, i - 1);
				value = str.substring(1, i - 1);

				// String datatype = null;
				if (str.charAt(i + 2) == '<' && str.charAt(len - 1) == '>')
				{
					datatype = str.substring(i + 3, len - 1);
				} else
				{
					datatype = str.substring(i + 2, len);
				}
				return;
			}

			next = cur;
		}
	}

	public String getValue()
	{
		return value;
	}

	public String getDatatype()
	{
		return datatype;
	}

	public String getLang()
	{
		return lang;
	}

}
