/**********************************************************

ADOBE SYSTEMS INCORPORATED
Copyright 2005-2010 Adobe Systems Incorporated
All Rights Reserved

NOTICE:  Adobe permits you to use, modify, and
distribute this file in accordance with the terms
of the Adobe license agreement accompanying it.
If you have received this file from a source
other than Adobe, then your use, modification,
or distribution of it requires the prior
written permission of Adobe.

*********************************************************/

/**********************************************************

Save as ToPNG.jsx

DESCRIPTION

This sample gets files specified by the user from the
selected folder and batch processes them and saves them
as PNGs in the user desired destination with the same
file name.

Based on Adobe's "Save as PDFs" sample script, intended for Illustrator CS6

**********************************************************/

// Main Code [Execution of script begins here]

// uncomment to suppress Illustrator warning dialogs
// app.userInteractionLevel = UserInteractionLevel.DONTDISPLAYALERTS;

var destFolder, sourceFolder, files, fileType, sourceDoc, targetFile, svgSaveOpts;

// Select the source folder.
sourceFolder = Folder.selectDialog( 'Select the folder with Illustrator files you want to convert to PNG', '~' );

// If a valid folder is selected
if ( sourceFolder != null )
{
  files = new Array();
	fileType = "*.*"

	// Get all files matching the pattern
	files = sourceFolder.getFiles( fileType );

	if ( files.length > 0 )
	{
		// Get the destination to save the files
		destFolder = sourceFolder;
		for ( i = 0; i < files.length; i++ )
		{
			sourceDoc = app.open(files[i]); // returns the document object

			// Call function getNewName to get the name and file to save the SVG
			targetFile = getNewName();

			var opt = new ExportOptionsPNG24();
			opt.antiAliasing = true;
			opt.transparency = true;
			opt.horizontalScale = opt.verticalScale = 512;

			// Save as svg
			sourceDoc.exportFile(targetFile, ExportType.PNG24, opt );

			sourceDoc.close();
		}
		alert( 'Files are saved as PNG in ' + destFolder );
	}
	else
	{
		alert( 'No matching files found' );
	}
}


/*********************************************************

getNewName: Function to get the new file name. The primary
name is the same as the source file.

**********************************************************/

function getNewName()
{
	var ext, docName, newName, saveInFile, docName;
	docName = sourceDoc.name;
	ext = '.png'; // new extension for svg file
	newName = "";

	for ( var i = 0 ; docName[i] != "." ; i++ )
	{
		newName += docName[i];
	}
	newName += ext; // full svg name of the file

	// Create a file object to save the svg
	saveInFile = new File( destFolder + '/' + newName );


	return saveInFile;
}
