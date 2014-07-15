package org.ki2na.web.data.util;

import gnu.trove.set.hash.THashSet;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Set of funcionalities to work with directories and files.
 * 
 * @author Emir Munoz (emir@emunoz.org)
 * @see ExtensionFileFilter
 * 
 */
public class DirectoryUtils
{

	/**
	 * Return the list of directories in a given path.
	 * 
	 * @param rootDir Given path.
	 * @param pattern Pattern for the filter.
	 */
	public static THashSet<String> getDirectoryList(String rootDir, final String pattern)
	{
		THashSet<String> dirSet = new THashSet<String>();

		File dir = new File(rootDir);
		// String[] children = dir.list();
		// if (children == null)
		// {
		// // Either dir does not exist or is not a directory
		// return dirSet;
		// }
		// It is also possible to filter the list of returned files.
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				if (dir.isDirectory() && name.startsWith(pattern))
					return true;
				else
					return false;
			}
		};
		String[] children = dir.list(filter);
		for (int i = 0; i < children.length; i++)
			dirSet.add(children[i]);

		return dirSet;
	}

	/**
	 * Return the list of files in a given path.
	 * 
	 * @param rootDir Given path.
	 * @param pattern Pattern for the filter.
	 */
	public static THashSet<String> getFilesList(String rootDir, final String pattern)
	{
		THashSet<String> filesSet = new THashSet<String>();

		if (!rootDir.endsWith("/"))
			rootDir = rootDir + "/";

		File dir = new File(rootDir);
		String[] children = dir.list();
		if (children == null)
		{
			// Either dir does not exist or is not a directory
			return filesSet;
		}
		// It is also possible to filter the list of returned files.
		// This example does not return any files that start with `.'.
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.endsWith(pattern); // e.g. ".gz"
			}
		};
		children = dir.list(filter);
		for (int i = 0; i < children.length; i++)
			filesSet.add(rootDir + children[i]);
		// filesSet.add(rootDir + "/" + children[i]);

		return filesSet;
	}

	/**
	 * Get a list with all files inside an specific directory.
	 * 
	 * @param rootDir Path to root directory.
	 */
	public static List<File> getFileList(String rootDir)
	{
		List<File> files = Lists.newArrayList();
		File documentDir = new File(rootDir);
		if (!documentDir.isDirectory())
		{
			files.add(documentDir);
			return files;
		}
		// recursively...
		listFiles_r(files, documentDir);
		return files;
	}

	/**
	 * Get a list with all files inside a directory, recursively.
	 * 
	 * @param files Final list of files.
	 * @param dir Path to root directory.
	 */
	public static void listFiles_r(List<File> files, File dir)
	{
		File[] docs = dir.listFiles(new FileFilter()
		{
			public boolean accept(File pathname)
			{
				return (pathname.isDirectory() || pathname.getName().endsWith(".xml"));
			}
		});
		for (File doc : docs)
		{
			if (doc.isDirectory())
			{
				listFiles_r(files, doc);
			} else
			{
				files.add(doc);
			}
		}
	}

}
