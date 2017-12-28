package com.jaxson.lib.util.exceptions;

public class FileExtensionNotFoundException extends IllegalArgumentException
{
    private static final long serialVersionUID = -9122046196827874273L;
    private static final String LOADER_NOT_FOUND = " is an invalid filetype";

    public FileExtensionNotFoundException()
    {
        super();
    }

    public FileExtensionNotFoundException(String extension)
    {
        super(extension + LOADER_NOT_FOUND);
    }
}
